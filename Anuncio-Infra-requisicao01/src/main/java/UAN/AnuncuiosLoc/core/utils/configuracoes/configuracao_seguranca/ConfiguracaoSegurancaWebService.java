package UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_seguranca;
// package UAN.AnuncuiosLoc.core.utils.configuracoes;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.Ordered;
// import org.springframework.core.annotation.Order;
// import org.springframework.ws.config.annotation.WsConfigurerAdapter;
// import org.springframework.ws.context.MessageContext;
// import org.springframework.ws.server.EndpointInterceptor;
// import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
// import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
// import org.springframework.ws.server.EndpointInterceptor;
// import org.springframework.ws.server.endpoint.interceptor.EndpointInterceptorAdapter;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;


// @Configuration
// public class ConfiguracaoSegurancaWebService extends WsConfigurerAdapter{


//     @Override
//     public void addInterceptors(List<EndpointInterceptor> interceptors) {
//         interceptors.add(securityInterceptor());
//     }

//     @Bean
//     public Wss4jSecurityInterceptor securityInterceptor() {
//         Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        
//         // Configuração para validação de requisições recebidas
//         securityInterceptor.setValidationActions("Timestamp UsernameToken");
//         securityInterceptor.setValidationCallbackHandler(callbackHandler());
        
//         // Configuração para segurança de mensagens enviadas (opcional)
//         // securityInterceptor.setSecurementActions("Timestamp UsernameToken");
//         // securityInterceptor.setSecurementUsername("username");
//         // securityInterceptor.setSecurementPassword("password");
        
//         return securityInterceptor;
//     }
    
//     @Bean
//     public SimplePasswordValidationCallbackHandler callbackHandler() {
//         SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
//         Map<String, String> users = new HashMap<>();
//         users.put("admin", "senha123"); // usuário e senha
//         callbackHandler.setUsersMap(users);
//         return callbackHandler;
//     }


// }
