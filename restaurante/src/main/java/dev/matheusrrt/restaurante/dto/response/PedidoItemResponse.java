package dev.matheusrrt.restaurante.dto.response;

import dev.matheusrrt.restaurante.domain.entity.PedidoItem;
import dev.matheusrrt.restaurante.domain.enums.StatusItemPedido;

import java.math.BigDecimal;

public record PedidoItemResponse(
        Long id,
        Long produtoId,
        Long pedidoId,
        String produtoNome,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal total,
        String observacao,
        StatusItemPedido statusItemPedido
) {

    public static PedidoItemResponse fromEntity(PedidoItem pedidoItem){

        BigDecimal total = pedidoItem.getPrecoUnitario()
                .multiply(BigDecimal.valueOf(pedidoItem.getQuantidade()));

        return new PedidoItemResponse(
                pedidoItem.getId(),
                pedidoItem.getProduto().getId(),
                pedidoItem.getPedido().getId(),
                pedidoItem.getProduto().getNome(),
                pedidoItem.getQuantidade(),
                pedidoItem.getPrecoUnitario(),
                total,
                pedidoItem.getObservacao(),
                pedidoItem.getStatusItemPedido()
        );
    }

}











