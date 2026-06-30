package UAN.AnuncuiosLoc.features.feature_autenticacao.adaptacao.response;

import java.util.List;

import com.anuncios.ws.autenticacao.*;

public class ResponseFactoryAutenticacao {
    public static ReplicacaoResponse replicacao (String mensagem, String dados, int status ){
      
      ReplicacaoResponse  response = new ReplicacaoResponse();
      response.setMensagem(mensagem);
      response.setStatusCode(status);
      System.out.println("=== RETORNANDO STATUS "+ response.getStatusCode());
      System.out.println("=== RETORNANDO MENSAGEM "+ response.getMensagem());
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

    public static ValidarTicketResponse validarToken (String mensagem,ValidarTicketResponse dados,int status){
      ValidarTicketResponse response = new ValidarTicketResponse();
      if (dados != null && dados.getDados() != null && status == 201) {
        UtilizadorTicket utilizadorTicket = new UtilizadorTicket();
        utilizadorTicket.setNome(dados.getDados().getNome());
        utilizadorTicket.setRole(dados.getDados().getRole());
        utilizadorTicket.setSaldo(dados.getDados().getSaldo());
        utilizadorTicket.setUuid(dados.getDados().getUuid());
        response.setDados(utilizadorTicket);
      }

      response.setMensagem(mensagem);
      response.setStatusCode(status);
      return response;
    } 

    public static PegarTodosUtilizadoresResponse pegarUtilizadores (String mensagem, List<UtilizadorRegisto> dados, int status ){
      
      PegarTodosUtilizadoresResponse  response = new PegarTodosUtilizadoresResponse();
      response.setMensagem(mensagem);
      response.setStatusCode(status);
      if (dados.size()!=0) {
        response.getUtilizadores().addAll(dados); 
      }      
      return response;
    } 

    public static ConsultarSaldoResponse consultarSaldo (String mensagem,String saldo, int status ){
      ConsultarSaldoResponse response = new ConsultarSaldoResponse();
      response.setMensagem(mensagem);
      response.setStatusCode(status);
      if (saldo!= null) {
          response.setSaldoActual(saldo);
      }
      return response;
    } 



}
