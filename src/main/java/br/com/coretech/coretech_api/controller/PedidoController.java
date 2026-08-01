package br.com.coretech.coretech_api.controller;

import br.com.coretech.coretech_api.infraestructure.entity.Pedido;
import br.com.coretech.coretech_api.infraestructure.entity.PedidoItem;
import br.com.coretech.coretech_api.infraestructure.repository.PedidoRepository;
import br.com.coretech.coretech_api.service.PedidoService;
import br.com.coretech.coretech_api.service.dto.PedidoDTO;
import br.com.coretech.coretech_api.service.dto.PedidoRequestDTO;
import br.com.coretech.coretech_api.service.dto.PedidoResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedido")
public class PedidoController {


    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;


    @PostMapping("/criar-pedido")
    public ResponseEntity<PedidoResponseDTO> salvaPedido(@Valid @RequestBody PedidoRequestDTO pedidoRequestDTO) {
        return ResponseEntity.ok(pedidoService.salvaPedido(pedidoRequestDTO));
    }

    //    @PostMapping("/criar-pedido-item")
//    public ResponseEntity<PedidoItemResponseDTO> salvaPedidoItem(@RequestBody PedidoItemRequestDTO pedidoItemRequestDTO) {
//       return ResponseEntity.ok(pedidoItemService.salvaPedidoItem(pedidoItemRequestDTO));
//    }
}
