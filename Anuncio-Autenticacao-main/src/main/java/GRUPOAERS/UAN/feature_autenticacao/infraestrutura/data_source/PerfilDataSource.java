package GRUPOAERS.UAN.feature_autenticacao.infraestrutura.data_source;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;

import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade.PerfilEntidade;

@Repository
public interface PerfilDataSource extends JpaRepository<PerfilEntidade, Integer>{ 
    Optional<PerfilEntidade> findByTitulo(String titulo);

}
