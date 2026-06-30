package GRUPOAERS.UAN.feature_autenticacao.dominio.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistoParams {
    private String nome;
    private String email;
    private RoleUtilizador role;
    private String senha;
}
