package br.com.gabrielbcunha.sistemaraizesdonordeste.mapper;

import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.itemPedido.ItemPedidoCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido.PedidoCancelarResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido.PedidoCreateRequest;
import br.com.gabrielbcunha.sistemaraizesdonordeste.dto.pedido.PedidoCreateResponse;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.ItemPedido;
import br.com.gabrielbcunha.sistemaraizesdonordeste.model.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface PedidoMapper {

    @Mapping(target = "itens", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "valorTotal", ignore = true)
    @Mapping(target = "cliente.id", source = "clienteId")
    @Mapping(target = "unidade.id", source = "unidadeId")
    Pedido toEntity(PedidoCreateRequest request);

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "unidade.id", target = "unidadeId")
    PedidoCreateResponse toDto(Pedido pedido);

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "unidade.id", target = "unidadeId")
    PedidoCancelarResponse toDtoCancel(Pedido pedido);

    @Mapping(source = "item.id", target = "itemId")
    ItemPedidoCreateResponse itemPedidoToDto(ItemPedido itemPedido);

}
