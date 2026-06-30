package GRUPOAERS.UAN.core.utils.soap_clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.anuncios.ws.autenticacao.LocalRegisto;
import com.anuncios.ws.autenticacao.ReplicacaoResponse;
import com.anuncios.ws.autenticacao.UtilizadorRegistoReplicacaoRequest;


@Configuration
public class SoapClient {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(
            ReplicacaoResponse.class,
            UtilizadorRegistoReplicacaoRequest.class,
            LocalRegisto.class
        );
        return marshaller;
    }    
}
