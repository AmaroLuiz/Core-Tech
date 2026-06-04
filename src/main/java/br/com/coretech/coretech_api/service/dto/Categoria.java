package br.com.coretech.coretech_api.service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Categoria {

    private Long id;

    @NotBlank(message = "Nome de produto é obrigatório")
    @NotNull
    @UniqueElements
    @Size(min = 0, max = 100, message = "Nome deve ter entre 0 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Slug de produto é obrigatório")
    @NotNull
    private String slug;
}
