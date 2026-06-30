package UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.repositorio;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

import UAN.AnuncuiosLoc.core.exception.ValorJaExisteException;
import UAN.AnuncuiosLoc.core.exception.ValorNaoEncontrado;
import UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.ultima_execucao_controller.RoleUltimaExecucao;
import UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.ultima_execucao_controller.UltimaExecucaoService;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.data_source.UtilizadorDataSource;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;
import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.data_sorce.LocalDataSource;
import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.entidade.LocalEntidade;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.dominio.model.Perfil;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.dominio.repositorio.PerfilRepository;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.data_source.PerfilDataSource;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.entidade.PerfilEntidade;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.mappers.PerfilMappers;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class PerfilRepositorioImpl implements PerfilRepository {
    private final PerfilDataSource jpaPerfil;
    private final UtilizadorDataSource jpaUtilizador;
    private final UltimaExecucaoService ultimaExecucaoService;
    private final UtilizadorDataSource utilizadorDataSource;
    private final LocalDataSource jpaLocal;

    PerfilRepositorioImpl(PerfilDataSource jpaPerfil, UtilizadorDataSource jpaUtilizador, UltimaExecucaoService _ultimaExecucaoService, UtilizadorDataSource _utilizadorDataSource, LocalDataSource jpaLocal){
        this.jpaPerfil = jpaPerfil;
        this.jpaUtilizador = jpaUtilizador;
        this.ultimaExecucaoService = _ultimaExecucaoService;
        this.utilizadorDataSource = _utilizadorDataSource;
        this.jpaLocal = jpaLocal;
    }


    @Override
    public Perfil criar(String titulo, String uuidCriador) {
        Optional<PerfilEntidade> perfilOptinal = jpaPerfil.findByTitulo(titulo);
        if (perfilOptinal.isPresent()) {
                    throw new ValorJaExisteException("Esse perfil já está cadastrado.");
        }
        Optional<UtilizadorEntidade> utilizadorOptiona = jpaUtilizador.findById(uuidCriador);
        if (!(utilizadorOptiona.isPresent())) {
            throw new ValorNaoEncontrado("Utilizador Inválido.");
        }
        PerfilEntidade perfilEntidade = new PerfilEntidade();
        perfilEntidade.setTitulo(titulo);
        perfilEntidade.setUuidUtilizadorCriador(uuidCriador);
        perfilEntidade.setDataCriacao(LocalDateTime.now());
        perfilEntidade = jpaPerfil.save(perfilEntidade);
        return new Perfil(perfilEntidade.getId(), titulo, perfilEntidade.getDataCriacao(), uuidCriador);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Perfil> pegarTodosPerfis() {
            return jpaPerfil.findAll().stream()
                   .map(PerfilMappers::toModel)
                   .collect(Collectors.toList());
    }

    @Override
    public void associarPerfil(String uuidUtilizador, int idPerfil) {
        Optional<UtilizadorEntidade> utilizadorOpt = utilizadorDataSource.findById(uuidUtilizador);
        if (!utilizadorOpt.isPresent()) {
           throw new ValorNaoEncontrado("Utilizador não encontrado");
        }
        UtilizadorEntidade utilizador = utilizadorOpt.get();
        if (idPerfil != 0) {
            Optional<PerfilEntidade> perfil = jpaPerfil.findById(idPerfil);
            if (!(perfil.isPresent())) {
                throw new ValorNaoEncontrado("Perfil inválido");
            }
            utilizador.setIdPerfilActivo(idPerfil);
            utilizador.setNomePerfilActivo(perfil.get().getTitulo());
        } else {
            utilizador.setIdPerfilActivo(0);
            utilizador.setNomePerfilActivo("DEFAULT"); 
        }
        utilizador.setUpdatedAt(LocalDateTime.now());
        jpaUtilizador.save(utilizador);
        List<LocalEntidade> locais = jpaLocal.findAll();
        System.out.println("LOCAIS " + locais.size());
        
        for (LocalEntidade local : locais) {
            String chaveExecucao = "UTILIZADOR" + "_" + local.getNome();
        ultimaExecucaoService.atualizarUltimaExecucaoNoRegisto(chaveExecucao, utilizador.getUpdatedAt());
        }
        String chaveExecucao = "UTILIZADOR" + "_" + "AUTENTICACAO";
        ultimaExecucaoService.atualizarUltimaExecucaoNoRegisto(chaveExecucao, utilizador.getUpdatedAt());
        ultimaExecucaoService.atualizarUltimaExecucaoNoRegisto(RoleUltimaExecucao.UTILIZADOR.toString(), utilizador.getUpdatedAt());
    }


    @Override
    public Optional<PerfilEntidade> findById(int idPerfil) {
        return jpaPerfil.findById(idPerfil);
    }


    @Override
    public void salvar(PerfilEntidade request) {
        jpaPerfil.save(request);
    }

    
}
