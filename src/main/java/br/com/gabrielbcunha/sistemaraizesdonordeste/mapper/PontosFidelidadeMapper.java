package br.com.gabrielbcunha.sistemaraizesdonordeste.mapper;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pontosFidelidade.PontosFidelidadeGetResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.PontosFidelidade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface PontosFidelidadeMapper {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "pedido.id", target = "pedidoId")
    PontosFidelidadeGetResponse toDto(PontosFidelidade pontosFidelidade);

}