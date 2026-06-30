package UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.operacoes_replicacao.actualizar_utilizador;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.anuncios.ws.autenticacao.ActualizacaoUtilizadorReplicacaoRequest;
import com.anuncios.ws.autenticacao.ReplicacaoResponse;

@Component
public class ActuallizarUtilizadorClient {
    private final Jaxb2Marshaller marshaller;

    public ActuallizarUtilizadorClient(Jaxb2Marshaller marshaller){
        this.marshaller = marshaller;
    }

    public int actualizarUtilizadorNoServidorSlave(ActualizacaoUtilizadorReplicacaoRequest request, String  urlServidor) {
        WebServiceTemplate template = new WebServiceTemplate(marshaller);
        System.out.println("Enviando requisição para: " + urlServidor);
        ReplicacaoResponse response = (ReplicacaoResponse) template
                .marshalSendAndReceive(urlServidor, request,
                        new SoapActionCallback("http://ws.anuncios.com/autenticacao/ActualizacaoUtilizadorReplicacaoRequest"));
        System.out.println("==== RESPOSTA DO SERVIDOR. STATUS CODE = " + response.getStatusCode());
        return response.getStatusCode();
    }
    
}
