package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.unidade.UnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.unidade.UnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.UnidadeMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Unidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

@Service
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;
    private final UnidadeMapper unidadeMapper;

    public UnidadeService(UnidadeRepository unidadeRepository, UnidadeMapper unidadeMapper) {
        this.unidadeRepository = unidadeRepository;
        this.unidadeMapper = unidadeMapper;
    }

    public UnidadeCreateResponse cadastrarUnidade(UnidadeCreateRequest unidadeCreateRequest) {
        Unidade novaUnidade = unidadeMapper.toEntity(unidadeCreateRequest);
        Unidade unidadeSalva = unidadeRepository.save(novaUnidade);
        return unidadeMapper.toDto(unidadeSalva);
    }

}
