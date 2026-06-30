package UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.data_source;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade.SessaoEntidade;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;


@Repository
public interface SessaoDataSource extends JpaRepository<SessaoEntidade, Integer> {
    Optional<SessaoEntidade> findByUtilizadorEntidadeAndDataFimIsNull(UtilizadorEntidade utilizadorEntidade);
}
