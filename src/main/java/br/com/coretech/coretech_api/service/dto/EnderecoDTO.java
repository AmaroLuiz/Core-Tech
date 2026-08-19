package br.com.coretech.coretech_api.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTO {

    private Long id;

    @NotBlank(message = "Estado é obrigatório")
    @Pattern(
            regexp = "[A-Za-z]{2}",
            message = "Estado deve conter apenas 2 characters"
    )
    private String estado;

    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(
            regexp = "\\d{8}",
            message = "Cep inválido"
    )
    private String cep;

    @NotBlank(message = "Rua é obrigatória")
    private String rua;

    @Positive(message = "Número deve ser maior que zero")
    private Integer numero;

    private String complemento;
}
