package br.com.coretech.coretech_api.service;


import br.com.coretech.coretech_api.infraestructure.entity.Categoria;
import br.com.coretech.coretech_api.infraestructure.entity.Produto;
import br.com.coretech.coretech_api.infraestructure.exceptions.ResourceNotFoundException;
import br.com.coretech.coretech_api.infraestructure.repository.CategoriaRepository;
import br.com.coretech.coretech_api.infraestructure.repository.ProdutoRepository;
import br.com.coretech.coretech_api.service.dto.ProdutoDTO;
import br.com.coretech.coretech_api.service.dto.ProdutoRequestDTO;
import br.com.coretech.coretech_api.service.mapper.ProdutoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class ProdutoService {

    private final ProdutoConverter produtoConverter;
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final AuthService authService;

    public ProdutoRequestDTO salvaProduto(ProdutoDTO produtoDTO){
        verificaProdutoExistente(produtoDTO.getSku());

        Produto produto = produtoConverter.paraProdutoEntity(produtoDTO);

        if (produto.getCategoria() != null && produto.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(produto.getCategoria().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
            produto.setCategoria(categoria);
        }
        produtoRepository.save(produto);


        return produtoConverter.paraProdutoResponseDTO(produto);
    }

    public boolean verificaProdutoExistente(String sku){
        try{
            boolean existe = produtoRepository.existsBySku(sku);
            if(existe){
                return produtoRepository.existsBySku(sku);

            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
