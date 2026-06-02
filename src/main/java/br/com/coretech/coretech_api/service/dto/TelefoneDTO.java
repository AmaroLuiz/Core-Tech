package br.com.coretech.coretech_api.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneDTO {

    private Long id;
    private String ddd;
    private String numero;
    private String telefone;
    private String usuario_id;
}
