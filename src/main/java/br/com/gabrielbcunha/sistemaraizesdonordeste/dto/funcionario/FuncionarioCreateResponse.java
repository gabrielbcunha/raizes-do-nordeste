package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.Cargo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FuncionarioCreateResponse {

    private String nome;

    private Cargo cargo;

}
