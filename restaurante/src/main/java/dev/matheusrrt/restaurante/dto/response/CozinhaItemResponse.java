package dev.matheusrrt.restaurante.dto.response;

import dev.matheusrrt.restaurante.domain.entity.PedidoItem;
import dev.matheusrrt.restaurante.domain.enums.StatusItemPedido;

import java.math.BigDecimal;

public record CozinhaItemResponse(
        Long itemId,
        Long pedidoId,
        Integer numeroMesa,
        String produtoNome,
        Integer quantidade,
        String observacao,
        BigDecimal precoUnitario,
        StatusItemPedido statusItemPedido
) {

    public static CozinhaItemResponse fromEntity(PedidoItem item){
        return new CozinhaItemResponse(
                item.getId(),
                item.getPedido().getId(),
                item.getPedido().getMesa().getNumero(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getObservacao(),
                item.getPrecoUnitario(),
                item.getStatusItemPedido()
        );
    }

}
