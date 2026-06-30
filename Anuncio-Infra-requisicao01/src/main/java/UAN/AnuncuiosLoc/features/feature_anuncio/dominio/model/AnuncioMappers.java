package UAN.AnuncuiosLoc.features.feature_anuncio.dominio.model;

import java.util.ArrayList;
import java.util.List;

import com.anuncios.ws.local.AnuncioRegisto;
import com.anuncios.ws.local.AnuncioRegistoInfra;
import com.anuncios.ws.local.PegarTodosAnunciosInfraRegisto;

import UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade.AnuncioEntidade;

public class AnuncioMappers {
    public static AnuncioRegisto entidadeToAnuncioRegisto (AnuncioEntidade anuncioEntidade){
        AnuncioRegisto response = new AnuncioRegisto();
        response.setId(anuncioEntidade.getId());
        response.setTitulo(anuncioEntidade.getTitulo());
        response.setDescricao(anuncioEntidade.getDescricao());
        response.setRolePolitica(anuncioEntidade.getRolePoliticaAnuncio().toString());
        response.setUuidCriador(anuncioEntidade.getUuidCriador());
        response.setDataCriacao(anuncioEntidade.getCreatedAt().toString());
        response.setDataActualizacao(anuncioEntidade.getUpdatedAt().toString());
        return response;
    }


    public static AnuncioRegistoInfra entidadeToAnuncioRegistoInfra (AnuncioEntidade anuncioEntidade){
        AnuncioRegistoInfra response = new AnuncioRegistoInfra();
        response.setId(anuncioEntidade.getId());
        response.setTitulo(anuncioEntidade.getTitulo());
        response.setDescricao(anuncioEntidade.getDescricao());
        response.setRolePolitica(anuncioEntidade.getRolePoliticaAnuncio().toString());
        response.setUuidCriador(anuncioEntidade.getUuidCriador());
        response.setDataCriacao(anuncioEntidade.getCreatedAt().toString());
        response.setDataActualizacao(anuncioEntidade.getUpdatedAt().toString());
        return response;
    }

    public static List<PegarTodosAnunciosInfraRegisto> toPegarTodosAnunciosInfraResponse (List<AnuncioRegistoInfra> dados){
        List<PegarTodosAnunciosInfraRegisto> response = new ArrayList<PegarTodosAnunciosInfraRegisto>();
        for (AnuncioRegistoInfra a : dados) {
            PegarTodosAnunciosInfraRegisto r = new PegarTodosAnunciosInfraRegisto();
            r.setId(a.getId());
            r.setTitulo(a.getTitulo());
            r.setDescricao(a.getDataCriacao());
            r.setRolePolitica(a.getRolePolitica());
            r.setDataCriacao(a.getDataActualizacao());
            r.setDataActualizacao(a.getDataActualizacao());
            r.setUuidCriador(a.getUuidCriador());
            response.add(r); 
        }
        return response;
    }
}
