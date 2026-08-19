package br.com.coretech.coretech_api.controller;

import br.com.coretech.coretech_api.service.ProdutoService;
import br.com.coretech.coretech_api.service.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoRequestDTO> salvaProduto(@Valid @RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.ok(produtoService.salvaProduto(produtoDTO));
    }

    @PostMapping("/categorias")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaDTO> salvaCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO){
        return ResponseEntity.ok(produtoService.salvaCategoria(categoriaDTO));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> exibirDashboard(){
        return ResponseEntity.ok(produtoService.exibirDashboard());
    }

    @GetMapping("/categorias/{slug}/produtos")
    public ResponseEntity<List<ProdutoResponseDTO>> exibirProdutoPorCategoria(@PathVariable
                                                                 String slug) {
        return ResponseEntity.ok(produtoService.exibirProdutoPorCategoria(slug));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodosProdutos() {
        return ResponseEntity.ok(produtoService.listarTodosOsProdutos());
    }

    @GetMapping
    public ResponseEntity<ProdutoResumoDTO> exibirProdutoPorId(@RequestParam("id") Long id){
        return ResponseEntity.ok(produtoService.exibirProdutoPorId(id));
    }

    @GetMapping("/sku")
    public ResponseEntity<ProdutoResumoDTO> exibirProdutoPorSku(@RequestParam("sku") String sku){
        return ResponseEntity.ok(produtoService.exibirProdutoPorSku(sku));
    }

    @GetMapping("/nome")
    public ResponseEntity<ProdutoResumoDTO> exibirProdutoPorNome(@RequestParam("nome") String nome){
        return ResponseEntity.ok(produtoService.exibirProdutoPorNome(nome));
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDTO>> exibirTodasCategorias(){
        return ResponseEntity.ok(produtoService.exibirTodasCategorias());
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaDTO> exibirCategoriaPorId(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.exibirCategoriaPorId(id));
    }

    @DeleteMapping("/categoria")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarCategoria(@RequestParam("id") Long id){
        produtoService.apagarCategoria(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarProduto(@RequestParam("id") Long id){
        produtoService.apagarProduto(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoResponseDTO> atualizaProduto(@PathVariable Long id,
                                                      @RequestBody ProdutoDTO produtoDTO){
        return ResponseEntity.ok(produtoService.atualizaProduto(id, produtoDTO));
    }

    @PutMapping("/categoria/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaDTO> atualizaCategoria(@PathVariable Long id,
                                                          @RequestBody CategoriaDTO categoriaDTO
    ){
        return ResponseEntity.ok(produtoService.atualizaCategoria(id, categoriaDTO));
    }


}
