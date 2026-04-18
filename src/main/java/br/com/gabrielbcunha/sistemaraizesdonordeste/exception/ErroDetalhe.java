package br.com.gabrielbcunha.sistemaraizesdonordeste.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErroDetalhe {

    private String field;
    private String issue;

    public ErroDetalhe(String field, String issue) {
        this.field = field;
        this.issue = issue;
    }
}
