package br.com.coretech.coretech_api.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100, unique = true)
    private String nome;

    @Column(name = "slug", nullable = false)
    private String slug;


    @OneToMany(fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Produto> produto;
}
