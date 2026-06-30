package UAN.AnuncuiosLoc.features.feature_anuncio.aplicacao;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anuncios.ws.local.AnuncioInfraResponse;
import com.anuncios.ws.local.AnuncioRequest;
import UAN.AnuncuiosLoc.features.feature_anuncio.dominio.model.RolePoliticaAnuncio;
import UAN.AnuncuiosLoc.features.feature_anuncio.dominio.model.RolePoliticaMapper;
import UAN.AnuncuiosLoc.features.feature_anuncio.dominio.repositorio.AnuncioRepository;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.AnuncioEntidade;

@Service
public class RegistarAnuncioUseCase {
    
    private AnuncioRepository anuncioRepository;

    RegistarAnuncioUseCase(AnuncioRepository rep){
        this.anuncioRepository= rep;
    }
    public AnuncioInfraResponse executar (AnuncioRequest request){
        AnuncioEntidade anuncioEntidade = new AnuncioEntidade();
        anuncioEntidade.setTitulo(request.getTitulo());
        anuncioEntidade.setDescricao(request.getDescricao());
        RolePoliticaAnuncio role = RolePoliticaMapper.toRole(request.getRolePolitica());
        anuncioEntidade.setRolePoliticaAnuncio(role);
        anuncioEntidade.setUuidCriador(request.getUuidCriador());
        anuncioEntidade.setCreatedAt(LocalDateTime.now());
        anuncioEntidade.setUpdatedAt(LocalDateTime.now());
        anuncioEntidade = anuncioRepository.registarAnuncio(anuncioEntidade);
        AnuncioInfraResponse response = new AnuncioInfraResponse();
        response.setIdAnuncioInfra(anuncioEntidade.getId());
        response.setStatus(201);
        return response;
    }  
}
