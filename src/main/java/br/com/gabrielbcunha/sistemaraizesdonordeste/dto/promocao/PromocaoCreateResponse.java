package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.promocao;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.MenuUnidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Unidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.CanalPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.FormaPagamento;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.TipoEntrega;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.TipoPromocao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PromocaoCreateResponse {
	
	private String nomePromocao;
	
	private String codigoPromocao;
	
	private Set<Unidade> unidades;
	
	private Set<MenuUnidade> itensMenu;
	
    private List<TipoEntrega> entregasEscritasNaPromocao;

	private List<CanalPedido> canaisEscritosNaPromocao;

	private List<FormaPagamento> pagamentosEscritosNaPromocao;

	private TipoPromocao tipoPromocao;

	private BigDecimal valorPromocao;
	
	private LocalDateTime inicioPromocao;
	
	private LocalDateTime terminoPromocao;
	
}