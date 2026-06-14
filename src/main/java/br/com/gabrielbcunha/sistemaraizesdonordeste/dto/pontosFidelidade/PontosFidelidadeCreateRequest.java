package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pontosFidelidade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PontosFidelidadeCreateRequest {

    @NotBlank(message="O número de cadastro de fidelidade deve existir")
    private String numCadastroFidelidade;

    @NotNull(message="O Id do pedido deve existir")
    private Long pedidoId;

    @NotNull(message="A quantidade de pontos de fidelidade deve existir")
    private Integer quantidadePontos;

}
