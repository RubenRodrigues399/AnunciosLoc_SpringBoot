package UAN.AnuncuiosLoc.features.feature_autenticacao.aplicacao;

import org.springframework.stereotype.Service;

import UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;

@Service
public class LogoutUseCase {
    private final AutenticacaoRepository repositorioAutenticacao;
    
    public LogoutUseCase(AutenticacaoRepository repository){
        this.repositorioAutenticacao = repository;
    }

    public void executar (String uuidString){
        repositorioAutenticacao.TerminarSessao(uuidString);
    }    
}
