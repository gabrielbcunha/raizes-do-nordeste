package br.com.gabrielbcunha.sistemaraizesdonordeste.mapper;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario.FuncionarioCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.funcionario.FuncionarioCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Funcionario;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface FuncionarioMapper {

    Funcionario toEntity(FuncionarioCreateRequest funcionarioCreateRequest);

    FuncionarioCreateResponse toDto(Funcionario funcionario);

}
