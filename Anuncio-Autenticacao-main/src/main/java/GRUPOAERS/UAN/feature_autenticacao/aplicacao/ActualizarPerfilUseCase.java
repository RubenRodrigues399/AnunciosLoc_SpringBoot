package GRUPOAERS.UAN.feature_autenticacao.aplicacao;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.anuncios.ws.autenticacao.PerfilRegisto;

import GRUPOAERS.UAN.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade.PerfilEntidade;

@Service
public class ActualizarPerfilUseCase {
    private final AutenticacaoRepository repository;

    ActualizarPerfilUseCase(AutenticacaoRepository repository){
        this.repository = repository;
    }

    public int executar (PerfilRegisto request){
        PerfilEntidade perfil = new PerfilEntidade();
        perfil.setId(request.getId());
        perfil.setTitulo(request.getTitulo());
        perfil.setUuidUtilizadorCriador(request.getUuidUtilizadorCriador());
        perfil.setDataCriacao(LocalDateTime.parse(request.getDataCriacao()));
        repository.savarPerfil(perfil);
        return 201;
    }

}
