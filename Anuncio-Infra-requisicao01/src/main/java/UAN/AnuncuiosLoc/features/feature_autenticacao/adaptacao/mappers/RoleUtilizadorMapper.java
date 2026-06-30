package UAN.AnuncuiosLoc.features.feature_autenticacao.adaptacao.mappers;
import UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.model.RoleUtilizador;

public class RoleUtilizadorMapper {
    public static String toString (RoleUtilizador role){
        if (role.toString().equalsIgnoreCase(RoleUtilizador.ADMIN.toString())) return RoleUtilizador.ADMIN.toString();
        if (role.toString().equalsIgnoreCase(RoleUtilizador.PUBLICADOR.toString())) return RoleUtilizador.PUBLICADOR.toString();
        throw new IllegalArgumentException("Role inválida");
    }

    public static RoleUtilizador toRole (String value){
        if (value.equalsIgnoreCase(RoleUtilizador.ADMIN.toString())) return RoleUtilizador.ADMIN;
        if (value.equalsIgnoreCase(RoleUtilizador.PUBLICADOR.toString())) return RoleUtilizador.PUBLICADOR;
        throw new IllegalArgumentException("Role incorrecta");
    }

}
