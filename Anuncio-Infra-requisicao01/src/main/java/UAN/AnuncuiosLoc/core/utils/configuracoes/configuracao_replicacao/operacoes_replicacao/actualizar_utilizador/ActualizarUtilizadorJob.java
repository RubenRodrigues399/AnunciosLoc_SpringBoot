package UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.operacoes_replicacao.actualizar_utilizador;

import java.time.LocalDateTime;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anuncios.ws.autenticacao.ActualizacaoUtilizadorReplicacaoRequest;

import UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.ultima_execucao_controller.UltimaExecucaoService;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.data_source.UtilizadorDataSource;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;
import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.data_sorce.LocalDataSource;
import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.entidade.LocalEntidade;

@Component
public class ActualizarUtilizadorJob implements Job{
    @Autowired
    private UtilizadorDataSource utilizadorDataSource;
    @Autowired
    private ActuallizarUtilizadorClient actuallizarUtilizadorClient;
    @Autowired
    private UltimaExecucaoService ultimaExecucaoService;
    @Autowired
    private LocalDataSource jpaLocal;


    private static final String CHAVE_ROLE = "UTILIZADOR";
    private static final LocalEntidade URL_SERVIDOR_AUTH = new LocalEntidade(0, "AUTENTICACAO", 0, 0, 0,"http://localhost:8083/ws", null, null);
    //https://anuncios-auth.onrender.com
    // private static final LocalEntidade URL_SERVIDOR_AUTH = new LocalEntidade(0, "AUTENTICACAO", 0, 0, 0,"https://anuncios-auth.onrender.com/ws", null, null);
    
    


    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        List<LocalEntidade> locais = jpaLocal.findAll();
        locais.add(URL_SERVIDOR_AUTH);
        for (LocalEntidade local : locais) {
            String chaveExecucao = CHAVE_ROLE + "_" + local.getNome();

            LocalDateTime ultimaDataReplicacao = ultimaExecucaoService.getUltimaData(chaveExecucao);

            List<UtilizadorEntidade> modificados = utilizadorDataSource
                .findByUpdatedAtIsNotNullAndUpdatedAtGreaterThanEqual(ultimaDataReplicacao);

            LocalDateTime menorDataErro = ultimaDataReplicacao;
            boolean sucessoTotal = true;      
            System.out.println("=== INICIANDO REPLICACAO PARA: " + local.getNome());
            System.out.println("=== QTD MODIFICADOS: " + modificados.size());
            
            for (UtilizadorEntidade u : modificados) {
                System.out.println("Tentando replicar utilizador: " + u.getUuid());
                boolean sucesso = tentarReplicarComRetry(u, 3, local.getUrl());
                if (!sucesso) {
                    sucessoTotal = false;
                    System.out.println("Falha ao replicar para " + local.getNome() + " -> UUID: " + u.getUuid());
                    if (menorDataErro.isEqual(ultimaDataReplicacao)) {
                        menorDataErro = u.getUpdatedAt();
                    }
                }
            }
            
        }

    }



    private boolean tentarReplicarComRetry(UtilizadorEntidade u, int maxTentativas, String urlServidor) {
    int tentativas = 0;
    
    while (tentativas < maxTentativas) {
        try {
                ActualizacaoUtilizadorReplicacaoRequest request = new ActualizacaoUtilizadorReplicacaoRequest();
                request.setUuid(u.getUuid());
                request.setNome(u.getNome());
                request.setEmail(u.getEmail());
                request.setTelefone(u.getTelefone());
                request.setSaldo(u.getSaldo());
                request.setIdPerfilActivo(u.getIdPerfilActivo());
                request.setNomePerfilActivo(u.getNomePerfilActivo());
                request.setDataActualizacao(u.getUpdatedAt().toString());
    
                System.out.println("Enviando request para: " + urlServidor);
                int response = actuallizarUtilizadorClient.actualizarUtilizadorNoServidorSlave(request, urlServidor);
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
