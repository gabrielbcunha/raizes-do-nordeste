package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.estoqueUnidade.EstoqueUnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.estoqueUnidade.EstoqueUnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.RecursoNaoEncontradoException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.EstoqueUnidadeMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.MenuUnidadeMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.EstoqueUnidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Item;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Unidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.EstoqueUnidadeRepository;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.ItemRepository;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

@Service
public class EstoqueUnidadeService {

    private final EstoqueUnidadeRepository estoqueUnidadeRepository;
    private final ItemRepository itemRepository;
    private final UnidadeRepository unidadeRepository;
    private final EstoqueUnidadeMapper estoqueUnidadeMapper;

    public EstoqueUnidadeService(EstoqueUnidadeRepository estoqueUnidadeRepository, ItemRepository itemRepository, UnidadeRepository unidadeRepository, EstoqueUnidadeMapper estoqueUnidadeMapper) {
        this.estoqueUnidadeRepository = estoqueUnidadeRepository;
        this.itemRepository = itemRepository;
        this.unidadeRepository = unidadeRepository;
        this.estoqueUnidadeMapper = estoqueUnidadeMapper;
    }

    public EstoqueUnidadeCreateResponse cadastrarItemEstoque (EstoqueUnidadeCreateRequest estoqueUnidadeCreateRequest) {

        Item itemBuscado = itemRepository.findById(estoqueUnidadeCreateRequest.getItemId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item de ID: " + estoqueUnidadeCreateRequest.getItemId() + " não encontrado"));
        Unidade unidadeBuscada = unidadeRepository.findById(estoqueUnidadeCreateRequest.getUnidadeId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade de ID: " + estoqueUnidadeCreateRequest.getUnidadeId() + " não encontrada"));

        EstoqueUnidade novoItemEstoque = estoqueUnidadeMapper.toEntity(estoqueUnidadeCreateRequest);
        novoItemEstoque.setUnidade(unidadeBuscada);
        novoItemEstoque.setItem(itemBuscado);

        EstoqueUnidade novoItemEstoqueSalvo = estoqueUnidadeRepository.save(novoItemEstoque);
        return estoqueUnidadeMapper.toDto(novoItemEstoqueSalvo);
    }


}