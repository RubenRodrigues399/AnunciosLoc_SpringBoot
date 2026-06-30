package GRUPOAERS.UAN.feature_autenticacao.dominio.model;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Utilizador {
    private String uuid;
    private String nome;
    private String telefone;
    private String email;
    private RoleUtilizador role;
    private int idPerfilActivo;
    private String nomePerfilActivo;
    private int saldo;
    private String token;
    private String senha;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
