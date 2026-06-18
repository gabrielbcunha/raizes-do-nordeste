package br.com.gabrielbcunha.sistemaraizesdonordeste.mapper;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.promocao.PromocaoCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Promocao;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface PromocaoMapper {

	PromocaoCreateResponse toDto(Promocao promocao);
	
}