package br.com.gabrielbcunha.sistemaraizesdonordeste.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                "DADOS_INVALIDOS",
                "Um ou mais itens estão inválidos, faça o preenchimento correto e tente novamente",
                detalhesDoErro,
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
    }



}


