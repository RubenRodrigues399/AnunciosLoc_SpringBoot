package UAN.AnuncuiosLoc.features.feature_autenticacao.adaptacao;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import UAN.AnuncuiosLoc.core.exception.UtlizadorNaoAutorizado;
import UAN.AnuncuiosLoc.core.exception.ValorJaExisteException;
import UAN.AnuncuiosLoc.core.exception.ValorNaoEncontrado;
import UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_seguranca.InterceptorTokenSoap;
import UAN.AnuncuiosLoc.features.feature_autenticacao.adaptacao.response.ResponseFactoryAutenticacao;
import UAN.AnuncuiosLoc.features.feature_autenticacao.aplicacao.ActualizarUtilizadorUseCase;
import UAN.AnuncuiosLoc.features.feature_autenticacao.aplicacao.ConsultarSaldoUseCase;
import UAN.AnuncuiosLoc.features.feature_autenticacao.aplicacao.LogoutUseCase;
import UAN.AnuncuiosLoc.features.feature_autenticacao.aplicacao.PegarTodosUtilizadoresUseCase;
import UAN.AnuncuiosLoc.features.feature_autenticacao.aplicacao.RegistarUtilizadorUseCase;
import UAN.AnuncuiosLoc.features.feature_autenticacao.aplicacao.ValidarTokenUseCase;

import com.anuncios.ws.autenticacao.ActualizacaoUtilizadorReplicacaoRequest;
import com.anuncios.ws.autenticacao.ConsultarSaldoRequest;
import com.anuncios.ws.autenticacao.ConsultarSaldoResponse;
import com.anuncios.ws.autenticacao.LogoutRequest;
import com.anuncios.ws.autenticacao.LogoutResponse;
import com.anuncios.ws.autenticacao.PegarTodosUtilizadoresRequest;
import com.anuncios.ws.autenticacao.PegarTodosUtilizadoresResponse;
import com.anuncios.ws.autenticacao.ReplicacaoResponse;
import com.anuncios.ws.autenticacao.UtilizadorRegisto;
import com.anuncios.ws.autenticacao.UtilizadorRegistoReplicacaoRequest;
import com.anuncios.ws.autenticacao.ValidarTicketRequest;
import com.anuncios.ws.autenticacao.ValidarTicketResponse;

@Endpoint
public class AutenticacaoEndPoint {
    private static final String NAMESPACE_URI = "http://ws.anuncios.com/autenticacao";
    private final RegistarUtilizadorUseCase registarUtilizadorUseCase;
    private final ActualizarUtilizadorUseCase actualizarUtilizadorUseCase;
    private final ValidarTokenUseCase validarTokenUseCase;
    private final PegarTodosUtilizadoresUseCase pegarTodosUtilizadoresUseCase;
    private final ConsultarSaldoUseCase consultarSaldoUseCase;

    public AutenticacaoEndPoint(RegistarUtilizadorUseCase _registarUtilizador, ActualizarUtilizadorUseCase actualizarUtilizadorUseCase
    ,ValidarTokenUseCase validarTokenUseCase, PegarTodosUtilizadoresUseCase pegarTodosUtilizadoresUseCase,
    ConsultarSaldoUseCase consultarSaldoUseCase
    ){
        this.registarUtilizadorUseCase = _registarUtilizador;
        this.actualizarUtilizadorUseCase = actualizarUtilizadorUseCase;
        this.validarTokenUseCase = validarTokenUseCase;
        this.pegarTodosUtilizadoresUseCase = pegarTodosUtilizadoresUseCase;
        this.consultarSaldoUseCase = consultarSaldoUseCase;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UtilizadorRegistoReplicacaoRequest")
    @ResponsePayload
    public ReplicacaoResponse registar (@RequestPayload UtilizadorRegistoReplicacaoRequest request){
        System.out.println("== CHAMANDO A END POINT");
        try {
            int response = registarUtilizadorUseCase.executar(request);
            return ResponseFactoryAutenticacao.replicacao("Utilizador replicado com sucesso", null,response);

        } catch(ValorJaExisteException e){
            return ResponseFactoryAutenticacao.replicacao(e.getMessage(), null, 409);
        } catch (Exception e) {
            System.out.println("====== EXC "+ e.getMessage());
            return ResponseFactoryAutenticacao.replicacao(e.getMessage().toString(), null,500);
        }
        
    }

    



    // // VALIDAR TOKEN
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ValidarTicketRequest")
    @ResponsePayload
    public ValidarTicketResponse logout (@RequestPayload ValidarTicketRequest request){
        System.out.println("== CHAMANDO VALIDAR TOKEN");
        try {
            ValidarTicketResponse response = validarTokenUseCase.executar(request.getTicket());
            return ResponseFactoryAutenticacao.validarToken(response.getMensagem(),response ,response.getStatusCode());

        } catch(UtlizadorNaoAutorizado e){
            System.out.println(" ==== SAIU 1");
            return ResponseFactoryAutenticacao.validarToken(e.getMessage(),null,401);
        } catch (Exception e) {
            System.out.println(" ==== SAIU 2");
            return ResponseFactoryAutenticacao.validarToken(e.getMessage().toString(),null,500);
        }   
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PegarTodosUtilizadoresRequest")
    @ResponsePayload
    public PegarTodosUtilizadoresResponse registar (@RequestPayload PegarTodosUtilizadoresRequest request){
        System.out.println("== CHAMANDO A END POINT");
        try {
            List<UtilizadorRegisto> response = pegarTodosUtilizadoresUseCase.executar();
            return ResponseFactoryAutenticacao.pegarUtilizadores("Utilizador replicado com sucesso", response,201);

        } catch(ValorJaExisteException e){
            return ResponseFactoryAutenticacao.pegarUtilizadores(e.getMessage(), null, 409);
        } catch (Exception e) {
            System.out.println("====== EXC "+ e.getMessage());
            return ResponseFactoryAutenticacao.pegarUtilizadores(e.getMessage().toString(), null,500);
        }
        
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ConsultarSaldoRequest")
    @ResponsePayload
    public ConsultarSaldoResponse registar (@RequestPayload ConsultarSaldoRequest request){
        System.out.println("== CHAMANDO A END POINT");
        try {
            String saldo = consultarSaldoUseCase.executar(request.getUuidUtilizador());
            return ResponseFactoryAutenticacao.consultarSaldo("Saldo consultado com sucesso",saldo,201);

        } catch(ValorNaoEncontrado e){
            return ResponseFactoryAutenticacao.consultarSaldo(e.getMessage(), null, 409);
        } catch (Exception e) {
            System.out.println("====== EXC "+ e.getMessage());
            return ResponseFactoryAutenticacao.consultarSaldo(e.getMessage().toString(), null,500);
        }
        
    }

    // ENDPOINT UPDATE
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ActualizacaoUtilizadorReplicacaoRequest")
    @ResponsePayload
    public ReplicacaoResponse actualizarUtilizador (@RequestPayload ActualizacaoUtilizadorReplicacaoRequest request){
        System.out.println("== CHAMANDO A END POINT UPDATE UTILIZADOR");
        try {
            int resposta = actualizarUtilizadorUseCase.executar(request); 
            return ResponseFactoryAutenticacao.replicacao("Utilizador Actualizado com sucesso", null,201);

        } catch(UtlizadorNaoAutorizado e){
            return ResponseFactoryAutenticacao.replicacao(e.getMessage(), null,401);
        } catch(ValorJaExisteException e){
            return ResponseFactoryAutenticacao.replicacao(e.getMessage(),null,409);
        }
        catch (Exception e) {
            return ResponseFactoryAutenticacao.replicacao(e.getMessage().toString(),null,500);
       
        }   
    }

}
