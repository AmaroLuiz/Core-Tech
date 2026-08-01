package br.com.coretech.coretech_api.service.mapper;

import br.com.coretech.coretech_api.infraestructure.entity.Pedido;
import br.com.coretech.coretech_api.infraestructure.entity.PedidoItem;
import br.com.coretech.coretech_api.service.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoConverter {


    PedidoDTO paraPedidoDTO(Pedido pedido);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "usuarioId", target = "usuario.id")
    Pedido paraPedidoEntity(PedidoRequestDTO pedidoRequestDTO);

    Pedido paraPedidoEntity(PedidoDTO pedidoDTO);

    @Mapping(source = "usuario.id", target = "usuarioId")
    PedidoResponseDTO paraPedidoResponseDTO(Pedido pedido);

    @Mapping(source = "usuario.id", target = "usuarioId")
    PedidoRequestDTO paraPedidoRequestDTO(Pedido pedido);



    @Mapping(source = "produtoId", target = "produto.id")
    PedidoItem paraPedidoItemEntity(PedidoItemRequestDTO pedidoItemRequestDTO);


    PedidoItemDTO paraPedidoItemDTO(PedidoItem pedidoItem);

    @Mapping(source = "pedido.id", target = "pedidoId")
    @Mapping(source = "produto.id", target = "produtoId")
    PedidoItemResponseDTO paraPedidoItemResponseDTO(PedidoItem pedidoItem);

    @Mapping(source = "pedido.id", target = "pedidoId")
    @Mapping(source = "produto.id", target = "produtoId")
    PedidoItemRequestDTO paraPedidoItemRequestDTO(PedidoItem pedidoItem);
}
