package UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.datasource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.AnuncioEntidade;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.LeitorEntidade;
@Repository
public interface LeitorDataSource extends JpaRepository<LeitorEntidade, Integer> {
    Optional<LeitorEntidade> findByIdAnuncioAndUuidLeitor(int idAnuncio, String uuidLeitor);
    List<LeitorEntidade> findByUpdatedAtIsNotNullAndUpdatedAtGreaterThanEqual(LocalDateTime updatedAt);
}
