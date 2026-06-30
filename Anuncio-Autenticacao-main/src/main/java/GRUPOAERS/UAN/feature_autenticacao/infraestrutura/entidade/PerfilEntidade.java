package GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "perfil")
public class PerfilEntidade {
    @Id
    @Column(unique = true, nullable = false)
    private int id;
    @Column(unique = true, nullable = false)
    private String titulo;
    private String uuidUtilizadorCriador;
    private LocalDateTime dataCriacao;
}
