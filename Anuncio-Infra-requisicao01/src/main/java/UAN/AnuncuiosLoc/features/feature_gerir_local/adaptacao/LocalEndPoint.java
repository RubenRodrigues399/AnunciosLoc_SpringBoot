package UAN.AnuncuiosLoc.features.feature_gerir_local.adaptacao;

import java.util.List;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.anuncios.ws.local.PegarTodosLocaisRequest;
import com.anuncios.ws.local.PegarTodosLocaisResponse;
import com.anuncios.ws.local.RegistarLocalRequest;
import com.anuncios.ws.local.RegistarLocalResponse;
import UAN.AnuncuiosLoc.core.exception.ValorJaExisteException;
import UAN.AnuncuiosLoc.features.feature_gerir_local.adaptacao.response.ResponseFactoryLocal;
import UAN.AnuncuiosLoc.features.feature_gerir_local.aplicacao.PegarTodosLocaisUseCase;
import UAN.AnuncuiosLoc.features.feature_gerir_local.aplicacao.RegistarLocalUseCase;
import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.entidade.LocalEntidade;


@Endpoint
public class LocalEndPoint {
    private final RegistarLocalUseCase registarLocalUseCase;
    private final PegarTodosLocaisUseCase pegarTodosLocaisUseCase;

    LocalEndPoint(RegistarLocalUseCase _registarLocalUseCase, PegarTodosLocaisUseCase _pegarTodosLocaisUseCase){
        this.registarLocalUseCase = _registarLocalUseCase;
        this.pegarTodosLocaisUseCase =_pegarTodosLocaisUseCase;
    }

    private static final String NAMESPACE_URI = "http://ws.anuncios.com/local";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RegistarLocalRequest")
    @ResponsePayload
    public RegistarLocalResponse registar (@RequestPayload RegistarLocalRequest request){
        System.out.println("== CHAMANDO A END POINT REGISTAR LOCAL");
        System.out.println("REQ OBJ: " + request);
        System.out.println("REQ NOME: " + request.getNome());
        System.out.println("REQ URL: " + request.getUrl());
        try {
            LocalEntidade response = registarLocalUseCase.executar(request);
            return ResponseFactoryLocal.registarLocal("Local registado com sucesso.", response, 201);

        } catch(ValorJaExisteException e){
            return ResponseFactoryLocal.registarLocal(e.getMessage(), null, 409);
        } catch (Exception e) {
        System.out.println("====== EXC "+ e.getMessage());
                   return ResponseFactoryLocal.registarLocal(e.getMessage(), null, 500);
        }
        
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PegarTodosLocaisRequest")
    @ResponsePayload
    public PegarTodosLocaisResponse registar (@RequestPayload PegarTodosLocaisRequest request){
        System.out.println("== CHAMANDO A END POINT PEGAR TODOS LOCAIS");
        try {
           List<LocalEntidade> locais = pegarTodosLocaisUseCase.executar();
            return ResponseFactoryLocal.pegarTodosLocais("Locais carregados com sucesso.", locais, 201);

        } catch(ValorJaExisteException e){
            return ResponseFactoryLocal.pegarTodosLocais(e.getMessage(), null, 409);
        } catch (Exception e) {
        System.out.println("====== EXC "+ e.getMessage());
                   return ResponseFactoryLocal.pegarTodosLocais(e.getMessage(), null, 500);
        }
        
    }
    
}
