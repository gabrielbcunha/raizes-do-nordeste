package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido.PedidoCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido.PedidoCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping()
    public ResponseEntity<PedidoCreateResponse> cadastrarPedido(@Valid @RequestBody PedidoCreateRequest pedidoCreateRequest) {
        PedidoCreateResponse cadastroPedido = pedidoService.cadastrarPedido(pedidoCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroPedido);
    }


}
