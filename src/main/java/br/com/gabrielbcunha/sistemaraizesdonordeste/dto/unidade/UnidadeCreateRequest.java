package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.unidade;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.CanalPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.TipoEntrega;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UnidadeCreateRequest {

    @NotBlank(message="O nome da únidade deve existir")
    private String nome;

    @NotBlank(message="A localização da únidade deve existir")
    private String localizacao;

    @NotNull(message="Os canais suportados devem existir")
    private List<CanalPedido> canaisSuportados;

    @NotNull(message="Os tipos de entrega suportados devem existir")
    private List<TipoEntrega> entregasSuportadas;

}
