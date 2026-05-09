package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.estoqueUnidade;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoqueUnidadeCreateRequest {

    @NotNull(message="O ID da unidade deve existir")
    @Positive(message="O ID da unidade deve ser positivo")
    private Long unidadeId;

    @NotNull(message="O ID do item deve existir")
    @Positive(message="O ID do item deve ser positivo")
    private Long itemId;

    @NotNull(message="A quantidade do item deve existir")
    @PositiveOrZero(message="A quantidade do item deve ser positivo ou zero")
    private Integer quantidade;

}