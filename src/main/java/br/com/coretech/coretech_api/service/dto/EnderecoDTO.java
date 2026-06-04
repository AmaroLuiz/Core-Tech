package br.com.coretech.coretech_api.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTO {

    private Long id;
    private String estado;
    private String cidade;
    private String cep;
    private String rua;
    private String numero;
    private String complemento;
}
