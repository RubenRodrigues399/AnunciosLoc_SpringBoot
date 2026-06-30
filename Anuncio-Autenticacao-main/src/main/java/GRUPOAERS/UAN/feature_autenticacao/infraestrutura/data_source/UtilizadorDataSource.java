package GRUPOAERS.UAN.feature_autenticacao.infraestrutura.data_source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UtilizadorDataSource  extends JpaRepository<UtilizadorEntidade, String>{
    Optional<UtilizadorEntidade> findByEmail(String email);
    Optional<UtilizadorEntidade> findByNome(String nome);
    Optional<UtilizadorEntidade> findByTelefone(String telefone);
    List<UtilizadorEntidade> findByCreatedAtGreaterThanEqualOrUpdatedAtGreaterThanEqual(LocalDateTime created, LocalDateTime updated);
    List<UtilizadorEntidade> findByUpdatedAtIsNotNullAndUpdatedAtGreaterThanEqual(LocalDateTime updated);
}
