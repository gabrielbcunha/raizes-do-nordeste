package br.com.gabrielbcunha.sistemaraizesdonordeste.mapper;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.estoqueUnidade.EstoqueUnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.estoqueUnidade.EstoqueUnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.EstoqueUnidade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface EstoqueUnidadeMapper {

    EstoqueUnidade toEntity(EstoqueUnidadeCreateRequest estoqueUnidadeCreateRequest);

    @Mapping(source = "unidade.id", target = "unidadeId")
    @Mapping(source = "item.id", target = "itemId")
    EstoqueUnidadeCreateResponse toDto(EstoqueUnidade estoqueUnidade);

}