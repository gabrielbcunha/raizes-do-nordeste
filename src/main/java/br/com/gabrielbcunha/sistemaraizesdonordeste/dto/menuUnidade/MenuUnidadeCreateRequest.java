package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.menuUnidade;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuUnidadeCreateRequest {

    @NotNull(message="O ID da unidade deve existir")
    @Positive(message="O ID da unidade deve ser positivo")
    private Long unidadeId;

    @NotNull(message="O ID do item deve existir")
    @Positive(message="O ID do item deve ser positivo")
    private Long itemId;

    @NotNull(message="A disponibilidade do item deve existir")
    private Boolean disponivel;

}
