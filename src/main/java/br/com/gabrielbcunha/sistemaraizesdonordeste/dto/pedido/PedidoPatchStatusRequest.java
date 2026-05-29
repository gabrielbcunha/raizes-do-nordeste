package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoPatchStatusRequest {

    @NotNull(message="O status do pedido deve existir")
    private StatusPedido statusPedido;

}
