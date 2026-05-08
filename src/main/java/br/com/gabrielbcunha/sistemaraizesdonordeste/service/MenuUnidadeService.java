package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.menuUnidade.MenuUnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.menuUnidade.MenuUnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.exception.RecursoNaoEncontradoException;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.MenuUnidadeMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Item;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.MenuUnidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Unidade;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.ItemRepository;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.MenuUnidadeRepository;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.UnidadeRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuUnidadeService {

    private final MenuUnidadeRepository menuUnidadeRepository;
    private final ItemRepository itemRepository;
    private final UnidadeRepository unidadeRepository;
    private final MenuUnidadeMapper menuUnidadeMapper;

    public MenuUnidadeService(MenuUnidadeRepository menuUnidadeRepository, ItemRepository itemRepository, UnidadeRepository unidadeRepository, MenuUnidadeMapper menuUnidadeMapper) {
        this.menuUnidadeRepository = menuUnidadeRepository;
        this.itemRepository = itemRepository;
        this.unidadeRepository = unidadeRepository;
        this.menuUnidadeMapper = menuUnidadeMapper;
    }

    public MenuUnidadeCreateResponse cadastarItemMenu(MenuUnidadeCreateRequest menuUnidadeCreateRequest){

        Item itemBuscado = itemRepository.findById(menuUnidadeCreateRequest.getItemId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item de ID: " + menuUnidadeCreateRequest.getItemId() + " não encontrado"));
        Unidade unidadeBuscada = unidadeRepository.findById(menuUnidadeCreateRequest.getUnidadeId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Unidade de ID: " + menuUnidadeCreateRequest.getUnidadeId() + " não encontrada"));

        MenuUnidade novoItemMenu = menuUnidadeMapper.toEntity(menuUnidadeCreateRequest);
        novoItemMenu.setItem(itemBuscado);
        novoItemMenu.setUnidade(unidadeBuscada);

        MenuUnidade novoItemMenuSalvo = menuUnidadeRepository.save(novoItemMenu);
        return menuUnidadeMapper.toDto(novoItemMenuSalvo);
    }

}
