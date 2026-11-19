package dev.matheusrrt.restaurante.domain.entity;

import dev.matheusrrt.restaurante.domain.enums.FormaPagamento;
import dev.matheusrrt.restaurante.domain.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;

    @Column(name = "forma_pagamento")
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento = StatusPagamento.PENDENTE;

    @Column(name = "codigo_transacao_externa")
    private String codigoTransacaoExterna;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @PrePersist
    private void prePersist(){
        criadoEm = LocalDateTime.now();
    }

}
