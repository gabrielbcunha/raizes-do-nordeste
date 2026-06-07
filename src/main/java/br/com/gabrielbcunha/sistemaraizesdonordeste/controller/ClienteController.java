package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteDeleteRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteDeleteResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.ClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@Tag(name="Clientes", description="Endpoints para atualização de dados e anonimização de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteDeleteResponse> deleteCliente(@PathVariable Long id, @Valid @RequestBody ClienteDeleteRequest request) {
        ClienteDeleteResponse clienteDeletado = clienteService.deletarDadosCliente(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(clienteDeletado);
    }

}
