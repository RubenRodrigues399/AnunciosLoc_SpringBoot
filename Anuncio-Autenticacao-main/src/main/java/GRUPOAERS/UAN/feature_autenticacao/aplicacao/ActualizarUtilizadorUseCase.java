package GRUPOAERS.UAN.feature_autenticacao.aplicacao;

import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.anuncios.ws.autenticacao.ActualizacaoUtilizadorReplicacaoRequest;

import GRUPOAERS.UAN.core.exception.ValorJaExisteException;
import GRUPOAERS.UAN.core.exception.ValorNaoEncontrado;
import GRUPOAERS.UAN.core.utils.configuracao_webservice.InterceptorTokenSoap;
import GRUPOAERS.UAN.feature_autenticacao.dominio.model.Utilizador;
import GRUPOAERS.UAN.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;

@Service
public class ActualizarUtilizadorUseCase {
    private final AutenticacaoRepository repositorioAutenticacao;

    public ActualizarUtilizadorUseCase(AutenticacaoRepository repositorio){
        this.repositorioAutenticacao = repositorio;
    }
    
    public int executar (ActualizacaoUtilizadorReplicacaoRequest params) throws GeneralSecurityException {
        Logger logger = LoggerFactory.getLogger(InterceptorTokenSoap.class);
        Optional<Utilizador> utilizadorOptinal= repositorioAutenticacao.findByUuid(params.getUuid());
        if (!utilizadorOptinal.isPresent()) {
          throw new ValorNaoEncontrado("Utilizador Não encontrado.");
        }
        Utilizador utilizador = utilizadorOptinal.get();
        Optional<Utilizador> utilizadorFindEmail= repositorioAutenticacao.findByEmail(params.getEmail());

        if (utilizadorFindEmail.isPresent()) {
          if ((!(utilizador.getUuid().equals(utilizadorFindEmail.get().getUuid())))) {
            throw new ValorJaExisteException("E-mail já está em uso.");
          }
        }
        utilizador.setEmail(params.getEmail());
        
        // VERIFICA SE JÁ EXISTE UM USER COM ESSE NOME
        Optional<Utilizador> utilizadorFindNome= repositorioAutenticacao.findByNome(params.getNome());
        if (utilizadorFindNome.isPresent()) {
          if ((!(utilizador.getUuid().equals(utilizadorFindNome.get().getUuid())))) {
            throw new ValorJaExisteException("Nome já está em uso.");
          }
        }
        utilizador.setNome(params.getNome());
        logger.warn("========= UP 0");

        // VERIFICA SE JÁ EXISTE UM USER COM ESSE NÚMERO

        if (params.getTelefone()!= null) {
         Optional<Utilizador> utilizadorFindTelefone= repositorioAutenticacao.findByTelefone(params.getTelefone());
          logger.warn("========= UP 1");
          if (utilizadorFindTelefone.isPresent()) {
            if (!(utilizador.getUuid().equals(utilizadorFindTelefone.get().getUuid()))) {
              throw new ValorJaExisteException("Telefone já está em uso.");            
            }
        }
        logger.warn("========= UP 2");
          utilizador.setTelefone(params.getTelefone());   
        }
        utilizador.setIdPerfilActivo(params.getIdPerfilActivo());
        utilizador.setEmail(params.getEmail());
        utilizador.setSaldo(params.getSaldo());
        utilizador.setUpdatedAt(LocalDateTime.parse(params.getDataActualizacao()));
        logger.warn("========= UP 3");

        return repositorioAutenticacao.actualizarUtilizador(utilizador);
    }
}
