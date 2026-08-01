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

        BigDecimal valorProduto = BigDecimal.ZERO;

        for (PedidoItem item : pedido.getPedidoItems()) {

            item.setPedido(pedido);

            Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Produto não encontrado " + item.getProduto().getId()));

            item.setPrecoUnitario(produto.getPreco());

            item.setSubTotal(
                    produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()))
            );

            valorProduto = valorProduto.add(item.getSubTotal());
        }

        String email = authService.getUsuarioAutenticadoEmail();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Usuario não encontrado " + email)
        );

        pedido.setUsuario(usuario);

        pedido.setValorProduto(valorProduto);
        pedido.setValorFrete(BigDecimal.valueOf(10.00));
        pedido.setValorTotal(pedido.getValorProduto().add(pedido.getValorFrete()));

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return pedidoConverter.paraPedidoResponseDTO(pedidoSalvo);

    }

    public PedidoResponseDTO buscaPedido(Long id){

        String email = authService.getUsuarioAutenticadoEmail();

        Pedido pedido = pedidoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Pedido não encontrado " + id)
        );

        Usuario usuario = pedido.getUsuario();

        if (!usuario.getEmail().equals(email)){
            throw new ConflictExceptions("Pedido não pertence ao usuario " + email);
        }

//        if (pedido == null){
//            throw new ResourceNotFoundException("Usuario não possui pedidos " + email);
//        }

        return pedidoConverter.paraPedidoResponseDTO(pedido);
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
}
