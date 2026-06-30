package UAN.AnuncuiosLoc.features.feature_gerir_perfil.adaptacao.response;
import java.util.List;
import com.anuncios.ws.perfil.AssociarPerfilResponse;
import com.anuncios.ws.perfil.PegarPerfisAssociadoResponse;
import com.anuncios.ws.perfil.PegarTodosPerfilResponse;
import com.anuncios.ws.perfil.PerfilRegisto;
import com.anuncios.ws.perfil.RegistarPerfilResponse;

public class ResponseFactoryPerfil {
    public static RegistarPerfilResponse registarPerfil (String mensagem, PerfilRegisto dados, int status ){
      RegistarPerfilResponse  response = new RegistarPerfilResponse();
      response.setMensagem(mensagem);
      response.setStatusCode(status);
      response.setDados(dados);
      return response;
    } 

    public static PegarTodosPerfilResponse pegarTodosPerfis (String mensagem, List <PerfilRegisto> dados, int status ){
      PegarTodosPerfilResponse  response = new PegarTodosPerfilResponse();
      response.setMensagem(mensagem);
      response.setStatusCode(status);
      if (dados!= null) {
        response.getDados().addAll(dados);      
      }
      return response;
    }

    public static AssociarPerfilResponse associarPerfil (String mensagem, int status ){
      AssociarPerfilResponse  response = new AssociarPerfilResponse();
      response.setMensagem(mensagem);
      response.setStatusCode(status);
      return response;
    }

    public static PegarPerfisAssociadoResponse pegarPerfisAssociados (String mensagem, List <PerfilRegisto> dados, int status ){
      PegarPerfisAssociadoResponse  response = new PegarPerfisAssociadoResponse();
      response.setMensagem(mensagem);
      response.setStatusCode(status);
      if (dados!= null) {
        response.getDados().addAll(dados);        
      }
      return response;
    }

}
