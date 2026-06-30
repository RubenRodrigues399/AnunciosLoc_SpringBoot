package UAN.AnuncuiosLoc.features.feature_gerir_perfil.dominio.model;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Perfil {
    private int id;
    private String titulo;
    private LocalDateTime dataCriacao;
    private String uuidUtilizadorCriador;
}
