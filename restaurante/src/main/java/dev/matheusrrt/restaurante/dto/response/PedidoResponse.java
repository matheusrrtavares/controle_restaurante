package dev.matheusrrt.restaurante.dto.response;

import dev.matheusrrt.restaurante.domain.entity.Pedido;
import dev.matheusrrt.restaurante.domain.enums.StatusPedido;

import java.time.LocalDateTime;

public record PedidoResponse(
    Long id,
    Long mesaId,
    Integer numeroMesa,
    LocalDateTime dataAbertura,
    LocalDateTime dataFechamento,
    StatusPedido statusPedido,
    String observacao
) {

    public static PedidoResponse fromEntity(Pedido pedido){
        return new PedidoResponse(
                pedido.getId(),
                pedido.getMesa().getId(),
                pedido.getMesa().getNumero(),
                pedido.getDataAbertura(),
                pedido.getDataFechamento(),
                pedido.getStatusPedido(),
                pedido.getObservacao()
        );
    }

}
