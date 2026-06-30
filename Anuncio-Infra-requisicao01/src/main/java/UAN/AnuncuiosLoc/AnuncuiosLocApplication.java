package UAN.AnuncuiosLoc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ws.config.annotation.EnableWs;

import ao.uan.fc.dam.ws.uddi.UDDINaming;

@EnableWs
@SpringBootApplication
@ComponentScan("UAN")
public class AnuncuiosLocApplication{

	public static void main(String[] args) {
		SpringApplication.run(AnuncuiosLocApplication.class, args);
		System.out.println("SERVIDOR INFRAESTRUTURA 1 RODANDO");
	}

	@Bean
//	@Profile("!test")
	public CommandLineRunner run() throws Exception {
		return args -> {
			UDDINaming juddiService = null;
			try{
				// publish to UDDI
				System.out.printf("PUBLICANDO '%s' NO UDDI %s%n", "CXX_INFRAESTRUTURA1", "http://localhost:9090");
				juddiService = new UDDINaming("http://localhost:9090");
				juddiService.rebind("CXX_INFRAESTRUTURA1", "http://localhost:8084/ws");

				// wait
				System.out.println("AGUARDANDO CONEXÃO");
				System.out.println("Press enter to shutdown");
				System.in.read();
			} catch (Exception e) {
				System.out.printf("Caught exception: %s%n", e);
				e.printStackTrace();
			} finally {
				try {
					if (juddiService != null) {
						// delete from UDDI
						juddiService.unbind("CXX_INFRAESTRUTURA1");
						System.out.printf("Deleted '%s' from UDDI%n", "CXX_INFRAESTRUTURA1");
					}
				} catch (Exception e) {
					System.out.printf("Caught exception when deleting: %s%n", e);
				}
			}
		};
	}

}
