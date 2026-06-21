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

    @PostMapping("/categoria")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaDTO> salvaCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO){
        return ResponseEntity.ok(produtoService.salvaCategoria(categoriaDTO));
    }
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> exibirDashboard(){
        return ResponseEntity.ok(produtoService.exibirDashboard());
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<List<ProdutoResponseDTO>> exibirProdutoPorCategoria(@PathVariable
                                                                 String slug) {
        return ResponseEntity.ok(produtoService.exibirProdutoPorCategoria(slug));
    }

    @GetMapping("/categoria-all")
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodosProdutos() {
        return ResponseEntity.ok(produtoService.listarTodosOsProdutos());
    }

    @GetMapping("/id-{id}")
    public ResponseEntity<ProdutoResumoDTO> exibirProdutoPorId(@PathVariable Long id){

        return ResponseEntity.ok(produtoService.exibirProdutoPorId(id));
    }

    @GetMapping("/sku-{sku}")
    public ResponseEntity<ProdutoResumoDTO> exibirProdutoPorSku(@PathVariable String sku){
        return ResponseEntity.ok(produtoService.exibirProdutoPorSku(sku));
    }

    @GetMapping("/nome-{nome}")
    public ResponseEntity<ProdutoResumoDTO> exibirProdutoPorNome(@PathVariable String nome){
        return ResponseEntity.ok(produtoService.exibirProdutoPorNome(nome));
    }


    @DeleteMapping("/deletar/categoria")
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
    public ResponseEntity<ProdutoDTO> atualizaProduto(@PathVariable Long id,
                                                      @RequestBody ProdutoDTO produtoDTO){
        return ResponseEntity.ok(produtoService.atualizaProduto(id, produtoDTO));
    }
    @PutMapping("/atualizar/categoria/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaDTO> atualizaCategoria(@PathVariable Long id,
                                                          @RequestBody CategoriaDTO categoriaDTO
    ){
        return ResponseEntity.ok(produtoService.atualizaCategoria(id, categoriaDTO));
    }
}
