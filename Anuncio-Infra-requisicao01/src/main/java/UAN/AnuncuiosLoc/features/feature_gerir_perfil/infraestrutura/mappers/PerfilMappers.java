package UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.mappers;
import com.anuncios.ws.perfil.PerfilRegisto;

import UAN.AnuncuiosLoc.features.feature_gerir_perfil.dominio.model.Perfil;
import UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.entidade.PerfilEntidade;

public class PerfilMappers {
    public static PerfilEntidade toEntity (Perfil perfil){
        return new PerfilEntidade(perfil.getId(), perfil.getTitulo(), perfil.getUuidUtilizadorCriador(), perfil.getDataCriacao());
    }
    
    public static Perfil toModel (PerfilEntidade entidade){
        return new Perfil(entidade.getId(), entidade.getTitulo(), entidade.getDataCriacao(), entidade.getUuidUtilizadorCriador());
    }

    public static PerfilRegisto modelToPerfilRegisto(Perfil perfil){
        PerfilRegisto response = new PerfilRegisto();
        response.setId(perfil.getId());
        response.setTitulo(perfil.getTitulo());
        response.setUuidUtilizadorCriador(perfil.getUuidUtilizadorCriador());
        response.setDataCriacao(perfil.getDataCriacao().toString());
        return response;
    }
}
