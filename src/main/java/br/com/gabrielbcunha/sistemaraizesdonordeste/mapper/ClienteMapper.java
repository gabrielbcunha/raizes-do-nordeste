package br.com.gabrielbcunha.sistemaraizesdonordeste.mapper;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth.CadastroClienteRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth.CadastroClienteResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ClienteMapper {

    Cliente toEntity(CadastroClienteRequest cadastroClienteRequest);

    @Mapping(source="usuario.username", target="email")
    CadastroClienteResponse toDto(Cliente cliente);

}
