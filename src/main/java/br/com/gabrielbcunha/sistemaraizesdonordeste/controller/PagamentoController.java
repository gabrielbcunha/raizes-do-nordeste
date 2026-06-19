package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pagamento.PagamentoRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pagamento.PagamentoResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamento")
@Tag(name="Pagamento", description = "Endpoints para realizar Mock de pagamento de pedido")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/pedido/{id}")
    @Operation(summary="Busca pedido e tenta realizar pagamento",
            description="Busca o pedido no banco de dados e tenta realizar o pagamento, faz uma simulação de se o pagamento foi aprovado ou não e retorna um novo status de pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado e mock de pagamento realizado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para acessar este recurso")
    })
    public ResponseEntity<PagamentoResponse> realizarPagamento(@PathVariable Long id, @Valid @RequestBody PagamentoRequest pagamentoRequest) {
        PagamentoResponse tentarPagamento = pagamentoService.tentarPagamento(id, pagamentoRequest);
        return ResponseEntity.ok(tentarPagamento);
    }

}
