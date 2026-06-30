package UAN.AnuncuiosLoc.core.utils.configuracoes.configuracao_replicacao.ultima_execucao_controller;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.*;

@Entity
@Table(name = "ultimaExecucao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UltimaExecucaoEntidade {
    @Id
    @Column(unique = false)  
    private String tabela;

    @Column(name = "ultima_execucao")
    private LocalDateTime ultimaData;
    
}
