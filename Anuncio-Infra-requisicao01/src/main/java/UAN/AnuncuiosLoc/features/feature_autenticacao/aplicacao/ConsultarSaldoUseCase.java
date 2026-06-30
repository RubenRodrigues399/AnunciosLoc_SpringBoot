package UAN.AnuncuiosLoc.features.feature_autenticacao.aplicacao;

import java.util.Optional;

import org.springframework.stereotype.Service;

import UAN.AnuncuiosLoc.core.exception.ValorNaoEncontrado;
import UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;

@Service
public class ConsultarSaldoUseCase {
    private final AutenticacaoRepository repositorioAutenticacao;
    
    public ConsultarSaldoUseCase(AutenticacaoRepository repository){
        this.repositorioAutenticacao = repository;
    }

    public String executar (String uuidString){
        Optional<UtilizadorEntidade> utilOpt = repositorioAutenticacao.findById(uuidString);
        if (!utilOpt.isPresent()) {
            throw new ValorNaoEncontrado("uuid inválido");
        }
        return String.valueOf(utilOpt.get().getSaldo());
    }    
    
}
