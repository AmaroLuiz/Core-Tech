package br.com.coretech.coretech_api.service.mapper;

import br.com.coretech.coretech_api.infraestructure.entity.Pedido;
import br.com.coretech.coretech_api.service.dto.PedidoDTO;
import br.com.coretech.coretech_api.service.dto.PedidoRequestDTO;
import br.com.coretech.coretech_api.service.dto.PedidoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoConverter {


    PedidoDTO paraPedidoDTO(Pedido pedido);

    @Mapping(source = "id", target = "id")
    Pedido paraPedidoEntity(PedidoRequestDTO pedidoRequestDTO);

    @Mapping(source = "usuario.id", target = "usuarioId")
    PedidoResponseDTO paraPedidoResponseDTO(Pedido pedido);

    @Mapping(source = "usuarioId", target = "usuario.id")
    PedidoRequestDTO paraPedidoRequestDTO(Pedido pedido);

}
