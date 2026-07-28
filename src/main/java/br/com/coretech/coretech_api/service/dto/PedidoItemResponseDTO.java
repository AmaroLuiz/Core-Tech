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
public class PedidoItemResponseDTO {

    private Long id;

    @NotNull(message = "O pedido é obrigatório.")
    private Long pedidoId;

    @NotNull(message = "O produto é obrigatório.")
    private Long produtoId;

    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    private Integer quantidade;

    @NotNull(message = "O valor total é obrigatório.")
    @DecimalMin(value = "0.00", inclusive = true,
            message = "O valor total não pode ser negativo.")
    private BigDecimal precoUnitario;

    @NotNull(message = "O valor total é obrigatório.")
    @DecimalMin(value = "0.00", inclusive = true,
            message = "O valor total não pode ser negativo.")
    private BigDecimal subTotal;


}
