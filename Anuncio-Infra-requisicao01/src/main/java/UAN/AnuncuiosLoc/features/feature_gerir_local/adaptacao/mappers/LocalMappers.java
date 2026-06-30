package UAN.AnuncuiosLoc.features.feature_gerir_local.adaptacao.mappers;

import com.anuncios.ws.local.LocalRegisto;

import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.entidade.LocalEntidade;

public class LocalMappers {
    public static LocalRegisto entidadeToLocalRegisto(LocalEntidade entidade){
        LocalRegisto localRegisto = new LocalRegisto();
        localRegisto.setId(entidade.getId());
        localRegisto.setLatitude(entidade.getLatitude());
        localRegisto.setLongitude(entidade.getLongitude());
        localRegisto.setNome(entidade.getNome());
        localRegisto.setRaio(entidade.getRaio());
        localRegisto.setUrl(entidade.getUrl());
        return localRegisto; 
    }
    
}
