package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemCreateResponse {

    private String nome;

    private String descricao;

    private BigDecimal preco;

}
