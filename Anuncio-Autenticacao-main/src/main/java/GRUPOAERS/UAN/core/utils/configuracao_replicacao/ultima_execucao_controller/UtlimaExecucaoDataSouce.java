package GRUPOAERS.UAN.core.utils.configuracao_replicacao.ultima_execucao_controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtlimaExecucaoDataSouce extends JpaRepository<UltimaExecucaoEntidade, String> {
    
    
}
