package br.com.coretech.coretech_api.service.dto;

import br.com.coretech.coretech_api.infraestructure.Enums.FormaPagamento;
import br.com.coretech.coretech_api.infraestructure.Enums.PagamentoStatus;
import br.com.coretech.coretech_api.infraestructure.entity.Usuario;
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
public class PedidoDTO {

    private Long id;

    @NotNull(message = "O usuário é obrigatório.")
    private UsuarioDTO usuario;

    @NotNull(message = "O status é obrigatório.")
    private PagamentoStatus status;

    @NotNull(message = "O valor dos produtos é obrigatório.")
    @DecimalMin(value = "0.00", inclusive = true,
            message = "O valor dos produtos não pode ser negativo.")
    private BigDecimal valorProduto;

    @NotNull(message = "O valor do frete é obrigatório.")
    @DecimalMin(value = "0.00", inclusive = true,
            message = "O valor do frete não pode ser negativo.")
    private BigDecimal valorFrete;

    @NotNull(message = "O valor total é obrigatório.")
    @DecimalMin(value = "0.00", inclusive = true,
            message = "O valor total não pode ser negativo.")
    private BigDecimal valorTotal;

    @NotNull(message = "A forma de pagamento é obrigatória.")
    private FormaPagamento formaPagamento;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataPagamento;

    @Valid
    @NotEmpty(message = "O pedido deve conter pelo menos um item.")
    private List<PedidoItemRequestDTO> pedidoItems;

}
