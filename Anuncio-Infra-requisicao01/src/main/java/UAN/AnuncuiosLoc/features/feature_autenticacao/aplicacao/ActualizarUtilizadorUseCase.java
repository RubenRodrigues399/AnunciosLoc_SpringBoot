package UAN.AnuncuiosLoc.features.feature_autenticacao.aplicacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anuncios.ws.autenticacao.ActualizacaoUtilizadorReplicacaoRequest;
import UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;

@Service
public class ActualizarUtilizadorUseCase {
    @Autowired
    private AutenticacaoRepository repositorioAutenticacao;

    public int executar (ActualizacaoUtilizadorReplicacaoRequest utilizador){
        System.out.println("== SUCESSO 01 {}"+ utilizador.getEmail());
        return repositorioAutenticacao.actualizarUtilizador(utilizador);
    }
}
