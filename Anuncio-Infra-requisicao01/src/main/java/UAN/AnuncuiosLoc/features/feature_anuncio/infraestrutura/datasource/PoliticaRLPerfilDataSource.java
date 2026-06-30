package UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.datasource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.*;
import java.util.List;
import java.util.Optional;


@Repository
public interface PoliticaRLPerfilDataSource extends JpaRepository<PoliticaRLPerfilEntidade, Integer> {
    Optional<List<PoliticaRLPerfilEntidade>> findByIdAnuncio(int idAnuncio);
    
}
