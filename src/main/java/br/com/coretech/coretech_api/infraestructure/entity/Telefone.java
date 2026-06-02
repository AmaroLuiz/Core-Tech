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

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "ddd")
    private String ddd;

    @JoinColumn(name = "usuario_id")
    private Long usuario_id;

}
