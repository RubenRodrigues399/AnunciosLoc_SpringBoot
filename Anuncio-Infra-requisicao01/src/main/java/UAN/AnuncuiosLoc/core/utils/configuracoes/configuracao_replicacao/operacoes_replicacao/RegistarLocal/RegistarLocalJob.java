package UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.operacoes_replicacao.RegistarLocal;

import java.time.LocalDateTime;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anuncios.ws.local.LocalRegisto;
import com.anuncios.ws.local.RegistarLocalRequest;

import UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.ultima_execucao_controller.RoleUltimaExecucao;
import UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.ultima_execucao_controller.UltimaExecucaoService;
import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.data_sorce.LocalDataSource;
import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.entidade.LocalEntidade;

@Component
public class RegistarLocalJob implements Job{
    @Autowired
    private LocalDataSource localDataSource;
    @Autowired
    private RegistarLocalClient registarLocalClient;
    @Autowired
    private UltimaExecucaoService ultimaExecucaoService;

    private static final String CHAVE_ROLE = RoleUltimaExecucao.LOCAL.toString();
    private static final String URL_SERVIDOR_AUTH = "http://localhost:8083/ws";
    // String URL_SERVIDOR_AUTH = "https://anuncios-auth.onrender.com/ws";
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        LocalDateTime ultimaDataReplicacao = ultimaExecucaoService.getUltimaData(CHAVE_ROLE);

        List<LocalEntidade> modificados =localDataSource
        .findByUpdatedAtIsNotNullAndUpdatedAtGreaterThanEqual(ultimaDataReplicacao);

        LocalDateTime menorDataErro = ultimaDataReplicacao;
        boolean sucessoTotal = true; 
        
        for (LocalEntidade u : modificados) {
            System.out.println("== TEM DADOS PARA REPLICAR ");
            boolean sucesso = tentarReplicarComRetry(u, 3);
            if (!sucesso) {
               sucessoTotal =false;
               if (menorDataErro.isEqual(ultimaDataReplicacao)) {
                    menorDataErro = u.getUpdatedAt(); 
                    System.out.println("==== DATA ERRO "+ menorDataErro);
               }

            }
        }
        if (sucessoTotal) {
            ultimaExecucaoService.atualizarUltimaExecucaoNaReplicacao(CHAVE_ROLE);
        } else {
            ultimaExecucaoService.atualizarUltimaExecucaoNaReplicacaoErro(CHAVE_ROLE, menorDataErro);
        }

    }



    private boolean tentarReplicarComRetry(LocalEntidade u, int maxTentativas) {
    int tentativas = 0;
    while (tentativas < maxTentativas) {
        try {
                LocalRegisto request = new LocalRegisto();
                request.setId(u.getId());
                request.setLatitude(u.getLatitude());
                request.setLongitude(u.getLongitude());
                request.setNome(u.getNome());
                request.setRaio(u.getRaio());
                request.setUrl(u.getUrl());
            int response = registarLocalClient.registarLocalNoServidorSlave(request, URL_SERVIDOR_AUTH);
                if (response == 201) {
                    System.out.println("[SUCESSO] Replicado para " + URL_SERVIDOR_AUTH);
                    return true;
                } else {
                    System.out.println("[FALHA] Código de resposta: " + response);
                }
        } catch (Exception ex) {
            System.out.println("==== EXP AO REPLICAR {} " + ex);
            // Logar erro
        }
        tentativas++;
        try {
            Thread.sleep(1000 * tentativas); // Backoff exponencial
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
    return false;
    }

    
}
