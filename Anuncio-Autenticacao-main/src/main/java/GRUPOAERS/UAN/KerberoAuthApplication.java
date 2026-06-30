package GRUPOAERS.UAN;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ws.config.annotation.EnableWs;

@EnableWs
@SpringBootApplication
@ComponentScan("GRUPOAERS.UAN")
public class KerberoAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(KerberoAuthApplication.class, args);
		System.out.println("==== SERVIDOR DE AUTENTICAÇÃO ON");
	}
}
