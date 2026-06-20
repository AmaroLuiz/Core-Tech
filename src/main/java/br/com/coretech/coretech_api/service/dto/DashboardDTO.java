package br.com.coretech.coretech_api.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardDTO {
    @NotBlank(message = "Total de contas é obrigatório")
    private Long totalContas;
    @NotBlank(message = "Total de produtos é obrigatório")
    private Long totalProdutos;
    @NotBlank(message = "Total de categoria é obrigatório")
    private Long totalCategoria;

    private Long totalVisitas;
}
