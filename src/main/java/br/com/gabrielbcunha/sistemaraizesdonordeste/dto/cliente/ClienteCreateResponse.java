package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteCreateResponse {

    private String nome;

    private String numContato;

    private String email;
}
