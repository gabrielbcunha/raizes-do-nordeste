package br.com.gabrielbcunha.sistemaraizesdonordeste.mapper;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.menuUnidade.MenuUnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.menuUnidade.MenuUnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.MenuUnidade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface MenuUnidadeMapper {

    MenuUnidade toEntity(MenuUnidadeCreateRequest menuUnidadeCreateRequest);

    @Mapping(source = "unidade.id", target = "unidadeId")
    @Mapping(source = "item.id", target = "itemId")
    MenuUnidadeCreateResponse toDto(MenuUnidade menuUnidade);

}
