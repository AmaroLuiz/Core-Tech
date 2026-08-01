package br.com.coretech.coretech_api.service.mapper;

import br.com.coretech.coretech_api.infraestructure.entity.Pedido;
import br.com.coretech.coretech_api.infraestructure.entity.PedidoItem;
import br.com.coretech.coretech_api.service.dto.*;
import org.mapstruct.*;

import java.util.List;

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

//    @Mapping(source = "usuario.id", target = "usuarioId")
    List<PedidoResponseDTO> paraPedidoListResponseDTO(List<Pedido> pedido);

    @Mapping(source = "usuario.id", target = "usuarioId")
    PedidoRequestDTO paraPedidoRequestDTO(Pedido pedido);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "valorProduto", ignore = true)
    @Mapping(target = "valorFrete", ignore = true)
    @Mapping(target = "valorTotal", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "pedidoItems", ignore = true)
    Pedido updatePedido(PedidoRequestDTO dto, @MappingTarget Pedido entity);


    @Mapping(source = "produtoId", target = "produto.id")
    PedidoItem paraPedidoItemEntity(PedidoItemRequestDTO pedidoItemRequestDTO);

//    @Mapping(source = "produtoId", target = "produto.id")
    List<PedidoItem> paraPedidoItemListEntity(List<PedidoItemRequestDTO> pedidoItemRequestDTO);

    PedidoItemDTO paraPedidoItemDTO(PedidoItem pedidoItem);

    @Mapping(source = "pedido.id", target = "pedidoId")
    @Mapping(source = "produto.id", target = "produtoId")
    PedidoItemResponseDTO paraPedidoItemResponseDTO(PedidoItem pedidoItem);

    @Mapping(source = "pedido.id", target = "pedidoId")
    @Mapping(source = "produto.id", target = "produtoId")
    List<PedidoItemResponseDTO> paraPedidoItemListResponseDTO(List<PedidoItem >pedidoItem);

    @Mapping(source = "pedido.id", target = "pedidoId")
    @Mapping(source = "produto.id", target = "produtoId")
    PedidoItemRequestDTO paraPedidoItemRequestDTO(PedidoItem pedidoItem);
}
