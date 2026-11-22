package dev.matheusrrt.restaurante.dto.request;

import dev.matheusrrt.restaurante.domain.enums.StatusItemPedido;

import java.math.BigDecimal;

public record PedidoItemRequest(

        Long produtoId,
        Integer quantidade,
        String observacao

) {
}
