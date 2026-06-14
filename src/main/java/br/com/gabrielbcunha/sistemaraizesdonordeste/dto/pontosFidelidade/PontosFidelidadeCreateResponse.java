package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pontosFidelidade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PontosFidelidadeCreateResponse {

    private Long clienteId;

    private Long pedidoId;

    private String numCadastroFidelidade;

    private Integer quantidadePontosFidelidadeAnterior;

    private Integer quantidadePontosFidelidadeNova;

}
