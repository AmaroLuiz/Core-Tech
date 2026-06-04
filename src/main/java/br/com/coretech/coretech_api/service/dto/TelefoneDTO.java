package br.com.coretech.coretech_api.service.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneDTO {

    private Long id;

    @Pattern(
            regexp = "\\d{2}",
            message = "DDD deve conter 2 dígitos"
    )
    private String ddd;

    @Pattern(
            regexp = "\\d{8,9}",
            message = "Telefone inválido"
    )
    private String telefone;
}
