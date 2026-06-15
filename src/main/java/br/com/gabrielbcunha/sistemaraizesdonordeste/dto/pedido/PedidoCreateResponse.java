package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.itemPedido.ItemPedidoCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.ItemPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.CanalPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.FormaPagamento;
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
public class PedidoCreateResponse {

    private Long clienteId;

    private Long unidadeId;

    private CanalPedido canalPedido;

    private List<ItemPedidoCreateResponse> itens;

    private FormaPagamento formaPagamento;

    private BigDecimal valorTotal;

    private BigDecimal valorDesconto;

    private BigDecimal valorTotalDesconto;

}
