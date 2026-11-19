package dev.matheusrrt.restaurante.domain.entity;

import dev.matheusrrt.restaurante.domain.enums.StatusMesa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mesas")
@Getter
@Setter
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;
    private String descricao;
    private Integer capacidade;

    @Enumerated(EnumType.STRING)
    private StatusMesa status = StatusMesa.LIVRE;



}
