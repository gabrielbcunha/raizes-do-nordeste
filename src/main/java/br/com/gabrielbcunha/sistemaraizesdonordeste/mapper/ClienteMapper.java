package br.com.gabrielbcunha.sistemaraizesdonordeste.mapper;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth.CadastroClienteRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.auth.CadastroClienteResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ClienteMapper {

    Cliente toEntity(CadastroClienteRequest cadastroClienteRequest);

    CadastroClienteResponse toDto(Cliente cliente);

}
