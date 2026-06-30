package UAN.AnuncuiosLoc.features.feature_anuncio.dominio.model;

public class RolePoliticaMapper {

    public static RolePoliticaAnuncio toRole (String valor){
        if (valor.equalsIgnoreCase("blacklist")) {
            return RolePoliticaAnuncio.BLACKLIST;
        }        
        if (valor.equalsIgnoreCase("whitelist")) {
            return RolePoliticaAnuncio.WHITELIST;
        }
        return RolePoliticaAnuncio.NENHUMA;
    }

    
}
