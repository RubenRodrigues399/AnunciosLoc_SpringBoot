package UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.datasource;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.AnuncioEntidade;

@Repository
public interface AnuncioDataSource extends JpaRepository<AnuncioEntidade, Integer> {
    List<AnuncioEntidade> findByUuidCriador(String uuidCriador);
    List<AnuncioEntidade> findByUpdatedAtIsNotNullAndUpdatedAtGreaterThanEqual(LocalDateTime updatedAt);
}
