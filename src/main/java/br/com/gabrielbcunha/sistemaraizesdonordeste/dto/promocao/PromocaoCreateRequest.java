package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.promocao;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.CanalPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.FormaPagamento;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.TipoEntrega;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.TipoPromocao;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PromocaoCreateRequest {	
	
	@NotBlank(message="O nome da promoção deve existir")
	private String nomePromocao;
	
	@NotBlank(message="O código da promoção deve existir")
	private String codigoPromocao;
	
	@NotEmpty(message="A lista de itens não pode estar vazia")
	private List<Long> unidadesIds;
	
	@NotEmpty(message="A lista de intens do menu não pode estar vazia")
	private List<Long> menuUnidadeIds;
	
	@NotEmpty(message="A lista de tipos de entregas não pode estar vazia")
    private List<TipoEntrega> entregasEscritasNaPromocao;

	@NotEmpty(message="A lista de canais para pedido não pode estar vazia")
	private List<CanalPedido> canaisEscritosNaPromocao;

	@NotEmpty(message="A lista de formas de pagamento não pode estar vazia")
	private List<FormaPagamento> pagamentosEscritosNaPromocao;

	@NotNull(message="O tipo de promoção não pode estar vazio")
	private TipoPromocao tipoPromocao;

	@NotNull(message="O valor do desconto não pode estar vazio")
	private BigDecimal valorPromocao;
	
	@NotNull(message="A data de inicio da promoção não pode estar vazia")
	private LocalDateTime inicioPromocao;
	
    @NotNull(message="A data de termino da promoção não pode estar vazia")
    @Future
	private LocalDateTime terminoPromocao;
	
}