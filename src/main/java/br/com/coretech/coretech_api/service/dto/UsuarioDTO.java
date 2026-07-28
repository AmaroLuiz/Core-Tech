package br.com.coretech.coretech_api.service.dto;


import br.com.coretech.coretech_api.infraestructure.Enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @NotNull
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @NotNull
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Senha é obrigatório")
    @NotNull
    @Size(min = 6, max = 100,
            message = "Senha deve ter entre 6 e 100 caracteres")
    private String senha;

    private Role role;

    private List<EnderecoDTO> enderecos;
    private List<TelefoneDTO> telefones;

}
