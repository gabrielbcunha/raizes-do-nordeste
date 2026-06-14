package br.com.gabrielbcunha.sistemaraizesdonordeste.mapper;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pontosFidelidade.PontosFidelidadeGetResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.PontosFidelidade;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface PontosFidelidadeMapper {

    PontosFidelidadeGetResponse toDto(PontosFidelidade pontosFidelidade);

}
