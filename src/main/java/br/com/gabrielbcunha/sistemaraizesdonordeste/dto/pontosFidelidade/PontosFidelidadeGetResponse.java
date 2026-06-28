package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pontosFidelidade;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PontosFidelidadeGetResponse {

    private Long clienteId;

    private Long pedidoId;

    private String numCadastroFidelidade;

    private Integer quantidadePontos;

    private LocalDateTime dataTransacao;

}
