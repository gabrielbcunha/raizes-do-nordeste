package br.com.gabrielbcunha.sistemaraizesdonordeste.mapper;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.cliente.ClienteCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ClienteMapper {

    Cliente toEntity(ClienteCreateRequest cadastroClienteRequest);

    @Mapping(source="usuario.username", target="email")
    ClienteCreateResponse toDto(Cliente cliente);

}
