package UAN.AnuncuiosLoc.features.feature_gerir_local.dominio.repositorio;

import java.util.List;
import java.util.Optional;
import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.entidade.LocalEntidade;

public interface LocalRepository {
    LocalEntidade registarLocal(LocalEntidade request);
    Optional<LocalEntidade> findByNome(String nome);
    Optional<LocalEntidade> findById(Integer id);
    Optional<LocalEntidade> findByUrl(String url);
    Optional<LocalEntidade> findByLatAndLotAndRaio(double latitude, double longitude, double raio);
    List<LocalEntidade> pegarLocaisPorCoordenadas(double latitude, double longitude);
    List<LocalEntidade> pegarTodosLocais();
}
