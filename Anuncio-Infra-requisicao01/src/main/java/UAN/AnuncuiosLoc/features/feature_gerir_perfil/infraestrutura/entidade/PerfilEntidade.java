package UAN.AnuncuiosLoc.features.feature_gerir_perfil.infraestrutura.entidade;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;

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
