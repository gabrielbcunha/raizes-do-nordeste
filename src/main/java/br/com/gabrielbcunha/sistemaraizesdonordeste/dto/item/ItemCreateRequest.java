package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemCreateRequest {

    @NotBlank(message = "O nome do produto deve existir")
    private String nome;

    @NotBlank(message = "A descrição do produto deve existir")
    private String descricao;

    @NotNull(message="A quantidade de pontos do programa de fidelidade deve existir")
    @PositiveOrZero(message="A quantidade de ponto do programa de fidelidade deve ser positiva ou zero")
    private Integer quantidadePontosFidelidade;

    @NotNull(message="O preço do produto deve existir")
    @PositiveOrZero(message="O preço deve ser maior ou igual a zero")
    private BigDecimal preco;

}
