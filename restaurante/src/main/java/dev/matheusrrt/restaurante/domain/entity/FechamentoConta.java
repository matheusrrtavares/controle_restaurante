package dev.matheusrrt.restaurante.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fechamentos_conta")
@Getter
@Setter
public class FechamentoConta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal subtotal;

    @Column(name = "taxa_servico")
    private BigDecimal taxaServico;

    private BigDecimal desconto;

    private BigDecimal total;

    private LocalDateTime data_fechamento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    public Pedido pedido;

    @PrePersist
    public void prePersist(){
        data_fechamento = LocalDateTime.now();
    }

}
