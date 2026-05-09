package br.com.gabrielbcunha.sistemaraizesdonordeste.service;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.item.ItemCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.item.ItemCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.mapper.ItemMapper;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Item;
import br.com.gabrielbcunha.sistemaraizesdonordeste.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Transactional
    public ItemCreateResponse cadastrarItem(ItemCreateRequest itemCreateRequest) {
        Item novoItem =  itemMapper.toEntity(itemCreateRequest);
        Item itemSalvo = itemRepository.save(novoItem);
        return itemMapper.toDto(itemSalvo);
    }


}
