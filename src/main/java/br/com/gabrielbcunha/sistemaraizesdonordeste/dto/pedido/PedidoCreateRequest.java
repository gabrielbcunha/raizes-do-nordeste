package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.itemPedido.ItemPedidoCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.CanalPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.FormaPagamento;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.TipoEntrega;
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

    @NotNull(message="O canal de pedido deve existir")
    private CanalPedido canalPedido;

    @NotEmpty(message="A lista de Itens não pod estar vazia")
    private List<ItemPedidoCreateRequest> itens;

    @NotNull(message="A forma de pagamento deve existir")
    private FormaPagamento formaPagamento;

    @NotNull(message="O tipo de entrega deve existir")
    private TipoEntrega tipoEntrega;

    @NotEmpty(message="Deve ser informado se será aplicado os pontos fidelidade ou não")
    private boolean usarPontosFidelidade;

    @NotEmpty(message="Deve ser informado se será aplicado código de desconto ou não")
    private boolean usarCodigoDeDesconto;

    private String codigoDesconto;

}
