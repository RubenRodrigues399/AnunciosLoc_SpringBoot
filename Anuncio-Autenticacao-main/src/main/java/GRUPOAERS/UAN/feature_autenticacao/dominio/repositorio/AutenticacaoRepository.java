package GRUPOAERS.UAN.feature_autenticacao.dominio.repositorio;
import java.security.GeneralSecurityException;
import java.util.Optional;

import GRUPOAERS.UAN.feature_autenticacao.dominio.model.Utilizador;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade.PerfilEntidade;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade.SessaoEntidade;





public interface AutenticacaoRepository {
    Utilizador save(Utilizador utilizador);
    Optional<Utilizador> findByNome(String nome);
    Optional<Utilizador> findByEmail(String email);
    Optional<Utilizador> findByUuid(String uuid);
    Optional<Utilizador> findByTelefone(String telefone);
    String gerarTicket(Utilizador utilizador) throws GeneralSecurityException;
    int actualizarUtilizador(Utilizador utilizador);
    boolean logout (String uuidUtilizador);
    void savarPerfil(PerfilEntidade perfil);
    void salvarSessao (SessaoEntidade sessao);
    PerfilEntidade findByIdPerfil(int id);
    
}   
