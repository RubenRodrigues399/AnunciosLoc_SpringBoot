package UAN.AnuncuiosLoc.features.feature_gerir_perfil.aplicacao;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.anuncios.ws.perfil.PerfilRegisto;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.dominio.repositorio.PerfilRepository;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.entidade.PerfilEntidade;

@Service
public class ActualizarPerfilUseCase {
    private final PerfilRepository repository;
    ActualizarPerfilUseCase(PerfilRepository repository){
        this.repository = repository;
    }

    public int executar (PerfilRegisto request){
        PerfilEntidade perfil = new PerfilEntidade();
        perfil.setId(request.getId());
        perfil.setTitulo(request.getTitulo());
        perfil.setUuidUtilizadorCriador(request.getUuidUtilizadorCriador());
        perfil.setDataCriacao(LocalDateTime.parse(request.getDataCriacao()));
        repository.salvar(perfil);
        return 201;

    }
}
