package br.com.coretech.coretech_api.service;

import br.com.coretech.coretech_api.infraestructure.entity.Pedido;
import br.com.coretech.coretech_api.infraestructure.entity.PedidoItem;
import br.com.coretech.coretech_api.infraestructure.entity.Produto;
import br.com.coretech.coretech_api.infraestructure.exceptions.ConflictExceptions;
import br.com.coretech.coretech_api.infraestructure.exceptions.ResourceNotFoundException;
import br.com.coretech.coretech_api.infraestructure.repository.PedidoItemRepository;
import br.com.coretech.coretech_api.infraestructure.repository.PedidoRepository;
import br.com.coretech.coretech_api.infraestructure.repository.ProdutoRepository;
import br.com.coretech.coretech_api.service.dto.*;
import br.com.coretech.coretech_api.service.mapper.PedidoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor

public class PedidoService {

    private final PedidoConverter pedidoConverter;
    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final ProdutoRepository produtoRepository;

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

        pedido.setValorProduto(valorProduto);
        pedido.setValorFrete(BigDecimal.valueOf(10.00));
        pedido.setValorTotal(pedido.getValorProduto().add(pedido.getValorFrete()));

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return pedidoConverter.paraPedidoResponseDTO(pedidoSalvo);

    }

}
