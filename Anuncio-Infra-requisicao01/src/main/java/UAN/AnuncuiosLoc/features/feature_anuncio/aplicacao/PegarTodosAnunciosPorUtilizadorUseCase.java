package UAN.AnuncuiosLoc.features.feature_anuncio.aplicacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.anuncios.ws.local.AnuncioRegisto;
import com.anuncios.ws.local.PoliticaRLPerfilRegisto;
import UAN.AnuncuiosLoc.core.exception.ValorNaoEncontrado;
import UAN.AnuncuiosLoc.features.feature_anuncio.dominio.model.AnuncioMappers;
import UAN.AnuncuiosLoc.features.feature_anuncio.dominio.repositorio.AnuncioRepository;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.AnuncioEntidade;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.PoliticaRLPerfilEntidade;
import UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;
import UAN.AnuncuiosLoc.features.feature_gerir_local.dominio.repositorio.LocalRepository;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.dominio.repositorio.PerfilRepository;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.entidade.PerfilEntidade;

@Service
public class PegarTodosAnunciosPorUtilizadorUseCase {
    private final AnuncioRepository anuncioRepository;
    private final LocalRepository localRepository;
    private final PerfilRepository perfilRepository;
    private final AutenticacaoRepository autenticacaoRepository;

    PegarTodosAnunciosPorUtilizadorUseCase(AnuncioRepository anuncioRepository, LocalRepository localRepository, PerfilRepository perfilRepository, AutenticacaoRepository autenticacaoRepository){
        this.anuncioRepository=anuncioRepository;
        this.localRepository = localRepository;
        this.perfilRepository = perfilRepository;
        this.autenticacaoRepository = autenticacaoRepository;
    }

    public List<AnuncioRegisto> executar (String uuidCriador){
        Optional<UtilizadorEntidade> utilizadorOptional =autenticacaoRepository.findById(uuidCriador);
        if (!utilizadorOptional.isPresent()) {
            throw new ValorNaoEncontrado("Utilizador não encontrado."); 
        }
        List<AnuncioRegisto> response = new ArrayList<AnuncioRegisto>();

        // SEM ANUNCIOS PUBLICADOS
        System.out.println("PASSOU O1");
        List<AnuncioEntidade> anunciosOptional =anuncioRepository.pegarTodosAnunciosPorUtilizador(uuidCriador);
        System.out.println("PASSOU O2");
        if (!utilizadorOptional.isPresent()) {
            throw new ValorNaoEncontrado("Utilizador não encontrado."); 
        }
        System.out.println("PASSOU O3");
        System.out.println(anunciosOptional.size());
        for (AnuncioEntidade an : anunciosOptional) {
        System.out.println("PASSOU O4");
        
            AnuncioRegisto anuncio = AnuncioMappers.entidadeToAnuncioRegisto(an);
  
            anuncio.setNomeCriador(utilizadorOptional.get().getNome());
            anuncio.setEmailCriador(utilizadorOptional.get().getEmail());

            // PEGANDO AS POLÍTICAS DESSE ANUNCIO
            Optional<List<PoliticaRLPerfilEntidade>> politicasOptinal = anuncioRepository.pegarPoliticaRLPerfil(an.getId());
            if (!politicasOptinal.isPresent()) {
                continue;
            }
            for (PoliticaRLPerfilEntidade pol : politicasOptinal.get()) {
                PoliticaRLPerfilRegisto politicaRLPerfilRegisto = new PoliticaRLPerfilRegisto();
                Optional<PerfilEntidade> perfilOptional = perfilRepository.findById(pol.getIdPerfil());
                if (!perfilOptional.isPresent()) {
                    continue;
                }
                politicaRLPerfilRegisto.setId(pol.getId());
                politicaRLPerfilRegisto.setIdAnuncio(pol.getIdAnuncio());
                politicaRLPerfilRegisto.setIdPerfil(pol.getIdPerfil());
                politicaRLPerfilRegisto.setNomePerfil(perfilOptional.get().getTitulo());
                anuncio.getPerfis().add(politicaRLPerfilRegisto);
                
            }
            response.add(anuncio);
        }
        return response;
    }
}