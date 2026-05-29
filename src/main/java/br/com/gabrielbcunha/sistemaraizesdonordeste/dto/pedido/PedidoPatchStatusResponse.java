package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoPatchStatusResponse {

    private Long pedidoId;

    private StatusPedido statusPedido;
}
