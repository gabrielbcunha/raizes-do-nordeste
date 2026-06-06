package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.ClienteMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Cliente;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Usuario;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.Perfil;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.ClienteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final PasswordEncoder passwordEncoder;
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteService(PasswordEncoder passwordEncoder, ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.passwordEncoder = passwordEncoder;
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
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



}
