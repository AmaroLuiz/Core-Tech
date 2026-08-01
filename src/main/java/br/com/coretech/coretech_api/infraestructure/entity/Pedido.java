package br.com.coretech.coretech_api.infraestructure.entity;

import br.com.coretech.coretech_api.infraestructure.Enums.FormaPagamento;
import br.com.coretech.coretech_api.infraestructure.Enums.PagamentoStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PagamentoStatus status;

    @Column(name = "valor_produto")
    private BigDecimal valorProduto;

    @Column(name = "valor_frete")
    private BigDecimal valorFrete;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "pagamento", nullable = false)
    private FormaPagamento formaPagamento;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PedidoItem> pedidoItems;

    public void adicionarItem(PedidoItem item) {
        this.pedidoItems.add(item);
        item.setPedido(this);
    }

    @PrePersist
    private void prePersist() {
        this.dataCriacao = LocalDateTime.now();

    }
    @PreUpdate
    private void preUpdate() {
        if (status.equals(PagamentoStatus.PAGO)) {
            this.dataPagamento = LocalDateTime.now();
        }
    }
}
