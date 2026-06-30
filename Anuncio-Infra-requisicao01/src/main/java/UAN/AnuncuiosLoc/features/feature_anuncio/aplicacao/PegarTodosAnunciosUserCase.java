package UAN.AnuncuiosLoc.features.feature_anuncio.aplicacao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anuncios.ws.local.AnuncioRegistoInfra;
import UAN.AnuncuiosLoc.features.feature_anuncio.dominio.model.AnuncioMappers;
import UAN.AnuncuiosLoc.features.feature_anuncio.dominio.repositorio.AnuncioRepository;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.AnuncioEntidade;

@Service
public class PegarTodosAnunciosUserCase {
    @Autowired
    private AnuncioRepository anuncioRepository;

    public List<AnuncioRegistoInfra> executar (){
        // PEGAR TODOS ANUNCIOS
        List<AnuncioEntidade> anunciosOptional =anuncioRepository.pegarTodosAnuncios();
        List<AnuncioRegistoInfra> response = new ArrayList<AnuncioRegistoInfra>();

        for (AnuncioEntidade an : anunciosOptional) {
            AnuncioRegistoInfra anuncio = AnuncioMappers.entidadeToAnuncioRegistoInfra(an);
            response.add(anuncio);
        }
        return response;
    }
}
