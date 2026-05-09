package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.itemPedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemPedidoCreateResponse {

    public Long itemId;

    public Integer quantidade;

}
