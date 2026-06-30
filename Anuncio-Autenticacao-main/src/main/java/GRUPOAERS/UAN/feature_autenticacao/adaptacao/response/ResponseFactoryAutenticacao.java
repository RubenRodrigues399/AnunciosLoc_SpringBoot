package GRUPOAERS.UAN.feature_autenticacao.adaptacao.response;

import com.anuncios.ws.autenticacao.*;

import GRUPOAERS.UAN.feature_autenticacao.dominio.model.ValidacaoTicketResponse;

public class ResponseFactoryAutenticacao {
    public static RegistarUtilizadorResponse registarUtilizador (String mensagem, UtilizadorRegisto dados, int status ){
      
      RegistarUtilizadorResponse  response = new RegistarUtilizadorResponse();
      response.setMensagem(mensagem);
      response.setStatusCode(status);
      response.setDados(dados);
      return response;
    } 

    public static LoginResponse login (String mensagem, UtilizadorLogin dados, int status ){
      LoginResponse  response = new LoginResponse();
      response.setMensagem(mensagem);
      response.setStatusCode(status);
      response.setDados(dados);
      return response;
    } 

    public static LogoutResponse logout (String mensagem, int status ){
      LogoutResponse response = new LogoutResponse();
      response.setMensagem(mensagem);
      response.setStatusCode(status);
      return response;
    } 

    public static UpdateResponse updateUtilizador (String mensagem, UtilizadorRegisto dados, int status ){
      UpdateResponse  response = new UpdateResponse();
      response.setMensagem(mensagem);
      response.setStatusCode(status);
      response.setDados(dados);
      return response;
    } 


    public static ValidarTicketResponse validarTicket (String mensagem, ValidacaoTicketResponse dados, int status ){
      ValidarTicketResponse  response = new ValidarTicketResponse();
      UtilizadorTicket utilizadorTicket = new UtilizadorTicket();
      if (dados!=null) {
      utilizadorTicket.setNome(dados.getNome());
      utilizadorTicket.setRole(dados.getRole().name());
      utilizadorTicket.setSaldo(dados.getSaldo());
      utilizadorTicket.setUuid(dados.getUuid());
      response.setDados(utilizadorTicket);
        
      }
      response.setMensagem(mensagem);
      response.setStatusCode(status);
      return response;
    }

    public static ReplicacaoResponse replicacao (String mensagem, int status ){
      ReplicacaoResponse response = new ReplicacaoResponse();
      response.setMensagem(mensagem);
      response.setStatusCode(status);
      return response;
    } 


}
