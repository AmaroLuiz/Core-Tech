package br.com.coretech.coretech_api.service.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @NotNull
    private String email;

    @NotBlank(message = "Senha é obrigatório")
    @NotNull
    @Size(min = 6, max = 100,
            message = "Senha deve ter entre 6 e 100 caracteres")
    private String senha;
}
