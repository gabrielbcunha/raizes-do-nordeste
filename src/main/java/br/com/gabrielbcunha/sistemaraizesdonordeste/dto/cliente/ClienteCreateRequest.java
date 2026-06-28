package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class ClienteCreateRequest {

    @NotBlank(message="O nome do cliente deve existir")
    private String nome;

    @NotBlank(message="O CPF do cliente deve existir")
    @CPF(message="CPF com formato inválido")
    private String cpf;

    @NotBlank(message="O número de contato do cliente deve existir")
    private String numContato;

    @NotBlank(message="O email do usuário deve existir")
    @Email
    private String email;

    @NotBlank(message="A senha do usuário deve existir")
    @Size(min=6, message="A senha deve conter ao mínimo 6 caracteres")
    private String senha;

    @NotNull(message = "Deve ser informado se os pontos fidelidade estão ativos")
    private boolean pontosFidelidadeAtivos;

}
