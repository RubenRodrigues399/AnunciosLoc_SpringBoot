package UAN.AnuncuiosLoc.features.feature_anuncio.aplicacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anuncios.ws.local.AnuncioRegisto;
import com.anuncios.ws.local.PegarAnunciosPorCoordenadasRequest;
import com.anuncios.ws.local.PoliticaRLPerfilRegisto;

import UAN.AnuncuiosLoc.core.exception.ValorNaoEncontrado;
import UAN.AnuncuiosLoc.features.feature_anuncio.dominio.model.AnuncioMappers;
import UAN.AnuncuiosLoc.features.feature_anuncio.dominio.repositorio.AnuncioRepository;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.AnuncioEntidade;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.PoliticaRLPerfilEntidade;
import UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;
import UAN.AnuncuiosLoc.features.feature_gerir_local.dominio.repositorio.LocalRepository;
import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.entidade.LocalEntidade;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.dominio.repositorio.PerfilRepository;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.entidade.PerfilEntidade;

@Service
public class PegarTodosAnunciosPorCoordenadasUseCase {
    private final AnuncioRepository anuncioRepository;
    private final LocalRepository localRepository;
    private final PerfilRepository perfilRepository;
    private final AutenticacaoRepository autenticacaoRepository;

    PegarTodosAnunciosPorCoordenadasUseCase(AnuncioRepository anuncioRepository, LocalRepository localRepository, PerfilRepository perfilRepository, AutenticacaoRepository autenticacaoRepository){
        this.anuncioRepository=anuncioRepository;
        this.perfilRepository = perfilRepository;
        this.localRepository = localRepository;
        this.autenticacaoRepository = autenticacaoRepository;
    }

    public List<AnuncioRegisto> executar (PegarAnunciosPorCoordenadasRequest request){
        // VERIFICAR SE LOCAL EXISTE
        List<LocalEntidade> locais = localRepository.pegarLocaisPorCoordenadas(request.getLatitude(),request.getLongitude());
        if (locais.size()==0) {
            throw new ValorNaoEncontrado("Nenhum local associado a essa coordenada."); 
        }
        Optional<UtilizadorEntidade> utilOptional = autenticacaoRepository.findById(request.getUuidUtilizador());
        if (!utilOptional.isPresent()) {
            throw new ValorNaoEncontrado("uuid inválido."); 
        }
        List<AnuncioRegisto> response = new ArrayList<AnuncioRegisto>();
        for (LocalEntidade local : locais) {
            List<AnuncioEntidade> anunciosOptional =anuncioRepository.pegarTodosAnuncios();
            for (AnuncioEntidade an : anunciosOptional) {
            Optional<UtilizadorEntidade> utilOptionalAnuncio = autenticacaoRepository.findById(an.getUuidCriador());
            if (!utilOptionalAnuncio.isPresent()) {
                continue; 
            }

                AnuncioRegisto anuncio = AnuncioMappers.entidadeToAnuncioRegisto(an);
                anuncio.setNomeCriador(utilOptionalAnuncio.get().getNome());
                anuncio.setEmailCriador(utilOptionalAnuncio.get().getEmail());
                // PEGANDO AS POLÍTICAS DESSE ANUNCIO
                Optional<List<PoliticaRLPerfilEntidade>> politicasOptinal = anuncioRepository.pegarPoliticaRLPerfil(an.getId());
                if (!politicasOptinal.isPresent()) {
                    response.add(anuncio);
                    continue;
                }
                // Perfil default, ignora black list

                // se for black list
                    // verifica se o meu perfil actual faz parte da lista
                        // se fizer, n add
                        // se n fizer add
                // se form white list
                    // verifica se o meu perfil
                boolean contemMeuPerfil = politicasOptinal.get().stream()
                .anyMatch(p -> p.getIdPerfil() == utilOptional.get().getIdPerfilActivo());

                // VERIFCANDO SE O ANUNCIO É PRA MIM
                if (an.getRolePoliticaAnuncio().name().equalsIgnoreCase("blacklist")) {
                    if (contemMeuPerfil) {
                        continue;
                    }
                }
                if (an.getRolePoliticaAnuncio().name().equalsIgnoreCase("whitelist")) {
                    if (!contemMeuPerfil) {
                        continue;
                    }
                }

                // O ANUNCIO SERVE PRA MIM
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
        }
        return response;
    }
}
