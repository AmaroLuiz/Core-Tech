package br.com.coretech.coretech_api.service;


import br.com.coretech.coretech_api.infraestructure.entity.Categoria;
import br.com.coretech.coretech_api.infraestructure.entity.Produto;
import br.com.coretech.coretech_api.infraestructure.exceptions.ResourceNotFoundException;
import br.com.coretech.coretech_api.infraestructure.repository.CategoriaRepository;
import br.com.coretech.coretech_api.infraestructure.repository.ProdutoRepository;
import br.com.coretech.coretech_api.service.dto.*;
import br.com.coretech.coretech_api.service.mapper.ProdutoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


        return produtoConverter.paraProdutoRequestDTO(produto);
    }



    public List<ProdutoResponseDTO> listaProdutoPorCategoria(Long categoria){

        verificaCategriaExistente(categoria);

        List<Produto> produtos = produtoRepository.findAllByCategoria_Id(categoria);
        //colocar proteção contra lista vazia
        List<ProdutoResponseDTO> response = new ArrayList<>();


        for (Produto produto : produtos) {
            response.add(produtoConverter.paraProdutoResponseDTO(produto));
        }

        if(produtos.isEmpty()){
            throw new ResourceNotFoundException("Não existe produtos cadastrados nesta categoria");
        }else{
            return response;
        }

        //isso abaixo é o mesmo que o codigo acima. tem que testar dps pra confirmar

//        return produtos.stream()
//                .map(produtoConverter::paraProdutoResponseDTO)
//                .toList();
    }

    public List<ProdutoResponseDTO> listarTodosOsProdutos(){
        return produtoRepository.findAll().stream()
                .map(produtoConverter::paraProdutoResponseDTO)
                .toList();
    }

    public ProdutoResumoDTO pegarProduto(Long id) {

        Produto produto = produtoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Produto não encontrado " + id)
        );

        return produtoConverter.paraProdutoResumoDTO(produto);
    }

    public void apagarCategoria(Long id){
        categoriaRepository.deleteById(id);
    }

    public void apagarProduto(Long id){
        produtoRepository.deleteById(id);
    }

    public ProdutoDTO atualizaProduto(Long id, ProdutoDTO produtoDTO){
        verificaProdutoExistente(produtoDTO.getSku());

        Produto produto = produtoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Produto não encontrado " + id)
        );

        Produto produtoAtualizado = produtoConverter.updateProduto(produtoDTO, produto );

        return produtoConverter.paraProdutoDTO(produtoRepository.save(produtoAtualizado));
    }

    public CategoriaDTO atualizaCategoria(Long id, CategoriaDTO categoriaDTO){
        verificaCategriaExistente(id);

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada " + id)
        );

        Categoria categoriaAtualizada = produtoConverter.updateCategoria(categoriaDTO, categoria);

        return produtoConverter.paraCategoriaDTO(categoriaRepository.save(categoriaAtualizada));
    }







    public boolean verificaCategriaExistente(Long id){
        try{
            boolean existe = categoriaRepository.existsById(id);
            if(existe){
                return categoriaRepository.existsById(id);

            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
