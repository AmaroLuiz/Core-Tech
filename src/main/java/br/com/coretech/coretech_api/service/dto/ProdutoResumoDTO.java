package br.com.coretech.coretech_api.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoResumoDTO {

    private Long id;

    @NotBlank(message = "Nome de produto é obrigatório")
    @NotNull
    @Size(min = 0, max = 100, message = "Nome deve ter entre 0 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Descrição de produto é obrigatória")
    @NotNull
    @Size(min = 0, max = 300, message = "Descrição deve ter entre 0 e 300 caracteres")
    private String descricao;

    @Positive
    @NotBlank(message = "Preço de produto é obrigatório")
    @NotNull
    private BigDecimal preco;

    @NotBlank(message = "Imagem de produto é obrigatório")
    @NotNull
    private String imagemUrl;

    @NotBlank(message = "Categoria de produto é obrigatório")
    @NotNull
    private String nomeCategoria;
}
