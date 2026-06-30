package UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.respositorio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import UAN.AnuncuiosLoc.core.exception.ValorJaExisteException;
import UAN.AnuncuiosLoc.core.exception.ValorNaoEncontrado;
import UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.ultima_execucao_controller.UltimaExecucaoService;
import UAN.AnuncuiosLoc.features.feature_anuncio.dominio.repositorio.AnuncioRepository;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.datasource.AnuncioDataSource;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.datasource.LeitorDataSource;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.datasource.PoliticaRLPerfilDataSource;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.AnuncioEntidade;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.LeitorEntidade;
import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.PoliticaRLPerfilEntidade;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.data_source.UtilizadorDataSource;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;

@Repository
public class AnuncioRepositoryImpl implements AnuncioRepository{
    private final AnuncioDataSource jpaAnuncio;
    private final PoliticaRLPerfilDataSource jpaPolitica;
    private final LeitorDataSource jpaLeitor;
    private final UltimaExecucaoService jpaUltimaExecucao;
    private final UtilizadorDataSource jpaUtilizador;
    private static final Logger logger = LoggerFactory.getLogger(AnuncioRepositoryImpl.class);
    AnuncioRepositoryImpl(AnuncioDataSource jpaAnuncio, PoliticaRLPerfilDataSource jpaPolitica, LeitorDataSource jpaLeitor, UltimaExecucaoService jpaUltimaExecucao, UtilizadorDataSource jpaUtilizador){
        this.jpaAnuncio = jpaAnuncio;
        this.jpaPolitica = jpaPolitica;
        this.jpaLeitor = jpaLeitor;
        this.jpaUltimaExecucao = jpaUltimaExecucao;
        this.jpaUtilizador = jpaUtilizador;
    }

    @Override
    public AnuncioEntidade registarAnuncio(AnuncioEntidade request) {
        try {
           return jpaAnuncio.save(request);
        } catch (DataIntegrityViolationException e) {
            throw new ValorJaExisteException("Valor já existe: ");
        }


    }

    @Override
    public PoliticaRLPerfilEntidade salvarPoliticaRLPerfil(int idAnuncio, int idPerfil) {
        PoliticaRLPerfilEntidade r = new PoliticaRLPerfilEntidade();
        r.setIdAnuncio(idAnuncio);
        r.setIdPerfil(idPerfil);
        r.setCreatedAt(LocalDateTime.now());
        r.setUpdatedAt(LocalDateTime.now());
        return jpaPolitica.save(r);
    }





    @Override
    public List<AnuncioEntidade> pegarTodosAnunciosPorUtilizador(String uuidUtilizador) {
        return jpaAnuncio.findByUuidCriador(uuidUtilizador);
    }

    @Override
    public Optional<List<PoliticaRLPerfilEntidade>> pegarPoliticaRLPerfil(int idAnuncio) {
        return jpaPolitica.findByIdAnuncio(idAnuncio);
    }

    @Override
    public LeitorEntidade lerAnuncio(String uuidLeitor, int idAnuncio) {
        Optional<LeitorEntidade> leiOptional = jpaLeitor.findByIdAnuncioAndUuidLeitor(idAnuncio, uuidLeitor);
        if (leiOptional.isPresent()) {
            return leiOptional.get();
        }
        LeitorEntidade response = new LeitorEntidade();
        response.setIdAnuncio(idAnuncio);
        response.setUuidLeitor(uuidLeitor);
        response = jpaLeitor.save(response);
        Optional<AnuncioEntidade> anOptional = jpaAnuncio.findById(idAnuncio);
        if (!anOptional.isPresent()) {
            throw new ValorNaoEncontrado("Id Anuncio inválido");
        }
        Optional<UtilizadorEntidade> utilOptional = jpaUtilizador.findById(anOptional.get().getUuidCriador());
        if (!utilOptional.isPresent()) {
            jpaAnuncio.delete(anOptional.get());
            throw new ValorNaoEncontrado("Id Anuncio inválido");
        }
        if (utilOptional.get().getUuid().equalsIgnoreCase(uuidLeitor)) {
            return response;
        }
        jpaUltimaExecucao.atualizarUltimaExecucaoNoRegisto("LEITOR", response.getUpdatedAt());
        return response;
    }

    @Override
    public List<AnuncioEntidade> pegarTodosAnuncios() {
        return jpaAnuncio.findAll();
    }

}
