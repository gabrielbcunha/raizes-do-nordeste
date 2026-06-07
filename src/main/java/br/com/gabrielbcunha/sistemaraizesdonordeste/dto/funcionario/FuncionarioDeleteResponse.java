package br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDeleteResponse {

    private Long id;
    private String nomeFuncionario;
    private String responsavelPelaDelecao;
    private LocalDateTime dataDelecao;

}
