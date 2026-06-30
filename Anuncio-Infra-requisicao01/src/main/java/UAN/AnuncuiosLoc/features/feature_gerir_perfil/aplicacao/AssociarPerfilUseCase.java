package UAN.AnuncuiosLoc.features.feature_gerir_perfil.aplicacao;
import org.springframework.stereotype.Service;

import UAN.AnuncuiosLoc.features.feature_gerir_perfil.dominio.repositorio.PerfilRepository;

@Service
public class AssociarPerfilUseCase {
     private final PerfilRepository repository;
    AssociarPerfilUseCase(PerfilRepository repository){
        this.repository = repository;
    }  
    
    public void executar (String uuidUtilizador, int idPerfil){
        repository.associarPerfil(uuidUtilizador, idPerfil);
    }
}
