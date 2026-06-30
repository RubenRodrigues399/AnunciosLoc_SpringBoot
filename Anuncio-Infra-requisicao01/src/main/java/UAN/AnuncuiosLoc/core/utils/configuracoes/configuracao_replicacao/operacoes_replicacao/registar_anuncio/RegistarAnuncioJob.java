// package UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.operacoes_replicacao.registar_anuncio;

// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import org.quartz.Job;
// import org.quartz.JobExecutionContext;
// import org.quartz.JobExecutionException;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import com.anuncios.ws.local.AnuncioRegisto;
// import com.anuncios.ws.local.AnuncioRequest;
// import com.anuncios.ws.local.PoliticaRLPerfilRegisto;

// import UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.ultima_execucao_controller.RoleUltimaExecucao;
// import UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.ultima_execucao_controller.UltimaExecucaoService;
// import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.datasource.AnuncioDataSource;
// import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.datasource.PoliticaRLPerfilDataSource;
// import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.AnuncioEntidade;
// import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.PoliticaRLPerfilEntidade;
// import UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.data_source.PerfilDataSource;
// import UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.entidade.PerfilEntidade;

// @Component
// public class RegistarAnuncioJob implements Job{
//     @Autowired
//     private AnuncioDataSource anuncioDataSource;
//     @Autowired
//     private PoliticaRLPerfilDataSource politicaRLPerfilDataSource;
//     @Autowired
//     private PerfilDataSource perfilDataSource;
//     @Autowired
//     private RegistarAnuncioClient registarAnuncioClient;
//     @Autowired
//     private UltimaExecucaoService ultimaExecucaoService;

//     private static final String CHAVE_ROLE = RoleUltimaExecucao.ANUNCIO.toString();
//     private static final String URL_SERVIDOR_CENTRAL = "http://localhost:8080/ws";

//     // private static final String URL_SERVIDOR_CENTRAL = "https://anunciosloccentral.onrender.com/ws";

//     @Override
//     public void execute(JobExecutionContext context) throws JobExecutionException {

//         LocalDateTime ultimaDataReplicacao = ultimaExecucaoService.getUltimaData(CHAVE_ROLE);

//         List<AnuncioEntidade> modificados =anuncioDataSource.findByUpdatedAtIsNotNullAndUpdatedAtGreaterThanEqual(ultimaDataReplicacao);

//         LocalDateTime menorDataErro = ultimaDataReplicacao;
//         boolean sucessoTotal = true; 
        
//         for (AnuncioEntidade u : modificados) {
//             System.out.println("== TEM DADOS PARA REPLICAR ");
//             Optional<List<PoliticaRLPerfilEntidade>> politicas = politicaRLPerfilDataSource.findByIdAnuncio(u.getId());
//             List<PoliticaRLPerfilEntidade> pols = new ArrayList<PoliticaRLPerfilEntidade>();

//             if (politicas.isPresent()) {
//                 pols.addAll(politicas.get());
//             }
//             boolean sucesso = tentarReplicarComRetry(u, 3, pols);
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

//     private boolean tentarReplicarComRetry(AnuncioEntidade u, int maxTentativas, List<PoliticaRLPerfilEntidade> politicas) {
//     int tentativas = 0;
//     while (tentativas < maxTentativas) {
//         try {
//                 AnuncioRegisto request = new AnuncioRegisto();
//                 request.getId();
//                 request.setDescricao(u.getDescricao());
//                 request.setRolePolitica(u.getRolePoliticaAnuncio().toString());
//                 request.setUuidCriador(u.getUuidCriador());
//                 request.setTitulo(u.getTitulo());
//                 for (PoliticaRLPerfilEntidade p : politicas) {
//                     PoliticaRLPerfilRegisto polRegisto = new PoliticaRLPerfilRegisto();
//                     polRegisto.setId(p.getId());
//                     polRegisto.setIdAnuncio(p.getIdAnuncio());
//                     polRegisto.setIdPerfil(p.getIdPerfil());
//                     Optional<PerfilEntidade> perfilOpt = perfilDataSource.findById(p.getIdPerfil());
//                     if (!perfilOpt.isPresent()) {
//                         continue;
//                     }
//                     polRegisto.setNomePerfil(perfilOpt.get().getTitulo());
//                     request.getPerfis().add(polRegisto);
//                 }
//             int response = registarAnuncioClient.registarAnuncioNoServidorSlave(request, URL_SERVIDOR_CENTRAL);
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