package UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;
import com.anuncios.ws.autenticacao.ValidarTicketResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import UAN.AnuncuiosLoc.core.exception.UtlizadorNaoAutorizado;
import UAN.AnuncuiosLoc.features.feature_autenticacao.aplicacao.ValidarTokenUseCase;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class InterceptorTokenSoap implements EndpointInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(InterceptorTokenSoap.class);
    
    @Autowired
    private ValidarTokenUseCase validarTokenUseCase;

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
        SoapMessage soapMessage = (SoapMessage) messageContext.getRequest();
        String soapAction = soapMessage.getSoapAction();
    // Debug mais completo
    System.out.println("SOAP Action recebida (raw): [{}] " + soapAction);
    System.out.println("SOAP Action raw value: " + soapAction);
    
    // Limpa a ação removendo aspas e espaços
    String cleanedAction = soapAction != null ? 
        soapAction.replace("\"", "").trim() : 
        null;
    System.out.println("LIMPADO "+ cleanedAction);
    
    logger.debug("SOAP Action limpa: [{}]", cleanedAction);
    
    // Ações que não requerem token
    Set<String> publicActions = new HashSet<>(Arrays.   asList(
    "http://ws.anuncios.com/autenticacao/UtilizadorRegistoReplicacaoRequest",
    "http://ws.anuncios.com/autenticacao/PerfilRegisto",
    "http://ws.anuncios.com/local/AnuncioRegisto",
    "http://ws.anuncios.com/autenticacao/ActualizacaoUtilizadorReplicacaoRequest",
    "http://ws.anuncios.com/local/AnuncioRequest",
    "http://ws.anuncios.com/local/PegarTodosAnunciosInfraRequest",
    "http://ws.anuncios.com/local/PegarTodosAnunciosInfraResponse"
    ));
    
    // Verifica se é uma ação pública
    if (cleanedAction != null && publicActions.contains(cleanedAction)) {
        System.out.println(">>> É LIVRE <<<");
        return true;
    }
        // Depois verifica o token para outras ações
        try {
            String token = extractTokenFromSoapHeader(soapMessage);
            
            ValidarTicketResponse response = validarTokenUseCase.executar(token);
            //TicketContext.setToken(token);
            if (response.getStatusCode() == 401) {
                throw new UtlizadorNaoAutorizado("Erro na autenticação");
            }
            if (response.getStatusCode() != 201) {
                throw new Exception("Erro na autenticação");
            }
        } catch (UtlizadorNaoAutorizado e) {
            SoapMessage response = (SoapMessage) messageContext.getResponse();
            SoapBody body = response.getSoapBody();
            body.addClientOrSenderFault("Token Inválido", Locale.ENGLISH);
            return false;
        } catch (Exception e) {
            SoapMessage response = (SoapMessage) messageContext.getResponse();
            SoapBody body = response.getSoapBody();
            body.addClientOrSenderFault("Falha no servidor", Locale.ENGLISH);
            return false;
        }
        return true;
    }

    private String extractTokenFromSoapHeader(SoapMessage soapMessage) {
        try {
            SoapHeader header = soapMessage.getSoapHeader();
            if (header == null) {
                logger.warn("Cabeçalho SOAP não encontrado");
                throw new UtlizadorNaoAutorizado("Insira o cabeçalho da requisição.");
            }
            
            Iterator<SoapHeaderElement> it = header.examineAllHeaderElements();
            while (it.hasNext()) {
                SoapHeaderElement element = it.next();
                if (element.getName().getLocalPart().equals("token")) {
                    String token = element.getText();
                    logger.warn(" =========== Token extraído do cabeçalho SOAP {}", token);
                    return token;
                }
            }
            
            logger.error("Elemento 'token' não encontrado no cabeçalho SOAP");
            throw new UtlizadorNaoAutorizado("Token não encontrado.");
            
        } catch (Exception e) {
            logger.error("Erro ao extrair token do cabeçalho SOAP: {}", e.getMessage());
            throw new UtlizadorNaoAutorizado("Erro ao processar cabeçalho: " + e.getMessage());
        }
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
        // Pode ser usado para modificar a resposta antes de enviar ao cliente
        logger.debug("Processando resposta SOAP");
        return true; // Continuar o processamento
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
        // Pode ser usado para tratar falhas SOAP
        logger.error("== OCORREU UMA FALHA NO PROCESSAMENTO");
        return true; // Continuar o processamento
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {
        // Chamado após conclusão do processamento (sucesso ou falha)
        // Pode ser usado para limpeza de recursos
        if (ex != null) {
            logger.error("Erro durante processamento SOAP: {}", ex.getMessage());
        } else {
            logger.debug("Requisição SOAP processada com sucesso");
        }
        TicketContext.clear();
    }
}