package GRUPOAERS.UAN.feature_autenticacao.aplicacao;

import org.springframework.stereotype.Service;

import GRUPOAERS.UAN.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;


@Service
public class LogoutUseCase {
    private final AutenticacaoRepository repositorioAutenticacao;
    
    public LogoutUseCase(AutenticacaoRepository repository){
        this.repositorioAutenticacao = repository;
    }

    public void executar (String uuidString){
        repositorioAutenticacao.logout(uuidString);
    }   
    
}
