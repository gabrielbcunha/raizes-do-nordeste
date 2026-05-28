package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pagamento;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.FormaPagamento;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.StatusPagamento;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagamentoResponse {

    public Long pedidoId;

    public FormaPagamento formaPagamento;

    public StatusPedido statusPedido;

    public StatusPagamento statusPagamento;

    public Integer quantidadePontosFidelidade;

}
