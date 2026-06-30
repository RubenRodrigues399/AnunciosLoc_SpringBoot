package GRUPOAERS.UAN.feature_autenticacao.infraestrutura.services;
import java.security.GeneralSecurityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import GRUPOAERS.UAN.core.exception.ValorNaoEncontrado;
import GRUPOAERS.UAN.core.services.CriptografiaService;
import GRUPOAERS.UAN.core.utils.configuracao_webservice.InterceptorTokenSoap;
import GRUPOAERS.UAN.feature_autenticacao.dominio.model.Utilizador;



@Service
public class TicketService {

   public static String generateServiceTicket(Utilizador utilizador) throws GeneralSecurityException {
    
    if (utilizador == null || utilizador.getRole() == null) {
        throw new ValorNaoEncontrado("Utilizador e role não podem ser nulos");
    }

        String role = utilizador.getRole().name(); // se for enum, pega o nome exato

    // Permite somente roles esperadas, por segurança
    if (!role.equals("PUBLICADOR") && !role.equals("ADMIN")) {
        throw new ValorNaoEncontrado("Role inválida: " + role);
    }

        String ticket = String.format("ST|%s|%s|%d", role, utilizador.getUuid(), System.currentTimeMillis());
        return CriptografiaService.encryptTicket(ticket);
    }

     public static boolean validateTicketWithUser(String ticket, String uuidUser) {
             Logger logger = LoggerFactory.getLogger(InterceptorTokenSoap.class);
        try {
            String decrypted = CriptografiaService.decryptTicket(ticket);
            String[] parts = decrypted.split("\\|");

            logger.warn("=========PASSOU LOG 1");
            if (parts.length != 4) return false;
            logger.warn("=========PASSOU LOG 2");
            if ((!parts[0].equals("ST"))) 
                return false;
            logger.warn("=========PASSOU LOG 3");
            logger.warn("=========PASSOU {}", parts[1]);
            if ((!parts[1].equals("PUBLICADOR")) && (!parts[1].equals("ADMIN"))) {
                return false;
            }
            logger.warn("=========PASSOU LOG 4");
            if (!(parts[2].equals(uuidUser))) {
                return false;
            }
            logger.warn("=========PASSOU LOG 5");

            long creationTime = Long.parseLong(parts[3]);

            logger.warn("=========PASSOU {}", (System.currentTimeMillis() - creationTime <= 24 * 60 * 60 * 1000));
            
            return System.currentTimeMillis() - creationTime <= 24 * 60 * 60 * 1000;

        } catch (Exception e) {
        
            return false;
        }
    }

     public static boolean validateTicket(String ticket) {
        try {
            String decrypted = CriptografiaService.decryptTicket(ticket);
            String[] parts = decrypted.split("\\|");
             Logger logger = LoggerFactory.getLogger(InterceptorTokenSoap.class);
            logger.warn("=========PASSOU LOG 1");
            if (parts.length != 4) return false;
            logger.warn("=========PASSOU LOG 2");
            if ((!parts[0].equals("ST"))) 
                return false;
            logger.warn("=========PASSOU LOG 3");
            logger.warn("=========PASSOU {}", parts[1]);
            if ((!parts[1].equals("PUBLICADOR")) && (!parts[1].equals("ADMIN"))) {
                return false;
            }
            logger.warn("=========PASSOU LOG 5");

            long creationTime = Long.parseLong(parts[3]);

            logger.warn("=========PASSOU {}", (System.currentTimeMillis() - creationTime <= 24 * 60 * 60 * 1000));
            
            return System.currentTimeMillis() - creationTime <= 24 * 60 * 60 * 1000;

        } catch (Exception e) {
            return false;
        }
    }
}
