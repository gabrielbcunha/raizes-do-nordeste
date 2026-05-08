package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.menuUnidade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MenuUnidadeCreateResponse {

    private Long unidadeId;

    private Long itemId;

    private Boolean disponivel;

}
