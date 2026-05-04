package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CadastroClienteResponse {

    @NotBlank(message="O nome do cliente deve existir")
    private String nome;

    @NotBlank(message="O número de contato do cliente deve existir")
    private String numContato;

    @NotBlank(message="O email do usuário deve existir")
    @Email
    private String email;
}
