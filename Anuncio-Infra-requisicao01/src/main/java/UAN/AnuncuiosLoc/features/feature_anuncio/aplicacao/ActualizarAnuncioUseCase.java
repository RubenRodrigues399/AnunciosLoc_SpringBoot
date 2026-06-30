package UAN.AnuncuiosLoc.features.feature_anuncio.aplicacao;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.anuncios.ws.local.AnuncioRegisto;
import com.anuncios.ws.local.PoliticaRLPerfilRegisto;

import UAN.AnuncuiosLoc.features.feature_anuncio.dominio.model.RolePoliticaAnuncio;
import UAN.AnuncuiosLoc.features.feature_anuncio.dominio.model.RolePoliticaMapper;
import UAN.AnuncuiosLoc.features.feature_anuncio.dominio.repositorio.AnuncioRepository;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.datasource.PoliticaRLPerfilDataSource;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.AnuncioEntidade;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.PoliticaRLPerfilEntidade;

@Service
public class ActualizarAnuncioUseCase {
    private final AnuncioRepository anuncioRepository;
    private final PoliticaRLPerfilDataSource politicaRLPerfilDataSource;
    public ActualizarAnuncioUseCase(AnuncioRepository repository, PoliticaRLPerfilDataSource politicaRLPerfilDataSource){
        this.anuncioRepository = repository;
        this.politicaRLPerfilDataSource = politicaRLPerfilDataSource;
    }
    public int executar (AnuncioRegisto request){
        AnuncioEntidade anuncioEntidade = new AnuncioEntidade();
        anuncioEntidade.setId(request.getId());
        anuncioEntidade.setTitulo(request.getTitulo());
        anuncioEntidade.setDescricao(request.getDescricao());
        RolePoliticaAnuncio role = RolePoliticaMapper.toRole(request.getRolePolitica());
        anuncioEntidade.setRolePoliticaAnuncio(role);
        anuncioEntidade.setCreatedAt(LocalDateTime.now());
        anuncioEntidade.setUpdatedAt(LocalDateTime.now());
        anuncioEntidade.setUuidCriador(request.getUuidCriador());
        anuncioEntidade = anuncioRepository.registarAnuncio(anuncioEntidade);
        
        if (request.getPerfis().size()!=0) {
            for (PoliticaRLPerfilRegisto p : request.getPerfis()) {
                PoliticaRLPerfilEntidade pol = new PoliticaRLPerfilEntidade();
                pol.setId(p.getId());
                pol.setIdAnuncio(p.getIdAnuncio());
                pol.setIdPerfil(p.getIdPerfil());
                pol.setCreatedAt(LocalDateTime.now());
                pol.setUpdatedAt(LocalDateTime.now());
                politicaRLPerfilDataSource.save(pol);

                
            }
        }
        return 201;
    }  

    
}
