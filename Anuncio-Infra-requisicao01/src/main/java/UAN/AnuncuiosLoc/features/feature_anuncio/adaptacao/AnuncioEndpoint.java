package UAN.AnuncuiosLoc.features.feature_anuncio.adaptacao;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.anuncios.ws.local.AnuncioInfraResponse;
import com.anuncios.ws.local.AnuncioRegistoInfra;
import com.anuncios.ws.local.AnuncioRequest;
import com.anuncios.ws.local.PegarTodosAnunciosInfraRequest;
import com.anuncios.ws.local.PegarTodosAnunciosInfraResponse;

import UAN.AnuncuiosLoc.core.exception.ValorJaExisteException;
import UAN.AnuncuiosLoc.features.feature_anuncio.adaptacao.response.ResponseFactoryAnuncio;
import UAN.AnuncuiosLoc.features.feature_anuncio.aplicacao.PegarTodosAnunciosUserCase;
import UAN.AnuncuiosLoc.features.feature_anuncio.aplicacao.RegistarAnuncioUseCase;


@Endpoint
public class AnuncioEndpoint {
    private final RegistarAnuncioUseCase registarAnuncioUseCase;
    private final PegarTodosAnunciosUserCase pegarTodosAnunciosUserCase;

    @PostConstruct
    public void init() {
        System.out.println(" Endpoint `AnuncioEndpoint` instanciado!");
    }

    @Autowired
    public  AnuncioEndpoint(RegistarAnuncioUseCase registar, PegarTodosAnunciosUserCase pegarTodos){
        this.registarAnuncioUseCase = registar;
        this.pegarTodosAnunciosUserCase = pegarTodos;
    }

    private static final String NAMESPACE_URI = "http://ws.anuncios.com/local";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AnuncioRequest")
    @ResponsePayload
    public AnuncioInfraResponse registarAnuncio (@RequestPayload AnuncioRequest request){
        System.out.println("== CHAMANDO A END POINT REGISTAR ANUNCIO");
        AnuncioInfraResponse response = new AnuncioInfraResponse();
        try {
            AnuncioInfraResponse r = registarAnuncioUseCase.executar(request);
            response.setStatus(200);
            response.setIdAnuncioInfra(r.getIdAnuncioInfra());
            System.out.println(r.getIdAnuncioInfra());
            return response;
        } catch(ValorJaExisteException e){
            response.setStatus(409);
            return response;
        } catch (Exception e) {
            response.setStatus(500);
            return response;
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PegarTodosAnunciosInfraRequest")
    @ResponsePayload
    public PegarTodosAnunciosInfraResponse pegarTodosAnuncios (@RequestPayload PegarTodosAnunciosInfraRequest request){
        System.out.println("== CHAMANDO A END POINT PEGAR ANUNCIO");
        try {
            List<AnuncioRegistoInfra> r = pegarTodosAnunciosUserCase.executar();
            return ResponseFactoryAnuncio.pegarTodosAnunciosInfra( r, 200);
        } catch(ValorJaExisteException e){
            return ResponseFactoryAnuncio.pegarTodosAnunciosInfra( null, 409);
        } catch (Exception e) {
            return ResponseFactoryAnuncio.pegarTodosAnunciosInfra( null, 500);
        }
    }

}