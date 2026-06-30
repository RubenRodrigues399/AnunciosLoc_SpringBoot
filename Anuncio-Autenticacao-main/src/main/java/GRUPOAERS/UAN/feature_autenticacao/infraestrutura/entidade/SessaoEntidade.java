package GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sessao")
public class SessaoEntidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String uuidUtilizador;
    private String token;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
}
