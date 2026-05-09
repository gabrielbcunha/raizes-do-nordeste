package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.itemPedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoCreateRequest {

    @NotNull(message="O ID do item deve existir")
    private Long itemId;

    @NotNull(message="A quantidade do item deve existir")
    @Positive(message="A quantidade deve ser positiva")
    private Integer quantidade;

}