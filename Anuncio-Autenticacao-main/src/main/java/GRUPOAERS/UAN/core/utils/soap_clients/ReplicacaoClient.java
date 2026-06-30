package GRUPOAERS.UAN.core.utils.soap_clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.oxm.MarshallingFailureException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import com.anuncios.ws.autenticacao.ReplicacaoResponse;
import com.anuncios.ws.autenticacao.UtilizadorRegistoReplicacaoRequest;

@Component
public class ReplicacaoClient {

    private final Jaxb2Marshaller marshaller;

    public ReplicacaoClient(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public int registarUtilizadorServidorSlave(UtilizadorRegistoReplicacaoRequest request, String urlServidor) {
        try {
            WebServiceTemplate template = new WebServiceTemplate(marshaller);
            System.out.println("Enviando requisição para: " + urlServidor);
            System.out.println(request);
            ReplicacaoResponse response = (ReplicacaoResponse) template
                    .marshalSendAndReceive(urlServidor, request,
                            new SoapActionCallback("http://ws.anuncios.com/autenticacao/UtilizadorRegistoReplicacaoRequest"));
            System.out.println("==== RESPOSTA DO SERVIDOR. STATUS CODE = " + response.getStatusCode());
            return response.getStatusCode();
        } catch (MarshallingFailureException e) {
            System.err.println("Erro ao serializar o objeto: " + e.getMessage());
            e.printStackTrace();
            return -1; 
        }
    }
}