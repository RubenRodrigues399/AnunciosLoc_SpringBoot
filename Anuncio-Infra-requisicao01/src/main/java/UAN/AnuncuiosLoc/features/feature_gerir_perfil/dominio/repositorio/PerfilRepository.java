package UAN.AnuncuiosLoc.features.feature_gerir_perfil.dominio.repositorio;
import java.util.List;
import java.util.Optional;

import UAN.AnuncuiosLoc.features.feature_gerir_perfil.dominio.model.Perfil;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.entidade.PerfilEntidade;

public interface PerfilRepository {
    // CRIAR PERFIL
    Perfil criar(String titulo, String uuidCriador);
    // PEGAR TODOS PERFÍS CRIADOS
    List<Perfil> pegarTodosPerfis();
    // ASSOCIAR PERFIL 
    void associarPerfil (String uuidUtilizador, int idPerfil);
    Optional<PerfilEntidade> findById (int idPerfil);
    void salvar(PerfilEntidade request);
    // PEGAR TODOS PERFÍS ASSOCIADOS
    //List<Perfil> pegarPerfisAssociados(String uuidUtilizador);
    
} 
