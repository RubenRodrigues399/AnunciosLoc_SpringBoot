package GRUPOAERS.UAN.feature_autenticacao.adaptacao.mappers;

import com.anuncios.ws.autenticacao.UtilizadorLogin;
import com.anuncios.ws.autenticacao.UtilizadorRegisto;

import GRUPOAERS.UAN.feature_autenticacao.dominio.model.Utilizador;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade.PerfilEntidade;
import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade.UtilizadorEntidade;

public class UtilizadorMapper {
    public static UtilizadorEntidade toEntity (Utilizador utilizador){
        if (utilizador == null) return null;
        return new UtilizadorEntidade(utilizador.getUuid(), utilizador.getNome(), utilizador.getTelefone(),utilizador.getEmail(), utilizador.getRole(), utilizador.getIdPerfilActivo(),utilizador.getSaldo(),utilizador.getSenha(), utilizador.getCreatedAt(), utilizador.getUpdatedAt());
        
    }

    public static Utilizador toUtilizador (UtilizadorEntidade entidade, PerfilEntidade perfil){
        if (entidade == null) return null;
        return new Utilizador(entidade.getUuid(), entidade.getNome(), entidade.getTelefone(), entidade.getEmail(), entidade.getRole(),
        perfil.getId(),perfil.getTitulo(),entidade.getSaldo(),null, entidade.getSenha(), entidade.getCreatedAt(), entidade.getUpdatedAt());
    }

    public static UtilizadorRegisto toUtilizadorRegisto (Utilizador dados){
        if (dados == null) return null;
        UtilizadorRegisto utilizadorRegisto = new UtilizadorRegisto();
        utilizadorRegisto.setEmail(dados.getEmail());
        utilizadorRegisto.setNome(dados.getNome());
        utilizadorRegisto.setRole(dados.getRole().toString());
        utilizadorRegisto.setTelefone(dados.getTelefone());
        utilizadorRegisto.setSaldo(dados.getSaldo());
        utilizadorRegisto.setIdPerfilActivo(dados.getIdPerfilActivo());
        utilizadorRegisto.setNomePerfilActivo(dados.getNomePerfilActivo());
        utilizadorRegisto.setUuid(dados.getUuid());
        utilizadorRegisto.setDataCriacao(dados.getCreatedAt().toString());
        utilizadorRegisto.setDataActualizacao(dados.getUpdatedAt().toString());
        return utilizadorRegisto;
    }

    public static UtilizadorLogin toUtilizadorLogin (Utilizador dados){
        if (dados == null) return null;
        UtilizadorLogin utilizadorLogin = new UtilizadorLogin();
        utilizadorLogin.setEmail(dados.getEmail());
        utilizadorLogin.setNome(dados.getNome());
        utilizadorLogin.setRole(dados.getRole().toString());
        utilizadorLogin.setTelefone(dados.getTelefone());
        utilizadorLogin.setSaldo(dados.getSaldo());
        utilizadorLogin.setUuid(dados.getUuid());
        utilizadorLogin.setToken(dados.getToken());
        utilizadorLogin.setIdPerfilActivo(dados.getIdPerfilActivo());
        return utilizadorLogin;
    }

}
