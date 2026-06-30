
package GRUPOAERS.UAN.feature_autenticacao.adaptacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.anuncios.ws.autenticacao.ActualizacaoUtilizadorReplicacaoRequest;
import com.anuncios.ws.autenticacao.LocalRegisto;
import com.anuncios.ws.autenticacao.LoginRequest;
import com.anuncios.ws.autenticacao.LoginResponse;
import com.anuncios.ws.autenticacao.LogoutRequest;
import com.anuncios.ws.autenticacao.LogoutResponse;
import com.anuncios.ws.autenticacao.PerfilRegisto;
import com.anuncios.ws.autenticacao.RegistarLocalRequest;
import com.anuncios.ws.autenticacao.RegistarUtilizadorRequest;
import com.anuncios.ws.autenticacao.RegistarUtilizadorResponse;
import com.anuncios.ws.autenticacao.ReplicacaoResponse;
import com.anuncios.ws.autenticacao.UpdateRequest;
import com.anuncios.ws.autenticacao.UpdateResponse;
import com.anuncios.ws.autenticacao.UtilizadorLogin;
import com.anuncios.ws.autenticacao.UtilizadorRegisto;
import com.anuncios.ws.autenticacao.ValidarTicketRequest;
import com.anuncios.ws.autenticacao.ValidarTicketResponse;

import GRUPOAERS.UAN.core.exception.UtlizadorNaoAutorizado;
import GRUPOAERS.UAN.core.exception.ValorJaExisteException;
import GRUPOAERS.UAN.core.utils.configuracao_webservice.InterceptorTokenSoap;
import GRUPOAERS.UAN.feature_autenticacao.adaptacao.mappers.RoleUtilizadorMapper;
import GRUPOAERS.UAN.feature_autenticacao.adaptacao.mappers.UtilizadorMapper;
import GRUPOAERS.UAN.feature_autenticacao.adaptacao.response.ResponseFactoryAutenticacao;
import GRUPOAERS.UAN.feature_autenticacao.aplicacao.ActualizarPerfilUseCase;
import GRUPOAERS.UAN.feature_autenticacao.aplicacao.ActualizarUtilizadorUseCase;
import GRUPOAERS.UAN.feature_autenticacao.aplicacao.LoginWithEmailUseCase;
import GRUPOAERS.UAN.feature_autenticacao.aplicacao.LogoutUseCase;
import GRUPOAERS.UAN.feature_autenticacao.aplicacao.RegistarUtilizadorUseCase;
import GRUPOAERS.UAN.feature_autenticacao.aplicacao.ValidacaoTicketResponseUseCase;
import GRUPOAERS.UAN.feature_autenticacao.dominio.model.RegistoParams;
import GRUPOAERS.UAN.feature_autenticacao.dominio.model.RoleUtilizador;
import GRUPOAERS.UAN.feature_autenticacao.dominio.model.Utilizador;
import GRUPOAERS.UAN.feature_autenticacao.dominio.model.ValidacaoTicketResponse;


@Endpoint
public class AutenticacaoEndPoint {
    private static final String NAMESPACE_URI = "http://ws.anuncios.com/autenticacao";
    private final RegistarUtilizadorUseCase registarUtilizadorUseCase;
    private final LoginWithEmailUseCase loginWithEmailUseCase;
    private final LogoutUseCase logoutUseCase;
    private final ActualizarUtilizadorUseCase actualizarUtilizadorUseCase;
    private final ValidacaoTicketResponseUseCase validacaoTicketResponseUseCase;
    private final ActualizarPerfilUseCase actualizarPerfilUseCase;

    public AutenticacaoEndPoint(
        RegistarUtilizadorUseCase _registarUtilizador, LoginWithEmailUseCase loginWithEmailUseCase, LogoutUseCase logoutUseCase, 
        ActualizarUtilizadorUseCase actualizarUtilizadorUseCase,
        ValidacaoTicketResponseUseCase validacaoTicketResponseUseCase,
        ActualizarPerfilUseCase actualizarPerfilUseCase
    ){
        this.registarUtilizadorUseCase = _registarUtilizador;
        this.loginWithEmailUseCase = loginWithEmailUseCase;
        this.logoutUseCase = logoutUseCase;
        this.actualizarUtilizadorUseCase = actualizarUtilizadorUseCase;
        this.validacaoTicketResponseUseCase = validacaoTicketResponseUseCase;
        this.actualizarPerfilUseCase = actualizarPerfilUseCase;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RegistarUtilizadorRequest")
    @ResponsePayload
    public RegistarUtilizadorResponse registar (@RequestPayload RegistarUtilizadorRequest request){
        System.out.println("== CHAMANDO A END POINT");
        UtilizadorRegisto utilizadorRegisto = new UtilizadorRegisto();
        try {
            RoleUtilizador role = RoleUtilizadorMapper.toRole(request.getRole().toString());
            RegistoParams parms = new RegistoParams(request.getNome(),
             request.getEmail(),role, request.getSenha());
            Utilizador dados = new Utilizador();
             dados=registarUtilizadorUseCase.executar(parms);
            utilizadorRegisto = UtilizadorMapper.toUtilizadorRegisto(dados);
            return ResponseFactoryAutenticacao.registarUtilizador("Utilizador registado com sucesso", utilizadorRegisto, 201);

        } catch(ValorJaExisteException e){
            return ResponseFactoryAutenticacao.registarUtilizador(e.getMessage(), null, 409);
        } catch (Exception e) {
            return ResponseFactoryAutenticacao.registarUtilizador(e.getMessage(), null,500);
       
        }
        
    }
    
    // ENDPOINT LOGIN
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "LoginRequest")
    @ResponsePayload
    public LoginResponse login (@RequestPayload LoginRequest request){
        System.out.println("== CHAMANDO LOGIN");

        try {        
            Utilizador dados =loginWithEmailUseCase.executar(request.getEmail(), request.getSenha());
            UtilizadorLogin utilizadorLogin = new UtilizadorLogin();
            utilizadorLogin = UtilizadorMapper.toUtilizadorLogin(dados);
            return ResponseFactoryAutenticacao.login("Utilizador login feito com sucesso", utilizadorLogin, 201);

        } catch(UtlizadorNaoAutorizado e){
            return ResponseFactoryAutenticacao.login(e.getMessage(), null, 401);
        } catch (Exception e) {
            return ResponseFactoryAutenticacao.login(e.getMessage().toString(), null,500);
       
        }   
    }

    // ENDPOINT UPDATE
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ActualizacaoUtilizadorReplicacaoRequest")
    @ResponsePayload
    public ReplicacaoResponse update (@RequestPayload ActualizacaoUtilizadorReplicacaoRequest request){
        System.out.println("== CHAMANDO UPDATE");

        try {
        Logger logger = LoggerFactory.getLogger(InterceptorTokenSoap.class);
            logger.warn("========= PARAMETRIZOU");
            int resposta = actualizarUtilizadorUseCase.executar(request); 
            logger.warn("========= DADOS");
            return ResponseFactoryAutenticacao.replicacao("Utilizador Actualizado com sucesso", resposta);

        } catch(UtlizadorNaoAutorizado e){
            return ResponseFactoryAutenticacao.replicacao(e.getMessage(), 401);
        } catch(ValorJaExisteException e){
            return ResponseFactoryAutenticacao.replicacao(e.getMessage(),409);
        }
        catch (Exception e) {
            return ResponseFactoryAutenticacao.replicacao(e.getMessage().toString(),500);
       
        }   
    }

    // ENDPOINT VALIDAR TOKEN
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ValidarTicketRequest")
    @ResponsePayload
    public ValidarTicketResponse validarToken (@RequestPayload ValidarTicketRequest request){
        System.out.println("== CHAMANDO VALIDAR TICKET");
        try {
        Logger logger = LoggerFactory.getLogger(InterceptorTokenSoap.class);
          
        ValidacaoTicketResponse dados = validacaoTicketResponseUseCase.executar(request.getTicket()); 
            logger.warn("========= DADOS");
            return ResponseFactoryAutenticacao.validarTicket("Ticket Validado com sucesso",dados, 201);
        } catch(UtlizadorNaoAutorizado e){
        System.out.println("== RETORNANDO 401");
            return ResponseFactoryAutenticacao.validarTicket(e.getMessage(), null, 401);
        } catch(ValorJaExisteException e){
                System.out.println("== RETORNANDO 409");
            return ResponseFactoryAutenticacao.validarTicket(e.getMessage(), null, 409);
        } catch (Exception e) {
            System.out.println("== RETORNANDO 500");
            return ResponseFactoryAutenticacao.validarTicket(e.getMessage().toString(), null,500);
        } 
    }

    // ENDPOINT LOGOUT
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "LogoutRequest")
    @ResponsePayload
    public LogoutResponse logout (@RequestPayload LogoutRequest request){
        System.out.println("== CHAMANDO LOGOUT");
        try {
            logoutUseCase.executar(request.getUuid());
            return ResponseFactoryAutenticacao.logout("Logout feito com sucesso", 201);

        } catch(UtlizadorNaoAutorizado e){
            System.out.println(" ==== SAIU 1");
            return ResponseFactoryAutenticacao.logout(e.getMessage(), 401);
        } catch (Exception e) {
            System.out.println(" ==== SAIU 2");
            return ResponseFactoryAutenticacao.logout(e.getMessage().toString(),500);
       
        }   
    }

    // ENDPOINT ACTUALIZAR PERFIL
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PerfilRegisto")
    @ResponsePayload
    public ReplicacaoResponse registarPerfil (@RequestPayload PerfilRegisto request){
        System.out.println("== CHAMANDO A END POINT REGISTAR PERFIL ");
        try {
            int response = actualizarPerfilUseCase.executar(request);
            return ResponseFactoryAutenticacao.replicacao("Utilizador replicado com sucesso",response);

        } catch(ValorJaExisteException e){
            return ResponseFactoryAutenticacao.replicacao(e.getMessage(), 409);
        } catch (Exception e) {
            System.out.println("====== EXC "+ e.getMessage());
            return ResponseFactoryAutenticacao.replicacao(e.getMessage().toString(),500);
        }
    }

}
