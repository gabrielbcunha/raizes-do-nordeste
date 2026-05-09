package br.com.gabrielbcunha.sistemaraizesdonordeste.exception;

import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.Erros;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException exception, HttpServletRequest request) {

        List<ErroDetalhe> detalhesDoErro = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ErroDetalhe(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        ApiError erro = new ApiError(
                Erros.DADOS_INVALIDOS,
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Um ou mais itens estão inválidos, faça o preenchimento correto e tente novamente",
                detalhesDoErro,
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception exception, HttpServletRequest request) {

        ApiError erro = new ApiError(
                Erros.FALHA_INTERNA,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> badCredential(Exception exception, HttpServletRequest request) {

        ApiError erro = new ApiError(
                Erros.NAO_AUTORIZADO,
                HttpStatus.UNAUTHORIZED.value(),
                "Email ou senha incorretos",
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiError> recursoNaoEncontrado(Exception exception, HttpServletRequest request) {

        ApiError erro = new ApiError(
                Erros.RECURSO_NAO_ENCONTRADO,
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<ApiError> estoqueInsuficiente(Exception exception, HttpServletRequest request) {

        ApiError erro = new ApiError(
                Erros.ESTOQUE_INSUFICIENTE,
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                exception.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
    }

}


