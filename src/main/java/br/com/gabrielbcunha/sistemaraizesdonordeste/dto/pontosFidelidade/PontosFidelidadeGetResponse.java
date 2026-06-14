package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pontosFidelidade;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Cliente;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Pedido;

import java.time.LocalDateTime;

public class PontosFidelidadeGetResponse {

    private Cliente cliente;

    private Pedido pedido;

    private String numCadastroFidelidade;

    private Integer quantidadePontos;

    private LocalDateTime dataTransacao;

}
