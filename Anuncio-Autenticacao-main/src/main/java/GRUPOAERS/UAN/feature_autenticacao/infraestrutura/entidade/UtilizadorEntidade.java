package GRUPOAERS.UAN.feature_autenticacao.infraestrutura.entidade;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import GRUPOAERS.UAN.feature_autenticacao.dominio.model.RoleUtilizador;
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
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uuid;
    @Column(unique = true, nullable = false)
    private String nome;
    @Column(unique = true, nullable = true)
    private String telefone;
    @Column(unique = true, nullable = false)
    private String email;
    private RoleUtilizador role;
    private int idPerfilActivo;
    private int saldo;
    private String senha;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
