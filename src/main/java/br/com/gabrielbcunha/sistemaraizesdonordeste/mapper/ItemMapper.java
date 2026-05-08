package br.com.gabrielbcunha.sistemaraizesdonordeste.mapper;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.item.ItemCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.item.ItemCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ItemMapper {

    Item toEntity(ItemCreateRequest itemCreateRequest);

    ItemCreateResponse toDto(Item item);

}
