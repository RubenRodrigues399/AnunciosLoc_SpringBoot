package UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.services;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.anuncios.ws.autenticacao.ValidarTicketRequest;
import com.anuncios.ws.autenticacao.ValidarTicketResponse;


public class TicketClient extends WebServiceGatewaySupport {
    
    public ValidarTicketResponse validarTicket(String ticket) {
        ValidarTicketRequest request = new ValidarTicketRequest();
        request.setTicket(ticket);
        String URL_SERVIDOR_AUTH = "http://localhost:8083/ws";
        // String URL_SERVIDOR_AUTH = "https://anuncios-auth.onrender.com/ws";
        return (ValidarTicketResponse) getWebServiceTemplate()
                .marshalSendAndReceive(URL_SERVIDOR_AUTH, request,
                        new SoapActionCallback("http://ws.anuncios.com/autenticacao/ValidarTicketRequest"));
    }
}
