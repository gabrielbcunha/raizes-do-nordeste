package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.Cargo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioCreateRequest {

    @NotNull(message="O ID da únidade deve existir")
    private Long idUnidade;

    @NotNull(message="O cargo do funcionário deve existir")
    private Cargo cargo;

    @NotBlank(message="O nome do funcionário deve existir")
    private String nome;

    @NotBlank(message="O número do crachá do funcionário deve existir")
    private String numeroCracha;

    @NotBlank(message="A senha do funcionário deve existir")
    @Size(min=6, message="A senha deve conter ao mínimo 6 caracteres")
    private String senha;

}
