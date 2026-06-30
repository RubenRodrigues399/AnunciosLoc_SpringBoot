package UAN.AnuncuiosLoc.features.feature_anuncio.dominio.repositorio;
import java.util.List;
import java.util.Optional;

import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.AnuncioEntidade;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.LeitorEntidade;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.PoliticaRLPerfilEntidade;


public interface AnuncioRepository {
    AnuncioEntidade registarAnuncio(AnuncioEntidade request);
    PoliticaRLPerfilEntidade salvarPoliticaRLPerfil(int idAnuncio, int idPerfil);
    List<AnuncioEntidade> pegarTodosAnunciosPorUtilizador(String uuidUtilizador);
    Optional<List<PoliticaRLPerfilEntidade>> pegarPoliticaRLPerfil(int idAnuncio);
    LeitorEntidade lerAnuncio(String uuidLeitor, int idAnuncio);  
    List<AnuncioEntidade> pegarTodosAnuncios();  
}
