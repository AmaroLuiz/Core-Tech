package br.com.coretech.coretech_api.infraestructure.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table( name = "endereco")
@Entity

public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado", length = 2,  nullable = false)
    private String estado;

    @Column(name = "cidade", length = 100,  nullable = false)
    private String cidade;

    @Column(name = "cep", length = 8,   nullable = false)

    private String cep;

    @Column(name = "rua", length = 100,  nullable = false)
    private String rua;

    @Column(name = "numero",  length = 10,    nullable = false)
    private Integer numero;

    @Column(name = "complemento",  length = 50,   nullable = false)
    private String complemento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id",  nullable = false)
    private Usuario usuario;





}
