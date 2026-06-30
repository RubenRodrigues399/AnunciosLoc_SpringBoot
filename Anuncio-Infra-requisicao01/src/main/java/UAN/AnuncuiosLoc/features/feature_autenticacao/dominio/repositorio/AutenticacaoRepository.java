package UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.repositorio;
import java.util.List;
import java.util.Optional;

import com.anuncios.ws.autenticacao.ActualizacaoUtilizadorReplicacaoRequest;
import com.anuncios.ws.autenticacao.ValidarTicketResponse;
import UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.model.Utilizador;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;


public interface AutenticacaoRepository {
    UtilizadorEntidade save(UtilizadorEntidade utilizador);
    int actualizarUtilizador(ActualizacaoUtilizadorReplicacaoRequest parametros);
    Optional<Utilizador> findByNome(String nome);
    Optional<Utilizador> findByEmail(String email);
    Optional<UtilizadorEntidade> findById(String uuid);
    List<UtilizadorEntidade> pegarTodosUtilizadores();
    int saveSessao(String uuidUtilizador);
    boolean temSessaoActiva(String uuidUtilizador);
    void TerminarSessao (String uuidUtilizador);
    String gerarToken(int idSessao);
    ValidarTicketResponse validarTicket(String ticket);
}   
