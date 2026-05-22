package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.unidade.UnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.unidade.UnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.UnidadeMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Unidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.UnidadeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;
    private final UnidadeMapper unidadeMapper;

    public UnidadeService(UnidadeRepository unidadeRepository, UnidadeMapper unidadeMapper) {
        this.unidadeRepository = unidadeRepository;
        this.unidadeMapper = unidadeMapper;
    }

    @Transactional
    public UnidadeCreateResponse cadastrarUnidade(UnidadeCreateRequest unidadeCreateRequest) {
        Unidade novaUnidade = unidadeMapper.toEntity(unidadeCreateRequest);
        Unidade unidadeSalva = unidadeRepository.save(novaUnidade);
        return unidadeMapper.toDto(unidadeSalva);
    }

    public Page<UnidadeCreateResponse> listarUnidades(Pageable pageable) {
        Page<Unidade> paginaUnidades = unidadeRepository.findAll(pageable);
        return paginaUnidades.map(unidadeMapper::toDto);
    }

}
