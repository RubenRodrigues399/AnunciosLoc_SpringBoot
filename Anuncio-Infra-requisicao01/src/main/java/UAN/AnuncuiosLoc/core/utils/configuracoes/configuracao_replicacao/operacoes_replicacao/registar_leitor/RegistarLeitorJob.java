// package UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.operacoes_replicacao.registar_leitor;

// import java.time.LocalDateTime;
// import java.util.List;

// import org.quartz.Job;
// import org.quartz.JobExecutionContext;
// import org.quartz.JobExecutionException;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import com.anuncios.ws.local.LeitorRegisto;
// import UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.ultima_execucao_controller.RoleUltimaExecucao;
// import UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.ultima_execucao_controller.UltimaExecucaoService;
// import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.datasource.LeitorDataSource;
// import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.LeitorEntidade;

// @Component
// public class RegistarLeitorJob implements Job{
//    @Autowired
//     private LeitorDataSource leitorDataSource;;
//     @Autowired
//     private RegistarLeitorClient registarLeitorClient;
//     @Autowired
//     private UltimaExecucaoService ultimaExecucaoService;
    
//     private static final String CHAVE_ROLE = RoleUltimaExecucao.LEITOR.toString();
//     // private static final String URL_SERVIDOR_CENTRAL = "https://anunciosloccentral.onrender.com/ws";
//     private static final String URL_SERVIDOR_CENTRAL = "http://localhost:8080/ws";

//     @Override
//     public void execute(JobExecutionContext context) throws JobExecutionException {

//         System.out.println("=== INICIANDO REPLICACAO [ACTUALIZAR LEITORES] : ");

//         LocalDateTime ultimaDataReplicacao = ultimaExecucaoService.getUltimaData(CHAVE_ROLE);

//         List<LeitorEntidade> modificados =leitorDataSource
//         .findByUpdatedAtIsNotNullAndUpdatedAtGreaterThanEqual(ultimaDataReplicacao);

//         LocalDateTime menorDataErro = ultimaDataReplicacao;
//         boolean sucessoTotal = true; 
        
//         for (LeitorEntidade u : modificados) {
//             System.out.println("=== QTD MODIFICADOS: " + modificados.size());
//             boolean sucesso = tentarReplicarComRetry(u, 3);
//             if (!sucesso) {
//                sucessoTotal =false;
//                if (menorDataErro.isEqual(ultimaDataReplicacao)) {
//                     menorDataErro = u.getUpdatedAt(); 
//                     System.out.println("==== DATA ERRO "+ menorDataErro);
//                }

//             }
//         }
//         if (sucessoTotal) {
//             ultimaExecucaoService.atualizarUltimaExecucaoNaReplicacao(CHAVE_ROLE);
//         } else {
//             ultimaExecucaoService.atualizarUltimaExecucaoNaReplicacaoErro(CHAVE_ROLE, menorDataErro);
//         }

//     }

//     private boolean tentarReplicarComRetry(LeitorEntidade u, int maxTentativas) {
//     int tentativas = 0;
//     while (tentativas < maxTentativas) {
//         try {
//                 LeitorRegisto request = new LeitorRegisto();
//                 request.setId(u.getId());
//                 request.setIdAnuncio(u.getIdAnuncio());
//                 request.setUuidLeitor(u.getUuidLeitor());
//                 request.setDataLeitura(u.getCreatedAt().toString());
//             int response = registarLeitorClient.registarLeitorNoServidorSlave(request, URL_SERVIDOR_CENTRAL);
            
          
//                 if (response == 201) {
//                     System.out.println("[SUCESSO] Replicado para " + URL_SERVIDOR_CENTRAL);
//                     return true;
//                 } else {
//                     System.out.println("[FALHA] Código de resposta: " + response);
//                 }
//         } catch (Exception ex) {
//             System.out.println("==== EXP AO REPLICAR {} " + ex);
//             // Logar erro
//         }
//         tentativas++;
//         try {
//             Thread.sleep(1000 * tentativas); // Backoff exponencial
//         } catch (InterruptedException ie) {
//             Thread.currentThread().interrupt();
//             return false;
//         }
//     }
//     return false;
//     }
    
// }
