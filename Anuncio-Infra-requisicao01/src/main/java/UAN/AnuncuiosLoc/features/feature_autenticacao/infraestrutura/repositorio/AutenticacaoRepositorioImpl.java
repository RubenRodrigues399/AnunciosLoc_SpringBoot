package UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.repositorio;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.anuncios.ws.autenticacao.ActualizacaoUtilizadorReplicacaoRequest;
import com.anuncios.ws.autenticacao.ValidarTicketResponse;
import UAN.AnuncuiosLoc.core.exception.UtlizadorNaoAutorizado;
import UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.data_source.SessaoDataSource;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.data_source.UtilizadorDataSource;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade.SessaoEntidade;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.services.TicketClient;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.services.TokenService;
import UAN.AnuncuiosLoc.features.feature_autenticacao.adaptacao.mappers.RoleUtilizadorMapper;
import UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.model.Utilizador;

// ONDE OCORRE A IMPLEMENTAÇÃO DO JPA
@Repository
public class AutenticacaoRepositorioImpl implements AutenticacaoRepository{
    private final UtilizadorDataSource jpaUtilizador;
    private final SessaoDataSource jpaSessao;
    private final TicketClient ticketClient;

    public AutenticacaoRepositorioImpl(UtilizadorDataSource _jpaUtilizador, SessaoDataSource _jpaSessao, TicketClient _ticketClient){
        this.jpaUtilizador = _jpaUtilizador;
        this.jpaSessao = _jpaSessao;
        this.ticketClient = _ticketClient;
    }


    @Override
    public UtilizadorEntidade save(UtilizadorEntidade utilizadorEntidade) {
        UtilizadorEntidade response = jpaUtilizador.save(utilizadorEntidade);
        return response;
    }




    @Override
    public int saveSessao(String uuidUtilizador) {
        SessaoEntidade sessaoEntidade = new SessaoEntidade();
       Optional<UtilizadorEntidade> utilizador =     jpaUtilizador.findById(uuidUtilizador);
        
        if (!(utilizador.isPresent())) {
            throw new UtlizadorNaoAutorizado("Utilizador inválido.");
        }

        sessaoEntidade.setUtilizadorEntidade(utilizador.get());

        sessaoEntidade.setDataInicio(LocalDateTime.now());

        sessaoEntidade = jpaSessao.save(sessaoEntidade);
        return sessaoEntidade.getId();
    }


    @Override
    public boolean temSessaoActiva(String uuidUtilizador) {

        Optional<UtilizadorEntidade> utilizador =     jpaUtilizador.findById(uuidUtilizador);
        
        if (!(utilizador.isPresent())) {
            throw new UtlizadorNaoAutorizado("Utilizador inválido.");
        }
        
        Optional<SessaoEntidade> sessao = jpaSessao.findByUtilizadorEntidadeAndDataFimIsNull(utilizador.get());
        System.out.println("== TEM SESSÃO "+ sessao.isPresent());
        return sessao.isPresent();
    }


    @Override
    public void TerminarSessao(String uuidUtilizador) {
        Optional<UtilizadorEntidade> utilizador =     jpaUtilizador.findById(uuidUtilizador);
        
        if (!(utilizador.isPresent())) {
            throw new UtlizadorNaoAutorizado("Utilizador inválido.");
        }

        Optional<SessaoEntidade> sessao = jpaSessao.findByUtilizadorEntidadeAndDataFimIsNull(utilizador.get());

        if (!(sessao.isPresent())) {
            throw new UtlizadorNaoAutorizado("Nenhuma sessão iniciada.");
        }
        sessao.get().setDataFim(LocalDateTime.now());
        jpaSessao.save(sessao.get());
        
    }


    @Override
    public String gerarToken(int idSessao) {
        TokenService tokenService = new TokenService(jpaSessao);
        return tokenService.generateToken(String.valueOf(idSessao));  
    }


    @Override
    public ValidarTicketResponse validarTicket(String ticket) {
        return ticketClient.validarTicket(ticket);

    }
private String escapeXml(String input) {
    if (input == null) return "";
    return input.replace("&", "&amp;")
               .replace("<", "&lt;")
               .replace(">", "&gt;")
               .replace("\"", "&quot;")
               .replace("'", "&apos;");
}


@Override
public Optional<Utilizador> findByNome(String nome) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByNome'");
}


@Override
public Optional<Utilizador> findByEmail(String email) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
}


@Override
public Optional<UtilizadorEntidade> findById(String uuid) {
    //return jpaUtilizador.findById(uuid);
    return jpaUtilizador.findByUuid(uuid);
}


@Override
public List<UtilizadorEntidade> pegarTodosUtilizadores() {
    return jpaUtilizador.findAll();
}


@Override
public int actualizarUtilizador(ActualizacaoUtilizadorReplicacaoRequest parametros) {
        Optional<UtilizadorEntidade> utilizador =     jpaUtilizador.findById(parametros.getUuid());
        
        if (!(utilizador.isPresent())) {
            throw new UtlizadorNaoAutorizado("Utilizador inválido.");
        }

        UtilizadorEntidade entidade = utilizador.get();
        entidade.setNome(parametros.getNome());
        entidade.setEmail(parametros.getEmail());
        entidade.setTelefone(parametros.getTelefone());
        entidade.setSaldo(parametros.getSaldo());
        entidade.setIdPerfilActivo(parametros.getIdPerfilActivo());
        entidade.setNomePerfilActivo(parametros.getNomePerfilActivo());
        entidade.setUpdatedAt(LocalDateTime.parse(parametros.getDataActualizacao()));
        jpaUtilizador.save(entidade);
        return 201;
}





}
