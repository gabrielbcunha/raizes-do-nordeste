package br.com.gabrielbcunha.sistemaraizesdonordeste.controller;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth.CadastroClienteRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth.CadastroClienteResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth.LoginRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth.TokenResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> realizarLogin(@Valid @RequestBody LoginRequest loginRequest) {
        TokenResponse login = authService.fazerLogin(loginRequest);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<CadastroClienteResponse> cadastrarCliente(@Valid @RequestBody CadastroClienteRequest cadastroClienteRequest) {
        CadastroClienteResponse cadastroClienteResponse = authService.cadastrarCliente(cadastroClienteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroClienteResponse);
    }


}
