package UAN.AnuncuiosLoc.features.feature_anuncio.aplicacao;

import org.springframework.stereotype.Service;

import com.anuncios.ws.local.LeitorRegisto;
import com.anuncios.ws.local.LerAnuncioRequest;

import UAN.AnuncuiosLoc.features.feature_anuncio.dominio.repositorio.AnuncioRepository;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.LeitorEntidade;

@Service
public class LerAnuncioUserCase {
    private final AnuncioRepository anuncioRepository;

    public LerAnuncioUserCase(AnuncioRepository anuncioRepository){
        this.anuncioRepository = anuncioRepository;
    }

    public LeitorRegisto executar(LerAnuncioRequest request){
        LeitorEntidade leitor = anuncioRepository.lerAnuncio(request.getUuidUtilizador(), request.getIdAnuncio());
        LeitorRegisto response = new LeitorRegisto();
        response.setDataLeitura(leitor.getCreatedAt().toString());
        response.setId(leitor.getId());
        response.setIdAnuncio(leitor.getIdAnuncio());
        response.setUuidLeitor(leitor.getUuidLeitor());
        return response;

    }
    
}
