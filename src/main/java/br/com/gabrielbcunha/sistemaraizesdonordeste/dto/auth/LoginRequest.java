package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message="O username do usuário deve existir")
    private String userName;

    @NotBlank(message="A senha do usuário deve existir")
    private String senha;
}
