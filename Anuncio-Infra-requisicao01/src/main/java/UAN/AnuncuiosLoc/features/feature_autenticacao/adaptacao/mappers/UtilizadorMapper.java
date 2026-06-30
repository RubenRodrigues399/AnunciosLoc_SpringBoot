package UAN.AnuncuiosLoc.features.feature_autenticacao.adaptacao.mappers;

import com.anuncios.ws.autenticacao.*;
import UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.model.Utilizador;
import UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;

public class UtilizadorMapper {


    public static UtilizadorLogin toUtilizadorLogin (Utilizador dados){
        if (dados == null) return null;
        UtilizadorLogin utilizadorLogin = new UtilizadorLogin();
        utilizadorLogin.setEmail(dados.getEmail());
        utilizadorLogin.setNome(dados.getNome());
        utilizadorLogin.setRole(dados.getRole().toString());
        utilizadorLogin.setTelefone(dados.getTelefone());
        utilizadorLogin.setUuid(dados.getUuid());
        utilizadorLogin.setToken(dados.getToken());
        return utilizadorLogin;
    }

    public static UtilizadorRegisto toUtilizadorRegisto (UtilizadorEntidade dados){
        if (dados == null) return null;
        UtilizadorRegisto response = new UtilizadorRegisto();
        response.setUuid(dados.getUuid());
        response.setNome(dados.getNome());
        response.setEmail(dados.getEmail());
        response.setTelefone(dados.getTelefone());
        response.setSaldo(dados.getSaldo());
        response.setRole(dados.getRole().toString());
        response.setIdPerfilActivo(dados.getIdPerfilActivo());
        response.setNomePerfilActivo(dados.getNomePerfilActivo());
        response.setDataCriacao(dados.getCreatedAt().toString());
        response.setDataActualizacao(dados.getUpdatedAt().toString());
        return response;
    }

}
