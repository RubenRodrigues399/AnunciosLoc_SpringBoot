package UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.operacoes_replicacao.RegistarLocal;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import com.anuncios.ws.autenticacao.ReplicacaoResponse;
import com.anuncios.ws.local.LocalRegisto;
import com.anuncios.ws.local.RegistarLocalRequest;

@Component
public class RegistarLocalClient {
    private final Jaxb2Marshaller marshaller;

    public RegistarLocalClient(Jaxb2Marshaller marshaller){
        this.marshaller = marshaller;
    }

    public int registarLocalNoServidorSlave(LocalRegisto request, String  urlServidor) {
        WebServiceTemplate template = new WebServiceTemplate(marshaller);
        System.out.println("== REGISTAR LOCAL NO SLAVE: ");
        System.out.println("== ENVIANDO REQUISIÇÃO: " + urlServidor);
        ReplicacaoResponse response = (ReplicacaoResponse) template
                .marshalSendAndReceive(urlServidor, request,
                        new SoapActionCallback("http://ws.anuncios.com/local/LocalRegisto"));
        System.out.println("== RESPOSTA DO SLAVE. STATUS: " + response.getStatusCode());
        return response.getStatusCode();
    }
    
}
