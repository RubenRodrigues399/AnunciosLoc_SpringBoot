package UAN.AnuncuiosLoc.features.feature_autenticacao.aplicacao;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.anuncios.ws.autenticacao.UtilizadorRegistoReplicacaoRequest;
import UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.repositorio.AutenticacaoRepository;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;
import UAN.AnuncuiosLoc.features.feature_autenticacao.adaptacao.mappers.RoleUtilizadorMapper;

@Service
public class RegistarUtilizadorUseCase {
    private final AutenticacaoRepository repositorioAutenticacao;


    public RegistarUtilizadorUseCase(AutenticacaoRepository repositorio){
        this.repositorioAutenticacao = repositorio;
    }

    public int executar (UtilizadorRegistoReplicacaoRequest utilizador){
        System.out.println("== SUCESSO 01 {}"+ utilizador.getEmail());
        UtilizadorEntidade entidade = new UtilizadorEntidade();
        entidade.setUuid(utilizador.getUuid());
        entidade.setNome(utilizador.getNome());
        entidade.setEmail(utilizador.getEmail());
        entidade.setTelefone(utilizador.getTelefone());
        entidade.setSaldo(utilizador.getSaldo());
        entidade.setRole(RoleUtilizadorMapper.toRole(utilizador.getRole()));
        entidade.setIdPerfilActivo(utilizador.getIdPerfilActivo());
        entidade.setNomePerfilActivo(utilizador.getNomePerfilActivo());
        entidade.setCreatedAt(LocalDateTime.parse(utilizador.getDataCriacao()));
        entidade.setUpdatedAt(LocalDateTime.parse(utilizador.getDataActualizacao()));
        System.out.println("== SUCESSO 02");
        repositorioAutenticacao.save(entidade);
        return 201;
    }



    
}
