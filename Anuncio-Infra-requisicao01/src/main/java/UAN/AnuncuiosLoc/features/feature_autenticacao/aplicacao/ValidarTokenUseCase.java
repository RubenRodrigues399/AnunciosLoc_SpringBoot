package UAN.AnuncuiosLoc.features.feature_autenticacao.aplicacao;

import org.springframework.stereotype.Service;

import com.anuncios.ws.autenticacao.ValidarTicketResponse;

import UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;

@Service
public class ValidarTokenUseCase {
    private final AutenticacaoRepository repositorioAutenticacao;
    
    public ValidarTokenUseCase(AutenticacaoRepository repository){
        this.repositorioAutenticacao = repository;
    }

    public ValidarTicketResponse executar (String token){
       return repositorioAutenticacao.validarTicket(token);
    }
}
