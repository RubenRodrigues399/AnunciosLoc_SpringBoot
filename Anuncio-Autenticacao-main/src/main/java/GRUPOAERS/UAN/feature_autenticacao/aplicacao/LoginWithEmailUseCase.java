package GRUPOAERS.UAN.feature_autenticacao.aplicacao;

import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;
import GRUPOAERS.UAN.core.exception.UtlizadorNaoAutorizado;
import GRUPOAERS.UAN.core.services.CriptografiaService;
import GRUPOAERS.UAN.feature_autenticacao.dominio.model.Utilizador;
import GRUPOAERS.UAN.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade.PerfilEntidade;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade.SessaoEntidade;



@Service
public class LoginWithEmailUseCase {
        private final AutenticacaoRepository repositorioAutenticacao;
 

    public LoginWithEmailUseCase(AutenticacaoRepository repositorio){
        this.repositorioAutenticacao = repositorio;
    }

    public Utilizador executar (String email, String senha)throws GeneralSecurityException {
        // verificar se existe um user com esse email
        Optional<Utilizador> utilizadorFindEmail= repositorioAutenticacao.findByEmail(email);
        if (!(utilizadorFindEmail.isPresent())) {
            throw new UtlizadorNaoAutorizado("Credenciais Inválidas");
        }
        // se sim, descriptografar senha e verificar com a senha actual.
        String senhaDecript = CriptografiaService.decryptPassword(utilizadorFindEmail.get().getSenha());
       
        if (!(senha.equals(senhaDecript))) {
            throw new UtlizadorNaoAutorizado("Credenciais Inválidas");   
        }
        String ticket = repositorioAutenticacao.gerarTicket(utilizadorFindEmail.get());
        utilizadorFindEmail.get().setToken(ticket);
        
        // verificar se tem uma sessão iniciada
        SessaoEntidade sessaoEntidade = new SessaoEntidade();
        sessaoEntidade.setToken(ticket);
        sessaoEntidade.setUuidUtilizador(utilizadorFindEmail.get().getUuid());
        sessaoEntidade.setDataInicio(LocalDateTime.now());
        
        repositorioAutenticacao.salvarSessao(sessaoEntidade);
        PerfilEntidade perfilEntidade = repositorioAutenticacao.findByIdPerfil(utilizadorFindEmail.get().getIdPerfilActivo());

        utilizadorFindEmail.get().setIdPerfilActivo(perfilEntidade.getId());
        utilizadorFindEmail.get().setNomePerfilActivo(perfilEntidade.getTitulo());

       return utilizadorFindEmail.get();
      
    }


}
