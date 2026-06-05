package br.com.coretech.coretech_api.controller;

import br.com.coretech.coretech_api.infraestructure.entity.Produto;
import br.com.coretech.coretech_api.service.ProdutoService;
import br.com.coretech.coretech_api.service.dto.ProdutoDTO;
import br.com.coretech.coretech_api.service.dto.ProdutoRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoRequestDTO> salvaProduto(@Valid @RequestBody ProdutoDTO produtoDTO){
        return ResponseEntity.ok(produtoService.salvaProduto(produtoDTO));
    }
}
