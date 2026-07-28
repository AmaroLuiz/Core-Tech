package br.com.coretech.coretech_api.service;

import br.com.coretech.coretech_api.infraestructure.repository.PedidoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class PedidoItemService {

    private final PedidoItemRepository pedidoItemRepository;
    private final ProdutoService produtoService;



}
