package UAN.AnuncuiosLoc.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import UAN.AnuncuiosLoc.service.UtilizadorService;
import UAN.AnuncuiosLoc.soap.RegistarUtilizadorRequest;
import UAN.AnuncuiosLoc.soap.RegistarUtilizadorResponse;

@Endpoint
public class UtilizadorEndpoint {

    private static final String NAMESPACE_URI =
            "http://ws.anuncios.com/autenticacao";

    @Autowired
    private UtilizadorService utilizadorService;

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "RegistarUtilizadorRequest")
    @ResponsePayload
    public RegistarUtilizadorResponse registarUtilizador(
            @RequestPayload RegistarUtilizadorRequest request) {

        return utilizadorService.registar(request);
    }
}