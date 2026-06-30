// package UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.operacoes_replicacao.registar_anuncio;

// import org.springframework.oxm.jaxb.Jaxb2Marshaller;
// import org.springframework.stereotype.Component;
// import org.springframework.ws.client.core.WebServiceTemplate;
// import org.springframework.ws.soap.client.core.SoapActionCallback;
// import com.anuncios.ws.autenticacao.ReplicacaoResponse;
// import com.anuncios.ws.local.AnuncioRegisto;

// @Component
// public class RegistarAnuncioClient {
//     private final Jaxb2Marshaller marshaller;

//     public RegistarAnuncioClient(Jaxb2Marshaller marshaller){
//         this.marshaller = marshaller;
//     }

//     public int registarAnuncioNoServidorSlave(AnuncioRegisto request, String  urlServidor) {
//         WebServiceTemplate template = new WebServiceTemplate(marshaller);
//         System.out.println("== REGISTAR ANUNCIO NO SLAVE: ");
//         System.out.println("== ENVIANDO REQUISIÇÃO: " + urlServidor);
//         ReplicacaoResponse response = (ReplicacaoResponse) template
//                 .marshalSendAndReceive(urlServidor, request,
//                         new SoapActionCallback("http://ws.anuncios.com/local/AnuncioRegisto"));
//         System.out.println("== RESPOSTA DO SLAVE. STATUS: " + response.getStatusCode());
//         return response.getStatusCode();
//     }
    
// }
