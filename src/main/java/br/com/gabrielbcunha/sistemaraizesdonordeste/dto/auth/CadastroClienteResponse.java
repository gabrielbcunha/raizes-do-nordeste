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

    private String nome;

    private String numContato;

    private String email;
}
