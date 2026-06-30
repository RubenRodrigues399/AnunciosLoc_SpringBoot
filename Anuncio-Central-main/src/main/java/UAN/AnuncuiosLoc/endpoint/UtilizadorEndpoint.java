package UAN.AnuncuiosLoc.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import UAN.AnuncuiosLoc.service.UtilizadorService;

// importar as classes SOAP geradas do teu XSD

@Endpoint
public class UtilizadorEndpoint {

    private static final String NAMESPACE_URI = "http://ws.anuncios.com/utilizador";

    @Autowired
    private UtilizadorService utilizadorService;

}