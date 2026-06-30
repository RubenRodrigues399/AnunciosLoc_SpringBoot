package UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.services;

import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import com.anuncios.ws.autenticacao.UtilizadorTicket;
import com.anuncios.ws.autenticacao.ValidarTicketRequest;
import com.anuncios.ws.autenticacao.ValidarTicketResponse;
import com.anuncios.ws.local.AnuncioRegisto;
import com.anuncios.ws.local.AnuncioRequest;
import com.anuncios.ws.local.PegarTodosAnunciosInfraRequest;
import com.anuncios.ws.local.PegarTodosAnunciosInfraResponse;

@Component
public class SoapClient {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // marshaller.setContextPath("com.anuncios.ws.autenticacao"); // o pacote gerado
        marshaller.setClassesToBeBound(
            ValidarTicketRequest.class,
            ValidarTicketResponse.class,
            UtilizadorTicket.class,
            AnuncioRegisto.class,
            AnuncioRequest.class,
            PegarTodosAnunciosInfraResponse.class,
            PegarTodosAnunciosInfraRequest.class,
            PegarTodosAnunciosInfraResponse.class
        );
        return marshaller;
    }

    @Bean(name = "ticketClientBean")
    public TicketClient ticketClient(Jaxb2Marshaller marshaller) {
        String URL_SERVIDOR_AUTH = "http://localhost:8083/ws";
        
        // String URL_SERVIDOR_AUTH = "https://anuncios-auth.onrender.com/ws";
        TicketClient client = new TicketClient();
        client.setDefaultUri(URL_SERVIDOR_AUTH);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
