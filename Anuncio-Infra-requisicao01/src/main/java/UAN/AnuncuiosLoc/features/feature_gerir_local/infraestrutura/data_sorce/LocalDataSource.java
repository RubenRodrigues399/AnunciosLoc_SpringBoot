package UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.data_sorce;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.entidade.LocalEntidade;

@Repository
public interface LocalDataSource extends JpaRepository<LocalEntidade, Integer>{
        Optional<LocalEntidade> findByNome(String nome);
        Optional<LocalEntidade> findByLatitudeAndLongitudeAndRaio(double latitude, double longitude, double raio);
        Optional<LocalEntidade> findByUrl(String url);
        List<LocalEntidade> findByUpdatedAtIsNotNullAndUpdatedAtGreaterThanEqual(LocalDateTime updatedAt);
        
}
