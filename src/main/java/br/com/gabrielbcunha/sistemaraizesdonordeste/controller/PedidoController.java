package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido.PedidoCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido.PedidoCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public ResponseEntity<Page<PedidoCreateResponse>> listarTodosPedidos(@PageableDefault(sort="unidade.id", direction= Sort.Direction.ASC) Pageable pageable) {
        Page<PedidoCreateResponse> listarPedidos = pedidoService.listarTodosPedidos(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listarPedidos);
    }


}
