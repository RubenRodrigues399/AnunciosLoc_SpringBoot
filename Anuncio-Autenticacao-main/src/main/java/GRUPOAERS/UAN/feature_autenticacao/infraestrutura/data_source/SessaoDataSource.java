package GRUPOAERS.UAN.feature_autenticacao.infraestrutura.data_source;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade.SessaoEntidade;

@Repository
public interface SessaoDataSource  extends JpaRepository<SessaoEntidade, Integer>{

}