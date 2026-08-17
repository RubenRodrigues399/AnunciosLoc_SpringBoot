package UAN.AnuncuiosLoc.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import UAN.AnuncuiosLoc.service.LocalService;
import UAN.AnuncuiosLoc.service.AnuncioService;
import UAN.AnuncuiosLoc.soap.PegarTodosLocaisRequest;
import UAN.AnuncuiosLoc.soap.PegarTodosLocaisResponse;
import UAN.AnuncuiosLoc.soap.RegistarLocalRequest;
import UAN.AnuncuiosLoc.soap.RegistarLocalResponse;
import UAN.AnuncuiosLoc.soap.PegarAnunciosPorCoordenadasRequest;
import UAN.AnuncuiosLoc.soap.TodosAnunciosResponseInfra;

@Endpoint
public class LocalEndpoint {

    private static final String NAMESPACE_URI =
            "http://ws.anuncios.com/local";

    @Autowired
    private LocalService localService;

    @Autowired
    private AnuncioService anuncioService;

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "RegistarLocalRequest")
    @ResponsePayload
    public RegistarLocalResponse registar(
            @RequestPayload RegistarLocalRequest request) {

        return localService.registar(request);
    }

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "PegarTodosLocaisRequest")
    @ResponsePayload
    public PegarTodosLocaisResponse listar(
            @RequestPayload PegarTodosLocaisRequest request) {

        return localService.listar();
    }

    @PayloadRoot(
            namespace = NAMESPACE_URI,
            localPart = "PegarAnunciosPorCoordenadasRequest")
    @ResponsePayload
    public TodosAnunciosResponseInfra pegarAnunciosPorCoordenadas(
            @RequestPayload PegarAnunciosPorCoordenadasRequest request) {

        return anuncioService.pegarAnunciosPorCoordenadas(request);
    }

}