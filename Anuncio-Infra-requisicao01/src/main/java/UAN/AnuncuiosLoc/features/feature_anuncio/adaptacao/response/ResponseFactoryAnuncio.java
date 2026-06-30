package UAN.AnuncuiosLoc.features.feature_anuncio.adaptacao.response;

import java.util.List;
import com.anuncios.ws.local.*;

import UAN.AnuncuiosLoc.features.feature_anuncio.dominio.model.AnuncioMappers;
public class ResponseFactoryAnuncio {

    public static AnuncioResponse registarAnuncio (String mensagem, AnuncioRegisto dados, int status ){
        AnuncioResponse response = new AnuncioResponse();
        response.setMensagem(mensagem);
        response.setStatusCode(status);
        response.setDados(dados);
        return response;
    } 

    public static TodosAnunciosResponse pegarTodosAnuncios (String mensagem, List<AnuncioRegisto> dados, int status ){
        TodosAnunciosResponse response = new TodosAnunciosResponse();
        response.setMensagem(mensagem);
        response.setStatusCode(status);
        if (dados!= null) {
            response.getAnuncios().addAll(dados);            
        }
        return response;
    } 

    public static LerAnuncioResponse lerAnuncio (String mensagem, LeitorRegisto dados, int status ){
        LerAnuncioResponse response = new LerAnuncioResponse();
        response.setMensagem(mensagem);
        response.setStatusCode(status);
        if (dados!=null) {
            response.setDados(dados);            
        }
        return response;
    }


    public static PegarTodosAnunciosInfraResponse pegarTodosAnunciosInfra (List<AnuncioRegistoInfra> dados, int status ){
        PegarTodosAnunciosInfraResponse response = new PegarTodosAnunciosInfraResponse();
        response.setStatusCode(status);
        if (dados!= null) {
            List<PegarTodosAnunciosInfraRegisto> r =AnuncioMappers.toPegarTodosAnunciosInfraResponse(dados);
            response.getAnunciosInfra().addAll(r);            
        }
        return response;
    }
    

}
