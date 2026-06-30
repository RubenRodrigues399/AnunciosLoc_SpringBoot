package UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_webservice.filtroscors;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCorsFiltro implements WebMvcConfigurer { 
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/ws/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "OPTIONS")  // SOAP usa principalmente POST
                .allowedHeaders("Content-Type", "SOAPAction")
                .exposedHeaders("Content-Type", "SOAPAction")
                .allowCredentials(false)
                .maxAge(3600);
    }
}