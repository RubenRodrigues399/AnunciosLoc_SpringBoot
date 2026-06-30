package GRUPOAERS.UAN.core.utils.configuracao_webservice;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.context.ApplicationContext;
import java.util.List;

@Configuration
@EnableWs
public class ConfiguracaoWebService extends WsConfigurerAdapter{

    // ==== SEGURANÇA 
    @Bean
    public InterceptorTokenSoap interceptorTokenSoap() {
        InterceptorTokenSoap interceptor = new  InterceptorTokenSoap();
        return interceptor;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(interceptorTokenSoap());
    }

    // ==== CONFIGURAÇÕES XSDS 
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    // ==== CONFIGURAÇÕES XSDS  AUTENTICAÇÃO
    @Bean(name = "autenticacao")
    public DefaultWsdl11Definition usuarioWsdlDefinition(XsdSchema utilizadorSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("AutenticacaoPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://ws.anuncios.com/autenticacao");
        definition.setSchema(utilizadorSchema);
        return definition;
    }
    
    @Bean
    public XsdSchema utilizadorSchema() {
        return new SimpleXsdSchema(new ClassPathResource("wsdl/autenticacao.xsd"));
    }


}
