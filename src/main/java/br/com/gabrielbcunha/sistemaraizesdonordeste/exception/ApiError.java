package br.com.gabrielbcunha.sistemaraizesdonordeste.exception;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.Erros;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private Erros error;
    private int codeError;
    private String message;
    private List<ErroDetalhe> details;
    private LocalDateTime timestamp;
    private String path;

    public ApiError(Erros error, int codeError, String message, List<ErroDetalhe> details, LocalDateTime timestamp, String path) {
        this.error = error;
        this.codeError = codeError;
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
        this.path = path;
    }

    public ApiError(Erros error, int codeError, String message, LocalDateTime timestamp, String path) {
        this.error = error;
        this.codeError = codeError;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
    }

}
