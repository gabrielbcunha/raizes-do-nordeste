package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario.FuncionarioCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario.FuncionarioCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.RecursoNaoEncontradoException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.FuncionarioMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Funcionario;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Unidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Usuario;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.Perfil;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.FuncionarioRepository;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.UnidadeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final UnidadeRepository unidadeRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper, PasswordEncoder passwordEncoder, UnidadeRepository unidadeRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
        this.passwordEncoder = passwordEncoder;
        this.unidadeRepository = unidadeRepository;
    }

    public FuncionarioCreateResponse cadastrarAtendente(FuncionarioCreateRequest createRequest){

        Usuario novoUsuario = new Usuario();
        novoUsuario.setUserName(createRequest.getNumeroCracha());
        String senha = this.passwordEncoder.encode(createRequest.getSenha());
        novoUsuario.setSenha(senha);
        novoUsuario.setPerfil(Perfil.ROLE_ATENDENTE);

        Unidade unidadeSelecionada = unidadeRepository.findById(createRequest.getIdUnidade())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade de ID: " + createRequest.getIdUnidade() + " não encontrada"));

        Funcionario novoAtendente = funcionarioMapper.toEntity(createRequest);
        novoAtendente.setUsuario(novoUsuario);
        novoAtendente.setUnidade(unidadeSelecionada);

        Funcionario atendenteCriado = funcionarioRepository.save(novoAtendente);
        return funcionarioMapper.toDto(atendenteCriado);
    }



}
