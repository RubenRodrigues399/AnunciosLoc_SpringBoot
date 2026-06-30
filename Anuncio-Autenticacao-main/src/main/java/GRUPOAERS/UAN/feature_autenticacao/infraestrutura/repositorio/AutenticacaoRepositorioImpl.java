package GRUPOAERS.UAN.feature_autenticacao.infraestrutura.repositorio;

import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import GRUPOAERS.UAN.core.utils.configuracao_replicacao.ultima_execucao_controller.UltimaExecucaoService;
import GRUPOAERS.UAN.core.utils.configuracao_webservice.InterceptorTokenSoap;
import GRUPOAERS.UAN.feature_autenticacao.adaptacao.mappers.UtilizadorMapper;
import GRUPOAERS.UAN.feature_autenticacao.dominio.model.Utilizador;
import GRUPOAERS.UAN.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.data_source.PerfilDataSource;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.data_source.SessaoDataSource;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.data_source.UtilizadorDataSource;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade.PerfilEntidade;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade.SessaoEntidade;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.services.TicketService;



// ONDE OCORRE A IMPLEMENTAÇÃO DO JPA
@Repository
public class AutenticacaoRepositorioImpl implements AutenticacaoRepository{
    private final UtilizadorDataSource jpaUtilizador;
    private final UltimaExecucaoService ultimaExecucaoService; 
    @Autowired
    private PerfilDataSource jpaPerfil;
    @Autowired
    private SessaoDataSource jpaSessao;
    

    public AutenticacaoRepositorioImpl(UtilizadorDataSource _jpaUtilizador, UltimaExecucaoService ultimaExecucaoService){
        this.jpaUtilizador = _jpaUtilizador;
        this.ultimaExecucaoService = ultimaExecucaoService;
    }


    @Override
    public Utilizador save(Utilizador utilizador) {
        UtilizadorEntidade entidadeUtilizador = new UtilizadorEntidade(
            null, 
            utilizador.getNome(), 
            utilizador.getTelefone(),
            utilizador.getEmail(),
            utilizador.getRole(),
            utilizador.getIdPerfilActivo(),
            utilizador.getSaldo(),
            utilizador.getSenha(), 
            LocalDateTime.now(), 
            LocalDateTime.now()
        );
        entidadeUtilizador = jpaUtilizador.save(entidadeUtilizador);
        String chaveExecucao = "UTILIZADOR" + "_" + "CENTRAL";
        ultimaExecucaoService.atualizarUltimaExecucaoNoRegisto(chaveExecucao, entidadeUtilizador.getUpdatedAt()); 
        PerfilEntidade perfil = new PerfilEntidade();
        perfil.setId(0);
        perfil.setTitulo("DEFAULT");
        return UtilizadorMapper.toUtilizador(entidadeUtilizador, perfil);
    }

    @Override
    public Optional<Utilizador> findByNome(String nome) {
        Optional<UtilizadorEntidade>  utilizadorOpt = jpaUtilizador.findByNome(nome);
        if (!utilizadorOpt.isPresent()) {
            return Optional.empty();
        }
        return formatUserOPtinal(utilizadorOpt.get());
    }

    @Override
    public Optional<Utilizador> findByEmail(String email) {
        Optional<UtilizadorEntidade>  utilizadorOpt = jpaUtilizador.findByEmail(email);
        if (!utilizadorOpt.isPresent()) {
            return Optional.empty();
        }
        return formatUserOPtinal(utilizadorOpt.get());
        
    }


    @Override
    public boolean logout(String uuidUtilizador) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logout'");
    }


    @Override
    public String gerarTicket(Utilizador utilizador){
    try {
        return TicketService.generateServiceTicket(utilizador);
    } catch (GeneralSecurityException e) {
        System.out.println("000000 " + e.toString());
        throw new RuntimeException("Erro ao gerar ticket" + e.toString());
    }
}



    @Override
    public Optional<Utilizador> findByUuid(String uuid) {
        Optional<UtilizadorEntidade>  utilizadorOpt = jpaUtilizador.findById(uuid);
        if (!utilizadorOpt.isPresent()) {
            return Optional.empty();
        }
        return formatUserOPtinal(utilizadorOpt.get());
    }


    @Override
    public int actualizarUtilizador(Utilizador utilizador) {
            Logger logger = LoggerFactory.getLogger(InterceptorTokenSoap.class);
            UtilizadorEntidade entidadeUtilizador = new UtilizadorEntidade(
                utilizador.getUuid(), 
                utilizador.getNome(), 
                utilizador.getTelefone(), 
                utilizador.getEmail(), 
                utilizador.getRole(), 
                utilizador.getIdPerfilActivo(),
                utilizador.getSaldo(), 
                utilizador.getSenha(), 
                utilizador.getCreatedAt(), 
                utilizador.getUpdatedAt()
            );
        entidadeUtilizador = jpaUtilizador.save(entidadeUtilizador);
        logger.warn("========= ENVIANDO 201");
        return 201;
    }


    @Override
    public Optional<Utilizador> findByTelefone(String telefone) {
        Optional<UtilizadorEntidade>  utilizadorOpt = jpaUtilizador.findByTelefone(telefone);
        if (!utilizadorOpt.isPresent()) {
            return Optional.empty();
        }
        return formatUserOPtinal(utilizadorOpt.get());

    }


    @Override
    public void savarPerfil(PerfilEntidade request) {
        jpaPerfil.save(request);
        
    }

    private Optional<Utilizador>formatUserOPtinal(UtilizadorEntidade utilizador){
        UtilizadorEntidade ut = utilizador;
        PerfilEntidade perfilAux = new PerfilEntidade();
        perfilAux.setId(0);
        perfilAux.setTitulo("DEFAULT");   
        if (ut.getIdPerfilActivo() != 0) {
            Optional<PerfilEntidade> perfil = jpaPerfil.findById(ut.getIdPerfilActivo());
            if (perfil.isPresent()) {
                perfilAux.setId(perfil.get().getId());
                perfilAux.setTitulo(perfil.get().getTitulo());
            }
        }

        return Optional.of(UtilizadorMapper.toUtilizador(ut, perfilAux));
    }


    @Override
    public void salvarSessao(SessaoEntidade sessao) {
        jpaSessao.save(sessao);
    }


    @Override
    public PerfilEntidade findByIdPerfil(int id) {
        Optional<PerfilEntidade> perfOptional = jpaPerfil.findById(id);
        PerfilEntidade perfilResponse = new PerfilEntidade();
        if (!perfOptional.isPresent()) {
            perfilResponse.setId(0);
            perfilResponse.setTitulo("DEFAULT");
            return perfilResponse;
        }
        perfilResponse.setId(perfOptional.get().getId());
        perfilResponse.setTitulo(perfOptional.get().getTitulo());
        return perfilResponse;
    }
}
