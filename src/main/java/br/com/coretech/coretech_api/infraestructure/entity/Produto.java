package br.com.coretech.coretech_api.infraestructure.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "descricao", nullable = false, length = 300)
    private String descricao;

    @Column(name = "preco", nullable = false)
    private BigDecimal preco;

    @Column(name = "imagemURL", nullable = false)
    private String imagemURL;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "CriadoEm")
    private LocalDateTime criadoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", referencedColumnName = "id")
    private List<Categoria> categorias;

    @PrePersist
    private void prePersist() {
        this.criadoEm = LocalDateTime.now();
        this.ativo = true;
    }
}
