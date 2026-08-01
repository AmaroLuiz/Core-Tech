package br.com.coretech.coretech_api.service.dto;

import br.com.coretech.coretech_api.infraestructure.Enums.FormaPagamento;
import br.com.coretech.coretech_api.infraestructure.Enums.PagamentoStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoRequestDTO {

    private Long id;


    private Long usuarioId;

    @NotNull(message = "O status é obrigatório.")
    private PagamentoStatus status;


    private BigDecimal valorProduto;


    private BigDecimal valorFrete;


    private BigDecimal valorTotal;

    @NotNull(message = "A forma de pagamento é obrigatória.")
    private FormaPagamento formaPagamento;

    @Valid
    @NotEmpty(message = "O pedido deve conter pelo menos um item.")
    private List<PedidoItemRequestDTO> pedidoItems;
}
