package GRUPOAERS.UAN.core.utils.configuracao_replicacao.operacores_replicacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.anuncios.ws.autenticacao.UtilizadorRegistoReplicacaoRequest;
import GRUPOAERS.UAN.core.utils.configuracao_replicacao.ultima_execucao_controller.UltimaExecucaoService;
import GRUPOAERS.UAN.core.utils.soap_clients.ReplicacaoClient;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.data_source.PerfilDataSource;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.data_source.UtilizadorDataSource;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade.PerfilEntidade;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;

@Component
public class RegistarUtilizadorJob implements Job{
    @Autowired
    private UtilizadorDataSource utilizadorDataSource;
    @Autowired
    private ReplicacaoClient replicacaoClient;
    @Autowired
    private UltimaExecucaoService ultimaExecucaoService;
    @Autowired
    private PerfilDataSource jpaPerfil;


    private static final String CHAVE_ROLE = "UTILIZADOR";
    //https://anunciosloccentral.onrender.com
    private static final String URL_SERVIDOR_CENTRAL = "http://localhost:8082/ws";
    // private static final LocalEntidade URL_SERVIDOR_CENTRAL = new LocalEntidade(0, "CENTRAL", "https://anunciosloccentral.onrender.com/ws", null);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

            String chaveExecucao = CHAVE_ROLE + "_CENTRAL";

            LocalDateTime ultimaDataReplicacao = ultimaExecucaoService.getUltimaData(chaveExecucao);

            List<UtilizadorEntidade> modificados = utilizadorDataSource
                .findByUpdatedAtIsNotNullAndUpdatedAtGreaterThanEqual(ultimaDataReplicacao);

            LocalDateTime menorDataErro = ultimaDataReplicacao;
            boolean sucessoTotal = true;

                System.out.println("=== INICIANDO REPLICACAO [ACTUALIZAR UTILIZADOR] : ");
                System.out.println("=== SERVIDOR : CENTRAL");
                System.out.println("=== QTD MODIFICADOS: " + modificados.size());

            for (UtilizadorEntidade u : modificados) {
                boolean sucesso = tentarReplicarComRetry(u, 3, URL_SERVIDOR_CENTRAL);

                if (!sucesso) {
                    sucessoTotal = false;
                    System.out.println("FALHA AO REPLICAR NO SERVIDOR CENTRAL " + " -> NOME: " + u.getNome());
                    if (menorDataErro.isEqual(ultimaDataReplicacao)) {
                        menorDataErro = u.getUpdatedAt();
                    }
                }
            }

            if (sucessoTotal) {
                System.out.println("[OK] TODOS DADOS REPLICADOS COM SUCESSO NO SERVIDOR CENTRAL");
                ultimaExecucaoService.atualizarUltimaExecucaoNaReplicacao(chaveExecucao);
            } else {
                System.out.println("[ERRO] ALGUMAS REPLICAÇÕES FALHARAM NO SERVIDOR CENTRAL");
                ultimaExecucaoService.atualizarUltimaExecucaoNaReplicacaoErro(chaveExecucao, menorDataErro);
            }
    }



    private boolean tentarReplicarComRetry(UtilizadorEntidade u, int maxTentativas, String urlServidor) {
        int tentativas = 0;
        while (tentativas < maxTentativas) {
            try {
                
                UtilizadorRegistoReplicacaoRequest request = new UtilizadorRegistoReplicacaoRequest();
                PerfilEntidade perfil = new PerfilEntidade();
                Optional<PerfilEntidade> p = jpaPerfil.findById(request.getIdPerfilActivo());
                if (!p.isPresent()) {
                    perfil.setId(0);
                    perfil.setTitulo("DEFAULT");
                } else {
                    perfil.setId(p.get().getId());
                    perfil.setTitulo(p.get().getTitulo());
                }
                request.setUuid(u.getUuid());
                request.setNome(u.getNome());
                request.setTelefone(u.getTelefone());
                request.setEmail(u.getEmail());
                request.setSaldo(u.getSaldo());
                request.setRole(u.getRole().name());
                request.setIdPerfilActivo(perfil.getId());
                request.setNomePerfilActivo(perfil.getTitulo());
                request.setDataCriacao(u.getCreatedAt().toString());
                request.setDataActualizacao(u.getUpdatedAt().toString());

                System.out.println("Enviando request para: " + urlServidor);
                int response = replicacaoClient.registarUtilizadorServidorSlave(request, urlServidor);
                if (response == 201) {
                    System.out.println("[SUCESSO] Replicado para " + urlServidor);
                    return true;
                } else {
                    System.out.println("[FALHA] Código de resposta: " + response);
                }
            } catch (Exception ex) {
                System.out.println("[EXCEPTION] Erro ao replicar para " + urlServidor + ": " + ex);
            }
            tentativas++;
            try {
                Thread.sleep(1000 * tentativas);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }
    
}
