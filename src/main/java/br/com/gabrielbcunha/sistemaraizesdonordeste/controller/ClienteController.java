package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteDeleteRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteDeleteResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary="Busca e anonimiza dados de um cliente",
                description="Busca o cliente no banco de dados e anonimiza os seus dados sensíveis quando solicitado, atendendo os padrões da LGPD")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Usuário encontrado e dados anonimizados"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "406", description = "Palavra de consentimento inválida"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para acessar este recurso")
    })
    public ResponseEntity<ClienteDeleteResponse> deleteCliente(@PathVariable Long id, @Valid @RequestBody ClienteDeleteRequest request) {
        ClienteDeleteResponse clienteDeletado = clienteService.deletarDadosCliente(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(clienteDeletado);
    }

}
