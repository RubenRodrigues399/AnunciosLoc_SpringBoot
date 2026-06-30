package UAN.AnuncuiosLoc.features.feature_anuncio.infraestrutura.entidade;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "politicaRLPerfil")
public class PoliticaRLPerfilEntidade {
    @Id
    @Column(updatable = false)
    private int id;
    private int idAnuncio;
    private int idPerfil;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;  
}
