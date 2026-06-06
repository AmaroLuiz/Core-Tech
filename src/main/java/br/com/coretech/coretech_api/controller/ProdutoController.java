package br.com.coretech.coretech_api.controller;

import br.com.coretech.coretech_api.infraestructure.entity.Produto;
import br.com.coretech.coretech_api.service.ProdutoService;
import br.com.coretech.coretech_api.service.dto.ProdutoDTO;
import br.com.coretech.coretech_api.service.dto.ProdutoRequestDTO;
import br.com.coretech.coretech_api.service.dto.ProdutoResponseDTO;
import br.com.coretech.coretech_api.service.dto.ProdutoResumoDTO;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoRequestDTO> salvaProduto(@Valid @RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.ok(produtoService.salvaProduto(produtoDTO));
    }

    @GetMapping("/categoria-{categoria}")
    public ResponseEntity<List<ProdutoResponseDTO>> listaProduto(@PathParam("categoria")
                                                                 Long categoria) {
        return ResponseEntity.ok(produtoService.listaProdutoPorCategoria(categoria));
    }

    @GetMapping("/categoria-all")
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodosProdutos() {
        return ResponseEntity.ok(produtoService.listarTodosOsProdutos());
    }

    @GetMapping("/produto-{id}")
    public ResponseEntity<ProdutoResumoDTO> pegarProduto(@PathParam("id") Long id){

        return ResponseEntity.ok(produtoService.pegarProduto(id));
    }
    @DeleteMapping("/deletar-categoria-{id}")
    public ResponseEntity<Void> deletarCategoria(@RequestParam("id") Long id){
        produtoService.apagarCategoria(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/deletar-produto-{id}")
    public ResponseEntity<Void> deletarProduto(@RequestParam("id") Long id){
        produtoService.apagarProduto(id);
        return ResponseEntity.ok().build();
    }


}
