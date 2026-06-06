package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth.LoginRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth.TokenResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name="Autorização", description="Endpoints para o cadastro, modificação, leitura e exclusão de Clientes e login para todos os usuários")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary="Busca e valida a entrada de um usuário",
            description="Busca os dados de login enviados no banco de dados e valida a senha com a senha armazenada, retornando um token de validação de login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado e login validado"),
            @ApiResponse(responseCode = "401", description = "Login não validado, usuário ou senha incorretos")
    })
    public ResponseEntity<TokenResponse> realizarLogin(@Valid @RequestBody LoginRequest loginRequest) {
        TokenResponse login = authService.fazerLogin(loginRequest);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/cadastrar")
    @Operation(summary="Cadastra um novo usuário com Role Cliente",
                description="Endpoint para cadastro de usuário com role Cliente, cadastro realizado pelo próprio cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado  com sucesso"),
            @ApiResponse(responseCode = "422", description = "Usuário não cadastrado, alguma informação com preenchimento incorreto")
    })
    public ResponseEntity<ClienteCreateResponse> cadastrarCliente(@Valid @RequestBody ClienteCreateRequest cadastroClienteRequest) {
        ClienteCreateResponse cadastroCliente = authService.cadastrarNovoCliente(cadastroClienteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroCliente);
    }
}