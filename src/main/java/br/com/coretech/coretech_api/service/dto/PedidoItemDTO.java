package br.com.coretech.coretech_api.service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoItemDTO {

    private Long id;

    @NotNull(message = "O pedido é obrigatório.")
    private PedidoDTO pedido;

    @NotNull(message = "O produto é obrigatório.")
    private ProdutoDTO produto;

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    private Integer quantidade;


    private BigDecimal precoUnitario;

    private BigDecimal subTotal;

}
