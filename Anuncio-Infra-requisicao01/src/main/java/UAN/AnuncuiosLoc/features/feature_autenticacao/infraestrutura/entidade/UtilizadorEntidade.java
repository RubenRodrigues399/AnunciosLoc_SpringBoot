package UAN.AnuncuiosLoc.features.feature_autenticacao.infraestrutura.entidade;
import java.time.LocalDateTime;
import org.hibernate.annotations.UpdateTimestamp;

import UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.model.RoleUtilizador;
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
@Table(name = "utilizador")
public class UtilizadorEntidade {
    @Id
    @Column(unique = true, nullable = false)
    private String uuid;
    @Column(unique = true, nullable = false)
    private String nome;
    @Column(unique = true, nullable = true)
    private String telefone;
    @Column(unique = true, nullable = false)
    private String email;
    private int saldo;
    private RoleUtilizador role;
    private int idPerfilActivo;
    private String nomePerfilActivo;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
