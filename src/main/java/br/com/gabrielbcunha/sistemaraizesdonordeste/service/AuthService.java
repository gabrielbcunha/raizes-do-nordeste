package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth.LoginRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth.TokenResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.ClienteMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Cliente;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Usuario;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.Perfil;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.ClienteRepository;
import br.com.gabrielbcunha.sistemaraizesdonordeste.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtService jwtService, AuthenticationManager authenticationManager, ClienteRepository clienteRepository, ClienteMapper clienteMapper, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ClienteCreateResponse cadastrarCliente(ClienteCreateRequest request){

        Usuario novoUsuario = new Usuario();
        novoUsuario.setUserName(request.getEmail());
        String senha = this.passwordEncoder.encode(request.getSenha());
        novoUsuario.setSenha(senha);
        novoUsuario.setPerfil(Perfil.ROLE_CLIENTE);

        String numeroFidelidade = java.util.UUID.randomUUID().toString();

        Cliente novoCliente = clienteMapper.toEntity(request);
        novoCliente.setUsuario(novoUsuario);
        novoCliente.setNumCadastroFidelidade(numeroFidelidade);
        novoCliente.setQuantPontosFidelidade(0);

        Cliente cliente = clienteRepository.save(novoCliente);
        return clienteMapper.toDto(cliente);
    }

    public TokenResponse fazerLogin(LoginRequest request){
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(request.getUserName(), request.getSenha());
        Authentication autenticacao = authenticationManager.authenticate(login);
        Usuario usuario = (Usuario) autenticacao.getPrincipal();
        String Token = jwtService.geradorToken(usuario);
        TokenResponse tokenResponse = new TokenResponse(Token);
        return tokenResponse;
    }


}
