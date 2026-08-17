package UAN.AnuncuiosLoc.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import UAN.AnuncuiosLoc.service.AnuncioService;
import UAN.AnuncuiosLoc.soap.AnuncioRequest;
import UAN.AnuncuiosLoc.soap.AnuncioResponse;
import UAN.AnuncuiosLoc.soap.PegarAnunciosDoUsuarioRequest;
import UAN.AnuncuiosLoc.soap.TodosAnunciosResponseInfra;
import UAN.AnuncuiosLoc.soap.PegarAnunciosDoLocalRequest;

@Endpoint
public class AnuncioEndpoint {

    private static final String NAMESPACE_URI =
            "http://ws.anuncios.com/local";

    @Autowired
    private AnuncioService anuncioService;

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "AnuncioRequest")
    @ResponsePayload
    public AnuncioResponse criarAnuncio(
            @RequestPayload AnuncioRequest request) {

        return anuncioService.registar(request);
    }

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "PegarAnunciosDoUsuarioRequest")
    @ResponsePayload
    public TodosAnunciosResponseInfra pegarAnunciosDoUsuario(
            @RequestPayload PegarAnunciosDoUsuarioRequest request) {

        return anuncioService.pegarAnunciosDoUsuario(request);
    }

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "PegarAnunciosDoLocalRequest")
    @ResponsePayload
    public TodosAnunciosResponseInfra pegarAnunciosDoLocal(
            @RequestPayload PegarAnunciosDoLocalRequest request) {

        return anuncioService.pegarAnunciosDoLocal(request);
    }

}