package br.com.coretech.coretech_api.infraestructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table( name = "telefone")
@Entity

public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telefone", length = 10, nullable = false)
    private String telefone;

    @Column(name = "ddd", length = 3, nullable = false)
    private String ddd;

    @Column(name = "usuario_id")
    private Long usuario;

}
