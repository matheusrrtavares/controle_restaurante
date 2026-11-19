package dev.matheusrrt.restaurante.domain.entity;

import dev.matheusrrt.restaurante.domain.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido = StatusPedido.ABERTO;

    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    @PrePersist
    public void prePersist(){
        dataAbertura = LocalDateTime.now();
    }

}
