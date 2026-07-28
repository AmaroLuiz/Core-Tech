package br.com.coretech.coretech_api.service.mapper;

import br.com.coretech.coretech_api.infraestructure.entity.Pedido;
import br.com.coretech.coretech_api.infraestructure.entity.PedidoItem;
import br.com.coretech.coretech_api.service.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PedidoItemConverter {


    PedidoItem paraPedidoEntity(PedidoItem pedidoItem);

    PedidoItemDTO paraPedidoItemDTO(PedidoItem pedidoItem);

    @Mapping(source = "pedido.id", target = "pedidoId")
    @Mapping(source = "produto.id", target = "produtoId")
    PedidoItemResponseDTO paraPedidoItemResponseDTO(PedidoItem pedidoItem);

    @Mapping(source = "pedido.id", target = "pedidoId")
    @Mapping(source = "produto.id", target = "produtoId")
    PedidoItemRequestDTO paraPedidoItemRequestDTO(PedidoItem pedidoItem);
}
