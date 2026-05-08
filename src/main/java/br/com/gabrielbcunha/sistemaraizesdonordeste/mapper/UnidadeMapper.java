package br.com.gabrielbcunha.sistemaraizesdonordeste.mapper;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.unidade.UnidadeCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.unidade.UnidadeCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Unidade;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface UnidadeMapper {

    Unidade toEntity(UnidadeCreateRequest unidadeCreateRequest);

    UnidadeCreateResponse toDto(Unidade unidade);

}
