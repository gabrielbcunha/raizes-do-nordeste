package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario.FuncionarioCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario.FuncionarioCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.RecursoNaoEncontradoException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.FuncionarioMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Funcionario;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Unidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Usuario;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.Cargo;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.enums.Perfil;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.FuncionarioRepository;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.UnidadeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public FuncionarioCreateResponse cadastrarAtendente(FuncionarioCreateRequest createRequest){

        Funcionario novoAtendente = funcionarioMapper.toEntity(createRequest);
        novoAtendente.setUsuario(cadastrarUsuario(createRequest, Perfil.ROLE_ATENDENTE));
        novoAtendente.setUnidade(procurarUnidade(createRequest));
        novoAtendente.setCargo(Cargo.ATENDENTE);

        Funcionario atendenteCriado = funcionarioRepository.save(novoAtendente);
        return funcionarioMapper.toDto(atendenteCriado);
    }

    @Transactional
    public FuncionarioCreateResponse cadastrarCozinheiro(FuncionarioCreateRequest createRequest){

        Funcionario novoCozinheiro = funcionarioMapper.toEntity(createRequest);
        novoCozinheiro.setUsuario(cadastrarUsuario(createRequest, Perfil.ROLE_COZINHEIRO));
        novoCozinheiro.setUnidade(procurarUnidade(createRequest));
        novoCozinheiro.setCargo(Cargo.COZINHEIRO);

        Funcionario cozinheiroCriado = funcionarioRepository.save(novoCozinheiro);
        return funcionarioMapper.toDto(cozinheiroCriado);
    }

    @Transactional
    public FuncionarioCreateResponse cadastrarAdministrativo(FuncionarioCreateRequest createRequest){

        Funcionario novoAdministrativo = funcionarioMapper.toEntity(createRequest);
        novoAdministrativo.setUsuario(cadastrarUsuario(createRequest, Perfil.ROLE_ADMINISTRATIVO));
        novoAdministrativo.setUnidade(procurarUnidade(createRequest));
        novoAdministrativo.setCargo(Cargo.ADMINISTRATIVO);

        Funcionario administrativoCriado = funcionarioRepository.save(novoAdministrativo);
        return funcionarioMapper.toDto(administrativoCriado);
    }

    @Transactional
    public FuncionarioCreateResponse cadastrarGerente(FuncionarioCreateRequest createRequest){

        Funcionario novoGerente = funcionarioMapper.toEntity(createRequest);
        novoGerente.setUsuario(cadastrarUsuario(createRequest, Perfil.ROLE_GERENTE));
        novoGerente.setUnidade(procurarUnidade(createRequest));
        novoGerente.setCargo(Cargo.GERENTE);

        Funcionario gerenteCriado = funcionarioRepository.save(novoGerente);
        return funcionarioMapper.toDto(gerenteCriado);
    }


    private Usuario cadastrarUsuario(FuncionarioCreateRequest request, Perfil perfil){

        Usuario novoUsuario = new Usuario();
        novoUsuario.setUserName(request.getNumeroCracha());
        String senha = this.passwordEncoder.encode(request.getSenha());
        novoUsuario.setSenha(senha);
        novoUsuario.setPerfil(perfil);
        return novoUsuario;
    }


    private Unidade procurarUnidade(FuncionarioCreateRequest request){
        Unidade unidadeSelecionada = unidadeRepository.findById(request.getIdUnidade())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade de ID: " + request.getIdUnidade() + " não encontrada"));
        return  unidadeSelecionada;
    }

}
