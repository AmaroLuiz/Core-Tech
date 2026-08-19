package br.com.coretech.coretech_api.controller;

import br.com.coretech.coretech_api.infraestructure.entity.Pedido;
import br.com.coretech.coretech_api.infraestructure.entity.PedidoItem;
import br.com.coretech.coretech_api.infraestructure.repository.PedidoRepository;
import br.com.coretech.coretech_api.service.PedidoService;
import br.com.coretech.coretech_api.service.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedido")
public class PedidoController {


    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;


    @PostMapping
    public ResponseEntity<PedidoResponseDTO> salvaPedido(@Valid @RequestBody PedidoRequestDTO pedidoRequestDTO) {
        return ResponseEntity.ok(pedidoService.salvaPedido(pedidoRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscaPedido(@PathVariable Long id){
        return ResponseEntity.ok(pedidoService.buscaPedido(id));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> buscaTodosPedidos(){
        return ResponseEntity.ok(pedidoService.buscarTodosPedidos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarPedido(@PathVariable Long id){
        pedidoService.apagarPedido(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> atualizaPedido(@PathVariable Long id,
                                                            @RequestBody PedidoRequestDTO pedidoRequestDTO){
        return ResponseEntity.ok(pedidoService.atualizaPedido(id, pedidoRequestDTO));
    }

    @PostMapping("/{id}/itens")
    public ResponseEntity<List<PedidoItemResponseDTO>> salvaPedidoItem(@PathVariable Long id
            ,@RequestBody List<PedidoItemRequestDTO> pedidoItemRequestDTO) {
       return ResponseEntity.ok(pedidoService.salvaPedidoItem(id, pedidoItemRequestDTO));
    }

    @GetMapping("/{pedidoId}/itens/{itemId}")
    public ResponseEntity<PedidoItemResponseDTO> buscaPedidoItem(@PathVariable Long pedidoId,
                                                                 @PathVariable Long itemId){
        return ResponseEntity.ok(pedidoService.buscarPedidoItem(pedidoId, itemId));
    }

    @DeleteMapping("/{pedidoId}/itens/{itemId}")
    public ResponseEntity<Void> apagarPedidoItem(@PathVariable Long pedidoId,
                                                 @PathVariable Long itemId){
        pedidoService.apagarPedidoItem(pedidoId, itemId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{pedidoId}/itens/{itemId}")
    public ResponseEntity<PedidoItemResponseDTO> atualizarPedidoItem(@PathVariable Long pedidoId,
                                                                     @PathVariable Long itemId,
                                                                     @RequestBody PedidoItemRequestDTO pedidoItemRequestDTO){
        return ResponseEntity.ok(pedidoService.atualizaPedidoItem(pedidoId, itemId, pedidoItemRequestDTO));
    }

}
