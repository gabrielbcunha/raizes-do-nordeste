package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pagamento.PagamentoRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pagamento.PagamentoResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/pedido/{id}")
    public ResponseEntity<PagamentoResponse> realizarPagamento(@PathVariable Long id, @Valid @RequestBody PagamentoRequest pagamentoRequest) {
        PagamentoResponse tentarPagamento = pagamentoService.tentarPagamento(id, pagamentoRequest);
        return ResponseEntity.ok(tentarPagamento);
    }

}
