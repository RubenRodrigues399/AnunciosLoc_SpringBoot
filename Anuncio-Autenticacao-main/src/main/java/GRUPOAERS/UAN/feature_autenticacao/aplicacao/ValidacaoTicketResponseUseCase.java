package GRUPOAERS.UAN.feature_autenticacao.aplicacao;

import java.security.GeneralSecurityException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import GRUPOAERS.UAN.core.exception.UtlizadorNaoAutorizado;
import GRUPOAERS.UAN.core.exception.ValorNaoEncontrado;
import GRUPOAERS.UAN.core.services.CriptografiaService;
import GRUPOAERS.UAN.feature_autenticacao.dominio.model.Utilizador;
import GRUPOAERS.UAN.feature_autenticacao.dominio.model.ValidacaoTicketResponse;
import GRUPOAERS.UAN.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.services.TicketService;

@Service
public class ValidacaoTicketResponseUseCase {
    private final AutenticacaoRepository repositorioAutenticacao;
    public ValidacaoTicketResponseUseCase(AutenticacaoRepository repository){
        this.repositorioAutenticacao = repository;
    }

    public ValidacaoTicketResponse executar (String ticket) throws GeneralSecurityException{
        boolean valido = TicketService.validateTicket(ticket);
        if (!valido) {
             throw new UtlizadorNaoAutorizado("Ticket inválido");   
        }
        String ticketDescriptografado= CriptografiaService.decryptTicket(ticket);
        String[] parts = ticketDescriptografado.split("\\|");
        String uuid = parts[2];

        Optional<Utilizador> utilizadorFindUuid= repositorioAutenticacao.findByUuid(uuid);
        System.out.println("========= DADOS01");
        if (!utilizadorFindUuid.isPresent()) {
            throw new ValorNaoEncontrado("Utilizador inválido");
        }
        System.out.println("========= DADOS01");
        Utilizador utilizador = utilizadorFindUuid.get();
        System.out.println("========= DADOS02");
        return new ValidacaoTicketResponse(uuid, utilizador.getNome(), utilizador.getRole(), utilizador.getSaldo());
  
    }   
    
}
