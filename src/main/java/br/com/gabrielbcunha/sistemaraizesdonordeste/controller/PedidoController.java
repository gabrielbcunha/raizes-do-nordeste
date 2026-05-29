package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido.PedidoCancelarResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido.PedidoCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido.PedidoCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.CanalPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Pedidos", description="Endpoints para o cadastro, modificação, leitura e exclusão de pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping()
    @Operation(summary="Cadastra um novo Pedido",
            description="Endpoint para o cadastro de um novo pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Pedido não cadastrado, alguma informação requerida com preenchimento incorreto")
    })
    public ResponseEntity<PedidoCreateResponse> cadastrarPedido(@Valid @RequestBody PedidoCreateRequest pedidoCreateRequest) {
        PedidoCreateResponse cadastroPedido = pedidoService.cadastrarPedido(pedidoCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroPedido);
    }

    @GetMapping()
    @Operation(summary="Lista os Pedidos",
                description="Endpoint para listar todos os pedidos, pode ser filtra para listar por canalPedido especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista encontrada, podendo conter ou não conteúdo")
    })
    public ResponseEntity<Page<PedidoCreateResponse>> listarPedidos(@RequestParam(required = false) CanalPedido canalPedido, @PageableDefault(sort="unidade.id", direction= Sort.Direction.ASC) Pageable pageable ) {
        Page<PedidoCreateResponse> listarPedidos = pedidoService.listarPedidos(canalPedido, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listarPedidos);
    }


    @PostMapping("/cancelar/{id}")
    public ResponseEntity<PedidoCancelarResponse> cancelarPedido(@PathVariable Long id) {
        PedidoCancelarResponse cancelarPedido = pedidoService.cancelarPedido(id);
        return ResponseEntity.status(HttpStatus.OK).body(cancelarPedido);
    }


}
