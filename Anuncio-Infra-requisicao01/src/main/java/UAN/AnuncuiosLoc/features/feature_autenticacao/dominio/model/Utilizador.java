package UAN.AnuncuiosLoc.features.feature_autenticacao.dominio.model;
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
    private String token;
    private String senha;
}
