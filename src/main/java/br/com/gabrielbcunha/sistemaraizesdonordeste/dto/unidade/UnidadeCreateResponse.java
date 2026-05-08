package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.unidade;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.CanalPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.TipoEntrega;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UnidadeCreateResponse {

    private String nome;

    private String localizacao;

    private List<CanalPedido> canaisSuportados;

    private List<TipoEntrega> entregasSuportadas;
}
