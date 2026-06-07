package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioDeleteRequest {

    @NotBlank(message="A mensagem de consentimento deve existir")
    private String consentimento;

    @NotBlank(message="A senha do responsável deve existir")
    private String senha;

}
