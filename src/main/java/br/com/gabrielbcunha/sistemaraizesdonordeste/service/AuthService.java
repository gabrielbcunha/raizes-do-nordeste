package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth.LoginRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth.TokenResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.ClienteMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Usuario;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.ClienteRepository;
import br.com.gabrielbcunha.sistemaraizesdonordeste.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ClienteService clienteService;

    public AuthService(JwtService jwtService, AuthenticationManager authenticationManager, ClienteService clienteService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.clienteService = clienteService;
    }

    public ClienteCreateResponse cadastrarNovoCliente(ClienteCreateRequest cadastroClienteRequest) {
        return clienteService.cadastrarCliente(cadastroClienteRequest);
    }

    public TokenResponse fazerLogin(LoginRequest request){
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(request.getUserName(), request.getSenha());
        Authentication autenticacao = authenticationManager.authenticate(login);
        Usuario usuario = (Usuario) autenticacao.getPrincipal();
        String Token = jwtService.geradorToken(usuario);
        TokenResponse tokenResponse = new TokenResponse(Token);
        log.info("Login realizado com sucesso para o usuário [{}]", request.getUserName());
        return tokenResponse;
    }


}
