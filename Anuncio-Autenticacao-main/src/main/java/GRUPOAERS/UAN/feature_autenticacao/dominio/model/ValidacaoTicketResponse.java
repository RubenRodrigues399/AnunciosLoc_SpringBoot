package GRUPOAERS.UAN.feature_autenticacao.dominio.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidacaoTicketResponse {
    private String uuid;
    private String nome;
    private RoleUtilizador role;
    private int saldo;
}
