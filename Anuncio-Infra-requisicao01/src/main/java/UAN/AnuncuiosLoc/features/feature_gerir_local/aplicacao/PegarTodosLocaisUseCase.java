package UAN.AnuncuiosLoc.features.feature_gerir_local.aplicacao;

import java.util.List;
import org.springframework.stereotype.Service;
import UAN.AnuncuiosLoc.features.feature_gerir_local.dominio.repositorio.LocalRepository;
import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.entidade.LocalEntidade;

@Service
public class PegarTodosLocaisUseCase {
    private final LocalRepository localRepository;

    PegarTodosLocaisUseCase(LocalRepository _localRepository){
        this.localRepository = _localRepository;
    }

    public List<LocalEntidade> executar(){
        return localRepository.pegarTodosLocais();
    }    
    
}
