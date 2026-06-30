package GRUPOAERS.UAN.core.utils.configuracao_webservice;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;
import GRUPOAERS.UAN.core.exception.UtlizadorNaoAutorizado;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.services.TicketService;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import java.util.Iterator;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;


public class InterceptorTokenSoap implements EndpointInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(InterceptorTokenSoap.class);
    
    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
        SoapMessage soapMessage = (SoapMessage) messageContext.getRequest();
        String soapAction = soapMessage.getSoapAction();
        
        // Debug da ação SOAP
        logger.debug("Processando ação SOAP: {}", soapAction);
        
        // Ações públicas que não requerem autenticação
        Set<String> publicActions = Set.of(
            "http://ws.anuncios.com/autenticacao/LoginRequest",
            "http://ws.anuncios.com/autenticacao/RegistarUtilizadorRequest",
            "http://ws.anuncios.com/autenticacao/ValidarTicketRequest",
            "http://ws.anuncios.com/autenticacao/ActualizacaoUtilizadorReplicacaoRequest",
            "http://ws.anuncios.com/autenticacao/PerfilRegisto");
        
        if (soapAction != null && publicActions.contains(soapAction.replace("\"", "").trim())) {
            logger.warn("=== PODE PASSAR");
            logger.warn("Ação pública - bypass de autenticação");
            return true;
        }
        
        try {
            // Extrai token e UUID
            logger.warn("=== NÃO PODE PASSAR");

            String token = extractTokenFromSoapHeader(soapMessage);
            String uuid = extractUuidFromSoapBody(soapMessage);
            
            logger.debug("Token extraído: {}", token);
            logger.debug("UUID extraído: {}", uuid);
            
            if (!TicketService.validateTicketWithUser(token, uuid)) {
                logger.warn("Falha na validação do token {} para UUID {}", token, uuid);
                throw new UtlizadorNaoAutorizado("Credenciais inválidas");
            }
            
            logger.debug("Autenticação válida para UUID {}", uuid);
            return true;
            
        } catch (UtlizadorNaoAutorizado e) {
            logger.error("Erro de autenticação: {}", e.getMessage());
            throw e; // Reenvia a exceção original
        } catch (Exception e) {
            logger.error("Erro no processamento: {}", e.getMessage());
            throw new UtlizadorNaoAutorizado("Erro no processamento da requisição");
        }
    }
    
    private String extractTokenFromSoapHeader(SoapMessage soapMessage) {
        try {
            SoapHeader header = soapMessage.getSoapHeader();
            if (header == null) {
                throw new UtlizadorNaoAutorizado("Cabeçalho SOAP ausente");
            }
            
            Iterator<SoapHeaderElement> it = header.examineAllHeaderElements();
            while (it.hasNext()) {
                SoapHeaderElement element = it.next();
                if ("token".equals(element.getName().getLocalPart())) {
                    return element.getText();
                }
            }
            
            throw new UtlizadorNaoAutorizado("Token não encontrado no cabeçalho");
            
        } catch (Exception e) {
            throw new UtlizadorNaoAutorizado("Erro ao extrair token: " + e.getMessage());
        }
    }
    
    private String extractUuidFromSoapBody(SoapMessage soapMessage) {
        try {
            Source source = soapMessage.getSoapBody().getPayloadSource();
            if (!(source instanceof DOMSource)) {
                throw new UtlizadorNaoAutorizado("Formato de corpo SOAP não suportado");
            }
            
            Node node = ((DOMSource) source).getNode();
            Document doc = node.getNodeType() == Node.DOCUMENT_NODE 
                ? (Document) node 
                : node.getOwnerDocument();
                
            // Usa XPath para extrair o UUID independentemente do namespace
            XPath xpath = XPathFactory.newInstance().newXPath();
            Node uuidNode = (Node) xpath.evaluate(
                "//*[local-name()='uuid']", 
                doc, 
                XPathConstants.NODE
            );
            
            if (uuidNode == null) {
                throw new UtlizadorNaoAutorizado("UUID não encontrado no corpo");
            }
            
            return uuidNode.getTextContent();
            
        } catch (XPathExpressionException e) {
            throw new UtlizadorNaoAutorizado("Erro ao processar XML: " + e.getMessage());
        } catch (Exception e) {
            throw new UtlizadorNaoAutorizado("Erro ao extrair UUID: " + e.getMessage());
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
        logger.error("Ocorreu uma falha no processamento SOAP");
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
    }

}