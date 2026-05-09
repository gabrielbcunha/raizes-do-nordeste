package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.itemPedido.ItemPedidoCreateRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoCreateRequest {

    @NotNull(message="O ID do cliente deve existir")
    @Positive(message="O ID do cliente deve ser positivo")
    private Long clienteId;

    @NotNull(message="O ID da unidade deve existir")
    @Positive(message="O ID da unidade deve ser positivo")
    private Long unidadeId;

    @NotBlank(message="O canal de pedido deve existir")
    private String canalPedido;

    @NotEmpty(message="A lista de Itens não pod estar vazia")
    private List<ItemPedidoCreateRequest> itens;

    @NotBlank(message="A forma de pagamento deve existir")
    private String formaPagamento;

    @NotBlank(message="O tipo de entrega deve existir")
    private String tipoEntrega;

}
