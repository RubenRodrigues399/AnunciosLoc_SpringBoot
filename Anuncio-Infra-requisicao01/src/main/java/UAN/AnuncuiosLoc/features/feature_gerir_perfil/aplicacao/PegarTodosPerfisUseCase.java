package UAN.AnuncuiosLoc.features.feature_gerir_perfil.aplicacao;

import java.util.List;

import org.springframework.stereotype.Service;

import UAN.AnuncuiosLoc.features.feature_gerir_perfil.dominio.model.Perfil;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.dominio.repositorio.PerfilRepository;

@Service
public class PegarTodosPerfisUseCase {
     private final PerfilRepository repository;
    PegarTodosPerfisUseCase(PerfilRepository repository){
        this.repository = repository;
    }  
    
    public List<Perfil> executar (){
       return repository.pegarTodosPerfis();
    }
}
