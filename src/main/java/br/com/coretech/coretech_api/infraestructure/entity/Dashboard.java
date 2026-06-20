package br.com.coretech.coretech_api.infraestructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dashboard")
public class Dashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_contas", nullable = false)
    private Long totalContas;

    @Column(name = "total_produtos", nullable = false)
    private Long totalProdutos;

    @Column(name = "total_categoria", nullable = false)
    private Long totalCategoria;

    @Column(name = "total_visitas")
    private Long totalVisitas;
}
