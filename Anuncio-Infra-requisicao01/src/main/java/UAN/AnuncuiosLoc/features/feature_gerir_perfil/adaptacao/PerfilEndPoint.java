package UAN.AnuncuiosLoc.features.feature_gerir_perfil.adaptacao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.anuncios.ws.autenticacao.ReplicacaoResponse;
import com.anuncios.ws.perfil.AssociarPerfilRequest;
import com.anuncios.ws.perfil.AssociarPerfilResponse;
import com.anuncios.ws.perfil.PegarTodosPerfilRequest;
import com.anuncios.ws.perfil.PegarTodosPerfilResponse;
import com.anuncios.ws.perfil.PerfilRegisto;
import UAN.AnuncuiosLoc.core.exception.ValorJaExisteException;
import UAN.AnuncuiosLoc.core.exception.ValorNaoEncontrado;
import UAN.AnuncuiosLoc.features.feature_autenticacao.adaptacao.response.ResponseFactoryAutenticacao;
import UAN.AnuncuiosLoc.features.feature_autenticacao.aplicacao.ValidarTokenUseCase;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.adaptacao.response.ResponseFactoryPerfil;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.aplicacao.AssociarPerfilUseCase;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.aplicacao.PegarTodosPerfisUseCase;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.aplicacao.ActualizarPerfilUseCase;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.dominio.model.Perfil;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.mappers.PerfilMappers;



@Endpoint
public class PerfilEndPoint {
    private static final String NAMESPACE_URI = "http://ws.anuncios.com/perfil";
    private final ActualizarPerfilUseCase actualizarPerfilUseCase;
    private final PegarTodosPerfisUseCase pegarTodosPerfisUseCase;
    private final AssociarPerfilUseCase associarPerfilUseCase;
    PerfilEndPoint(
        ActualizarPerfilUseCase actualizarPerfilUseCase,
        PegarTodosPerfisUseCase pegarTodosPerfisUseCase,
        AssociarPerfilUseCase associarPerfilUseCase,
        ValidarTokenUseCase validarTokenUseCase
    ){
        this.actualizarPerfilUseCase = actualizarPerfilUseCase;
        this.pegarTodosPerfisUseCase = pegarTodosPerfisUseCase;
        this.associarPerfilUseCase = associarPerfilUseCase;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PerfilRegisto")
    @ResponsePayload
    public ReplicacaoResponse registarPerfil (@RequestPayload PerfilRegisto request){
        System.out.println("== CHAMANDO A END POINT REGISTAR PERFIL ");
        try {
            int response = actualizarPerfilUseCase.executar(request);
            return ResponseFactoryAutenticacao.replicacao("Utilizador replicado com sucesso", null,response);

        } catch(ValorJaExisteException e){
            return ResponseFactoryAutenticacao.replicacao(e.getMessage(), null, 409);
        } catch (Exception e) {
            System.out.println("====== EXC "+ e.getMessage());
            return ResponseFactoryAutenticacao.replicacao(e.getMessage().toString(), null,500);
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PegarTodosPerfilRequest")
    @ResponsePayload
    public PegarTodosPerfilResponse pegarTodos (@RequestPayload PegarTodosPerfilRequest request){

        System.out.println("== CHAMANDO A END POINT PEGAR TODOS PERFIL ");
        try {            
            List<PerfilRegisto> perfisRegisto = new ArrayList<>();
            List<Perfil> perfil = pegarTodosPerfisUseCase.executar();
            perfil.forEach((p)->{
                PerfilRegisto perfilAux = PerfilMappers.modelToPerfilRegisto(p);
                perfisRegisto.add(perfilAux);
            });
            return ResponseFactoryPerfil.pegarTodosPerfis("Perfis carregados com sucesso", perfisRegisto, 201);
        } catch(ValorNaoEncontrado e){
                    return ResponseFactoryPerfil.pegarTodosPerfis(e.getMessage(), null, 404);
                } catch(ValorJaExisteException e){
                    return ResponseFactoryPerfil.pegarTodosPerfis(e.getMessage(), null, 409);
                } catch (Exception e) {
                    return ResponseFactoryPerfil.pegarTodosPerfis(e.getMessage().toString(), null,500);
                }
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AssociarPerfilRequest")
    @ResponsePayload
    public AssociarPerfilResponse associarPerfil (@RequestPayload AssociarPerfilRequest request){
        System.out.println("== CHAMANDO A END POINT ASSOCIAR PERFIL ");
        try {
            associarPerfilUseCase.executar(request.getUuidUtilizador(), request.getIdPerfil());
            return ResponseFactoryPerfil.associarPerfil("Perfíl associado com sucesso", 201);
        } catch(ValorNaoEncontrado e){
            return ResponseFactoryPerfil.associarPerfil(e.getMessage(),  404);
        } catch(ValorJaExisteException e){
            return ResponseFactoryPerfil.associarPerfil(e.getMessage(), 409);
        } catch (Exception e) {
            return ResponseFactoryPerfil.associarPerfil(e.getMessage().toString(),500);
        }   
    }


}
