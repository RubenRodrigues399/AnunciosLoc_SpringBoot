package GRUPOAERS.UAN.feature_autenticacao.aplicacao;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;

import GRUPOAERS.UAN.core.exception.ValorJaExisteException;
import GRUPOAERS.UAN.core.exception.ValorNaoEncontrado;
import GRUPOAERS.UAN.core.services.CriptografiaService;
import GRUPOAERS.UAN.feature_autenticacao.dominio.model.RegistoParams;
import GRUPOAERS.UAN.feature_autenticacao.dominio.model.Utilizador;
import GRUPOAERS.UAN.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;


@Service
public class RegistarUtilizadorUseCase {
    private final AutenticacaoRepository repositorioAutenticacao;

    public RegistarUtilizadorUseCase(AutenticacaoRepository repositorio){
        this.repositorioAutenticacao = repositorio;

    }

    public Utilizador executar (RegistoParams params) throws GeneralSecurityException {
        String senhaHash = CriptografiaService.encryptPassword(params.getSenha()); 
        String role = params.getRole().name();
        if (!role.equals("PUBLICADOR") && !role.equals("ADMIN")) {
            throw new ValorNaoEncontrado("Role inválida: " + role);
        }
        Optional<Utilizador> utilizadorFindEmail= repositorioAutenticacao.findByEmail(params.getEmail());
        
        if (utilizadorFindEmail.isPresent()) {
          throw new ValorJaExisteException("E-mail já cadastrado.");
        }

        Optional<Utilizador> utilizadorFindNome= repositorioAutenticacao.findByNome(params.getNome());
        if (utilizadorFindNome.isPresent()) {
            throw new ValorJaExisteException("Nome já cadastrado.");   
        }
        int saldo = 0;
        if (role.equals("PUBLICADOR")) {
            saldo = 10;
        }
        Utilizador utilizador = new Utilizador(null, params.getNome(), null, params.getEmail(), params.getRole(),0,"DEFAULT",saldo,null, senhaHash, LocalDateTime.now(), LocalDateTime.now());
        return repositorioAutenticacao.save(utilizador);
    }

}
