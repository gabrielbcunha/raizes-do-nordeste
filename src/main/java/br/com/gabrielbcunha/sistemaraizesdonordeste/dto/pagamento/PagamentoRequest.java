package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pagamento;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.FormaPagamento;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoRequest {

    @NotNull(message="O ID do pedido deve existir")
    private Long pedidoId;

    @NotNull(message="A forma de pagamento deve existir")
    private FormaPagamento formaPagamento;

}
