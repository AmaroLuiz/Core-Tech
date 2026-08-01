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


    @PostMapping("/criar-pedido")
    public ResponseEntity<PedidoResponseDTO> salvaPedido(@Valid @RequestBody PedidoRequestDTO pedidoRequestDTO) {
        return ResponseEntity.ok(pedidoService.salvaPedido(pedidoRequestDTO));
    }

    @GetMapping("/buscar-pedido-{id}")
    public ResponseEntity<PedidoResponseDTO> buscaPedido(@PathVariable Long id){
        return ResponseEntity.ok(pedidoService.buscaPedido(id));
    }

    @GetMapping("/buscar-todos-pedidos")
    public ResponseEntity<List<PedidoResponseDTO>> buscaTodosPedidos(){
        return ResponseEntity.ok(pedidoService.buscarTodosPedidos());
    }

    @DeleteMapping("/apagar-pedido")
    public ResponseEntity<Void> apagarPedido(@RequestParam("id") Long id){
        pedidoService.apagarPedido(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizar-pedido")
    public ResponseEntity<PedidoResponseDTO> atualizaPedido(@RequestParam("id") Long id,
                                                            @RequestBody PedidoRequestDTO pedidoRequestDTO){
        return ResponseEntity.ok(pedidoService.atualizaPedido(id, pedidoRequestDTO));
    }

    @PostMapping("/criar-pedido-item")
    public ResponseEntity<List<PedidoItemResponseDTO>> salvaPedidoItem(@RequestParam("id") Long id
            ,@RequestBody List<PedidoItemRequestDTO> pedidoItemRequestDTO) {
       return ResponseEntity.ok(pedidoService.salvaPedidoItem(id, pedidoItemRequestDTO));
    }

    @GetMapping("/buscar-pedido-item")
    public ResponseEntity<PedidoItemResponseDTO> buscaPedidoItem(@RequestParam("id") Long id){
        return ResponseEntity.ok(pedidoService.buscarPedidoItem(id));
    }

    @DeleteMapping("/apagar-pedido-item")
    public ResponseEntity<Void> apagarPedidoItem(@RequestParam("id") Long id){
        pedidoService.apagarPedidoItem(id);
        return ResponseEntity.ok().build();
    }

}
