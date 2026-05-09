package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.estoqueUnidade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EstoqueUnidadeCreateResponse {

    private Long unidadeId;

    private Long itemId;

    private Integer quantidade;

}