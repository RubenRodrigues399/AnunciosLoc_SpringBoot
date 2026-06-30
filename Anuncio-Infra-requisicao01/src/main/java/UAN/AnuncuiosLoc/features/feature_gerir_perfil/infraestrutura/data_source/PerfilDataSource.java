package UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.data_source;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.entidade.PerfilEntidade;

@Repository
public interface PerfilDataSource extends JpaRepository<PerfilEntidade, Integer>{ 
    Optional<PerfilEntidade> findByTitulo(String titulo);

}
