package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteDeleteRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteDeleteResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.RecursoNaoEncontradoException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.RegraNegocioException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.ClienteMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Cliente;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Usuario;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.Perfil;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.ClienteRepository;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ClienteService {

    private final PasswordEncoder passwordEncoder;
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;

    @Value("${senha.anonimizado}")
    private String senhaAnomizado;

    public ClienteService(PasswordEncoder passwordEncoder, ClienteRepository clienteRepository, ClienteMapper clienteMapper, UsuarioRepository usuarioRepository, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.usuarioRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
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

    @Transactional
    public ClienteDeleteResponse deletarDadosCliente(Long id, ClienteDeleteRequest request){
        Cliente clienteBuscado =  clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));

        Usuario usuarioBuscado = usuarioRepository.findById(clienteBuscado.getUsuario().getId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        String consentimento = request.getConsentimento().toLowerCase().replaceAll("[^A-Za-z]+","");
        String userName = usuarioBuscado.getUsername();
        String senha = request.getSenha();


        if (consentimento.equals("aceito")) {
            UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(userName, senha);
            Authentication autenticacao = authenticationManager.authenticate(login);

            usuarioBuscado.setUserName("AnonimizadoCliente@id" + usuarioBuscado.getId() + "deletado.com");
            usuarioBuscado.setSenha(senhaAnomizado);
            usuarioRepository.save(usuarioBuscado);

            clienteBuscado.setNome("Anonimizado");
            clienteBuscado.setCpf("000-{" + clienteBuscado.getId() + "}");
            clienteBuscado.setNumContato("00-{" + clienteBuscado.getId() + "}");
            clienteBuscado.setUsuario(usuarioBuscado);
            clienteBuscado.setProgramaFidelidadeAtivo(false);
            clienteBuscado.setNumCadastroFidelidade("Anonimizado {" + clienteBuscado.getId() + "}");
            clienteBuscado.setQuantPontosFidelidade(0);
            clienteRepository.save(clienteBuscado);
        } else {
            throw new RegraNegocioException("Palavra de consentimento inválida");
        }
        ClienteDeleteResponse clienteDeletado = clienteMapper.toDtoDelete(clienteBuscado);
        clienteDeletado.setDataDelecao(LocalDateTime.now());
        return clienteDeletado;
    }

}
