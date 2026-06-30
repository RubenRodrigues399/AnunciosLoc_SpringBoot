package UAN.AnuncuiosLoc.features.feature_autenticacao.aplicacao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.anuncios.ws.autenticacao.UtilizadorRegisto;
import UAN.AnuncuiosLoc.features.feature_autenticacao.adaptacao.mappers.UtilizadorMapper;
import UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;

@Service
public class PegarTodosUtilizadoresUseCase {
    private final AutenticacaoRepository repositorioAutenticacao;


    public PegarTodosUtilizadoresUseCase(AutenticacaoRepository repositorio){
        this.repositorioAutenticacao = repositorio;
    }

    public List<UtilizadorRegisto> executar (){
        List<UtilizadorRegisto> utilizadores = new ArrayList<UtilizadorRegisto>();
        List<UtilizadorEntidade> entidades = repositorioAutenticacao.pegarTodosUtilizadores();
        for (UtilizadorEntidade entidade : entidades) {
            UtilizadorRegisto u = UtilizadorMapper.toUtilizadorRegisto(entidade);
            utilizadores.add(u);
        }
        return utilizadores;
    }
}
