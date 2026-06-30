package UAN.AnuncuiosLoc.features.feature_gerir_local.adaptacao.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.anuncios.ws.local.LocalRegisto;
import com.anuncios.ws.local.PegarTodosLocaisResponse;
import com.anuncios.ws.local.RegistarLocalResponse;

import UAN.AnuncuiosLoc.features.feature_gerir_local.adaptacao.mappers.LocalMappers;
import UAN.AnuncuiosLoc.features.feature_gerir_local.infraestrutura.entidade.LocalEntidade;

public class ResponseFactoryLocal {
    
    public static RegistarLocalResponse registarLocal (String mensagem, LocalEntidade dados, int status ){
      RegistarLocalResponse  response = new RegistarLocalResponse();
      LocalRegisto value = new LocalRegisto();
      if (dados!= null) {
      value.setId(dados.getId());
      value.setLatitude(dados.getLatitude());
      value.setLongitude(dados.getLongitude());
      value.setNome(dados.getNome());
      value.setRaio(dados.getRaio());
      value.setUrl(dados.getUrl());
      }

      response.setMensagem(mensagem);
      response.setStatusCode(status);
      response.setDados(value);
      System.out.println("=== RETORNANDO STATUS "+ response.getStatusCode());
      System.out.println("=== RETORNANDO MENSAGEM "+ response.getMensagem());
      return response;
    } 


    public static PegarTodosLocaisResponse pegarTodosLocais (String mensagem, List<LocalEntidade> dados, int status ){
      PegarTodosLocaisResponse  response = new PegarTodosLocaisResponse();
      List<LocalRegisto> localisRegisto = new ArrayList<>();
      if (dados!=null) {
        localisRegisto= dados.stream().map(LocalMappers::entidadeToLocalRegisto).collect(Collectors.toList());
      }

      response.setMensagem(mensagem);
      response.setStatusCode(status);
      response.getDados().addAll(localisRegisto);
      System.out.println("=== RETORNANDO STATUS "+ response.getStatusCode());
      System.out.println("=== RETORNANDO MENSAGEM "+ response.getMensagem());
      return response;
    } 

}

      // PegarPerfisAssociadoResponse  response = new PegarPerfisAssociadoResponse();
      // response.setMensagem(mensagem);
      // response.setStatusCode(status);
      // response.getDados().addAll(dados);
      // return response;