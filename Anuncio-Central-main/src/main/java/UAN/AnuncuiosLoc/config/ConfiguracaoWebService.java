package UAN.AnuncuiosLoc.config.configuracao_webservice;

import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.adapter.DefaultMethodEndpointAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

//import UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_seguranca.InterceptorTokenSoap;

import org.springframework.context.ApplicationContext;



@Configuration
@EnableWs
@ComponentScan(basePackages = "UAN.AnuncuiosLoc")
public class ConfiguracaoWebService extends WsConfigurerAdapter{

    //==== SEGURANÇA
    //@Bean
    //public InterceptorTokenSoap interceptorTokenSoap() {
        //InterceptorTokenSoap interceptor = new  InterceptorTokenSoap();
        //return interceptor;
   // }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
       // interceptors.add(interceptorTokenSoap());
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

    //@Bean
    //public XsdSchema utilizadorSchema() {
    //    return new SimpleXsdSchema(new ClassPathResource("wsdl/autenticacao.xsd"));
    //}

    // ==== CONFIGURAÇÕES XSDS PERFIL
    @Bean(name = "perfil")
    public DefaultWsdl11Definition perfilWsdlDefinition(XsdSchema perfilSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("PerfilPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://ws.anuncios.com/perfil");
        definition.setSchema(perfilSchema);
        return definition;
    }

    @Bean
    public XsdSchema perfilSchema() {
        return new SimpleXsdSchema(new ClassPathResource("wsdl/perfil.xsd"));
    }

    // ==== CONFIGURAÇÕES XSDS LOCAL
    @Bean(name = "local")
    public DefaultWsdl11Definition localWsdlDefinition(XsdSchema localSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("LocalPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://ws.anuncios.com/local");
        definition.setSchema(localSchema);
        return definition;
    }

    @Bean
    public XsdSchema localSchema() {
        return new SimpleXsdSchema(new ClassPathResource("wsdl/local.xsd"));
    }

    @Bean(name = "utilizador")
    public DefaultWsdl11Definition utilizadorWsdlDefinition(
            XsdSchema utilizadorSchema) {

        DefaultWsdl11Definition definition =
                new DefaultWsdl11Definition();

        definition.setPortTypeName("UtilizadorPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://ws.anuncios.com/utilizador");
        definition.setSchema(utilizadorSchema);

        return definition;
    }

    @Bean
    public XsdSchema utilizadorSchema() {
        return new SimpleXsdSchema(
                new ClassPathResource("wsdl/utilizador.xsd"));
    }

}
