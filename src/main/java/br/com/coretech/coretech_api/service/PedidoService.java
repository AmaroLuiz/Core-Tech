package br.com.coretech.coretech_api.service;

import br.com.coretech.coretech_api.infraestructure.Enums.PagamentoStatus;
import br.com.coretech.coretech_api.infraestructure.entity.Pedido;
import br.com.coretech.coretech_api.infraestructure.entity.PedidoItem;
import br.com.coretech.coretech_api.infraestructure.entity.Produto;
import br.com.coretech.coretech_api.infraestructure.entity.Usuario;
import br.com.coretech.coretech_api.infraestructure.exceptions.ConflictExceptions;
import br.com.coretech.coretech_api.infraestructure.exceptions.ResourceNotFoundException;
import br.com.coretech.coretech_api.infraestructure.repository.PedidoItemRepository;
import br.com.coretech.coretech_api.infraestructure.repository.PedidoRepository;
import br.com.coretech.coretech_api.infraestructure.repository.ProdutoRepository;
import br.com.coretech.coretech_api.infraestructure.repository.UsuarioRepository;
import br.com.coretech.coretech_api.service.dto.*;
import br.com.coretech.coretech_api.service.mapper.PedidoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PedidoService {

    private final PedidoConverter pedidoConverter;
    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final ProdutoRepository produtoRepository;
    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public PedidoResponseDTO salvaPedido(PedidoRequestDTO pedidoRequestDTO){

        Pedido pedido = pedidoConverter.paraPedidoEntity(pedidoRequestDTO);

        for (PedidoItem item : pedido.getPedidoItems()) {

            item.setPedido(pedido);

            Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Produto não encontrado " + item.getProduto().getId()));

            item.setProduto(produto);

            item.recalcValores();
        }

        String email = authService.getUsuarioAutenticadoEmail();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Usuario não encontrado " + email)
        );

        pedido.setUsuario(usuario);

        pedido.recalcValores();

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return pedidoConverter.paraPedidoResponseDTO(pedidoSalvo);

    }

    @Transactional
    public List<PedidoItemResponseDTO> salvaPedidoItem(Long id,
                                                       List<PedidoItemRequestDTO> pedidoItemRequestDTO){

        String email = authService.getUsuarioAutenticadoEmail();

        Pedido pedido = pedidoRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Pedido não encontrado")
                );

        List<PedidoItem> novosItens = pedidoConverter.paraPedidoItemListEntity(pedidoItemRequestDTO);

        for (PedidoItem item : novosItens) {

            Produto produto = produtoRepository.findById(item.getProduto().getId()).orElseThrow(
                    () -> new ResourceNotFoundException("Produto não encontrado " + item.getProduto().getId())
            );

            item.setProduto(produto);

            item.recalcValores();

            pedido.adicionarItem(item);
        }

        pedido.recalcValores();

        Pedido pedidoSalvo = pedidoRepository.saveAndFlush(pedido);

        return pedidoConverter.paraPedidoItemListResponseDTO(pedidoSalvo.getPedidoItems());
    }

    public PedidoResponseDTO buscaPedido(Long id){

        String email = authService.getUsuarioAutenticadoEmail();

        Pedido pedido = pedidoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Pedido não encontrado " + id)
        );

        Usuario usuario = pedido.getUsuario();

//        if (pedido == null){
//            throw new ResourceNotFoundException("Usuario não possui pedidos " + email);
//        }

        if (!usuario.getEmail().equals(email)){
            throw new ConflictExceptions("Pedido não pertence ao usuario " + email);
        }


        return pedidoConverter.paraPedidoResponseDTO(pedido);
    }

    public PedidoItemResponseDTO buscarPedidoItem(Long id){

        String email = authService.getUsuarioAutenticadoEmail();

        PedidoItem pedidoItem = pedidoItemRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Item de pedido não encontrado")
        );

        if (!pedidoItem.getPedido().getUsuario().getEmail().equals(email)){
            throw new AccessDeniedException("Item de pedido não pertence ao usuario " + email);
        }

        return pedidoConverter.paraPedidoItemResponseDTO(pedidoItem);

    }

    public List<PedidoResponseDTO> buscarTodosPedidos(){

        String email = authService.getUsuarioAutenticadoEmail();

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Usuario não encontrado " + email)
        );

        List<Pedido> pedido = usuario.getPedidos();

        return pedidoConverter.paraPedidoListResponseDTO(pedido);
    }

    @Transactional
    public void apagarPedido(Long id){

        String email = authService.getUsuarioAutenticadoEmail();

        Pedido pedido = pedidoRepository.findByIdAndUsuarioEmail(id, email).orElseThrow(
                () -> new ResourceNotFoundException("Pedido não encontrado ou não pertence ao usuario")
        );

        pedidoRepository.delete(pedido);
    }

    @Transactional
    public void apagarPedidoItem(Long id){
        String email = authService.getUsuarioAutenticadoEmail();

        PedidoItem pedidoItem = pedidoItemRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Item de pedido não encontrado")
        );

        if (!pedidoItem.getPedido().getUsuario().getEmail().equals(email)){
            throw new AccessDeniedException("Item de pedido não pertence ao usuario " + email);
        }

        Pedido pedido = pedidoItem.getPedido();

        pedido.getPedidoItems().remove(pedidoItem);

        pedido.recalcValores();

        pedidoRepository.save(pedido);

    }

    @Transactional
    public PedidoResponseDTO atualizaPedido(Long id, PedidoRequestDTO pedidoRequestDTO){

        String email = authService.getUsuarioAutenticadoEmail();

        Pedido pedido = pedidoRepository.findByIdAndUsuarioEmail(id, email).orElseThrow(
                () -> new ResourceNotFoundException("Pedido não encontrado ou não pertence ao usuario")
        );

        Pedido pedidoAtualizado = pedidoConverter.updatePedido(pedidoRequestDTO, pedido);

        if (pedidoAtualizado.getStatus().equals(PagamentoStatus.PAGO)){
            pedidoAtualizado.setDataPagamento(LocalDateTime.now());
        }

        return pedidoConverter.paraPedidoResponseDTO(pedidoRepository.save(pedidoAtualizado));

    }

    @Transactional
    public PedidoItemResponseDTO atualizaPedidoItem(Long id,
                                                    PedidoItemRequestDTO pedidoItemRequestDTO){

        String email = authService.getUsuarioAutenticadoEmail();

        PedidoItem pedidoItem = pedidoItemRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Item de pedido não encontrado")
        );

        if (!pedidoItem.getPedido().getUsuario().getEmail().equals(email)){
            throw new AccessDeniedException("Item de pedido não pertence ao usuario " + email);
        }
        if (pedidoItemRequestDTO.getProdutoId() != null){
            Produto produto = produtoRepository.findById(pedidoItemRequestDTO.getProdutoId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Produto não encontrado " + pedidoItemRequestDTO.getProdutoId())
                    );
            pedidoItem.setProduto(produto);
        }
        if (pedidoItemRequestDTO.getQuantidade() != null){
            pedidoItem.setQuantidade(pedidoItemRequestDTO.getQuantidade());
        }

        pedidoItem.recalcValores();

        Pedido pedido = pedidoItem.getPedido();

        pedido.recalcValores();

        pedidoRepository.save(pedido);

        return pedidoConverter.paraPedidoItemResponseDTO(pedidoItem);

    }


}
