package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.itemPedido.ItemPedidoCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.CanalPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoCancelarResponse {

    private Long clienteId;

    private Long unidadeId;

    private CanalPedido canalPedido;

    private List<ItemPedidoCreateResponse> itens;

    private BigDecimal valorTotal;

    private StatusPedido statusPedido;

}
