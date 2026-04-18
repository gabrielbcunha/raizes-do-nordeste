package br.com.gabrielbcunha.sistemaraizesdonordeste.exception;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ApiError {
    private String error;
    private String message;
    private List<ErroDetalhe> details;
    private LocalDateTime timestamp;
    private String path;

    public ApiError(String error, String message, List<ErroDetalhe> details, LocalDateTime timestamp, String path) {
        this.error = error;
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
        this.path = path;
    }
}
