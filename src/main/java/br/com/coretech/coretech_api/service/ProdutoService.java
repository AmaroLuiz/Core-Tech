package br.com.coretech.coretech_api.service;


import br.com.coretech.coretech_api.infraestructure.entity.Categoria;
import br.com.coretech.coretech_api.infraestructure.entity.Dashboard;
import br.com.coretech.coretech_api.infraestructure.entity.Produto;
import br.com.coretech.coretech_api.infraestructure.exceptions.ConflictExceptions;
import br.com.coretech.coretech_api.infraestructure.exceptions.ResourceNotFoundException;
import br.com.coretech.coretech_api.infraestructure.repository.CategoriaRepository;
import br.com.coretech.coretech_api.infraestructure.repository.DashboardRepository;
import br.com.coretech.coretech_api.infraestructure.repository.ProdutoRepository;
import br.com.coretech.coretech_api.infraestructure.repository.UsuarioRepository;
import br.com.coretech.coretech_api.service.dto.*;
import br.com.coretech.coretech_api.service.mapper.ProdutoConverter;
import jakarta.transaction.Transactional;
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
    private final UsuarioRepository usuarioRepository;
    private final DashboardRepository dashboardRepository;

    @Transactional
    public ProdutoRequestDTO salvaProduto(ProdutoDTO produtoDTO){
        verificaProdutoExistente(produtoDTO.getSku());

        Produto produto = produtoConverter.paraProdutoEntity(produtoDTO);

        if (produto.getCategoria() != null && produto.getCategoria().getSlug() != null) {
            Categoria categoria = categoriaRepository.findBySlug(produto.getCategoria().getSlug())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
            produto.setCategoria(categoria);
        }

        produtoRepository.save(produto);

        return produtoConverter.paraProdutoRequestDTO(produto);
    }

    @Transactional
    public CategoriaDTO salvaCategoria(CategoriaDTO categoriaDTO){

        validarDuplicidadeCategoria(categoriaDTO.getSlug());

        Categoria categoria = produtoConverter.paraCategoriaEntity(categoriaDTO);

        categoriaRepository.save(categoria);

        return produtoConverter.paraCategoriaDTO(categoria);
    }

    public DashboardDTO exibirDashboard(){
        return DashboardDTO.builder()
                .totalProdutos(produtoRepository.count())
                .totalContas(usuarioRepository.count())
                .totalCategoria(categoriaRepository.count())
                .totalVisitas(1349L)
                .build();
    }
    public List<ProdutoResponseDTO> exibirProdutoPorCategoria(String slug){

        if (!categoriaRepository.existsBySlug(slug)) {
            throw new ResourceNotFoundException("Categoria não encontrada com o ID: " + slug);
        }

        List<Produto> produtos = produtoRepository.findAllByCategoria_Slug(slug);
        List<ProdutoResponseDTO> response = new ArrayList<>();


        for (Produto produto : produtos) {
            response.add(produtoConverter.paraProdutoResponseDTO(produto));
        }

        if(produtos.isEmpty()){
            throw new ResourceNotFoundException("Não existe produtos cadastrados nesta categoria");
        }else{
            return response;
        }

    }

    public List<ProdutoResponseDTO> listarTodosOsProdutos(){
        return produtoRepository.findAll().stream()
                .map(produtoConverter::paraProdutoResponseDTO)
                .toList();
    }

    public ProdutoResumoDTO exibirProdutoPorId(Long id) {

        Produto produto = produtoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Produto não encontrado " + id)
        );

        return produtoConverter.paraProdutoResumoDTO(produto);
    }

    public ProdutoResumoDTO exibirProdutoPorSku(String sku) {

        Produto produto = produtoRepository.findBySku(sku).orElseThrow(
                () -> new ResourceNotFoundException("Produto não encontrado " + sku)
        );

        return produtoConverter.paraProdutoResumoDTO(produto);
    }

    public ProdutoResumoDTO exibirProdutoPorNome(String nome) {

        Produto produto = produtoRepository.findByNome(nome).orElseThrow(
                () -> new ResourceNotFoundException("Produto não encontrado " + nome)
        );

        return produtoConverter.paraProdutoResumoDTO(produto);
    }

    public List<CategoriaDTO> exibirTodasCategorias(){

        return categoriaRepository.findAll().stream()
                .map(produtoConverter::paraCategoriaDTO)
                .toList();

    }

    @Transactional
    public void apagarCategoria(Long id){
        categoriaRepository.deleteById(id);
    }

    @Transactional
    public void apagarProduto(Long id){
        produtoRepository.deleteById(id);
    }

    @Transactional
    public ProdutoDTO atualizaProduto(Long id, ProdutoDTO produtoDTO){

        Produto produto = produtoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Produto não encontrado " + id)
        );

        Produto produtoAtualizado = produtoConverter.updateProduto(produtoDTO, produto );

        if(produtoDTO.getSku() == produtoAtualizado.getSku()){
            verificaProdutoExistente(produtoAtualizado.getSku());
        }

        return produtoConverter.paraProdutoDTO(produtoRepository.save(produtoAtualizado));
    }

    @Transactional
    public CategoriaDTO atualizaCategoria(Long id, CategoriaDTO categoriaDTO) {


        Categoria categoria = categoriaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada " + id)
        );

        Categoria categoriaAtualizada = produtoConverter.updateCategoria(categoriaDTO, categoria);

        if(categoriaDTO.getSlug() == categoriaAtualizada.getSlug()){
            validarDuplicidadeCategoria(categoriaAtualizada.getSlug());
        }

        return produtoConverter.paraCategoriaDTO(categoriaRepository.save(categoriaAtualizada));

    }

    public boolean verificaCategriaExistenteId(Long id){
        if(categoriaRepository.existsById(id)){
            throw new ConflictExceptions("Categoria já existe " + id );
        }
        return false;

    }

    public boolean validarDuplicidadeCategoria(String slug){
        if (categoriaRepository.existsBySlug(slug)){
            throw new ConflictExceptions("Categoria já existe " + slug );
        }
        return false;

    }

    public boolean verificaProdutoExistente(String sku){
        if(produtoRepository.existsBySku(sku)){
            throw new ConflictExceptions("produto já existe " + sku );
        }
        return false;
    }

    public boolean encontrarProduto(String sku){
        if(!produtoRepository.existsBySku(sku)){
            throw new ResourceNotFoundException("Produto não encontrado");
        }
        return true;
    }
}
