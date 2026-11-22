package dev.matheusrrt.restaurante.repository;

import dev.matheusrrt.restaurante.domain.entity.PedidoItem;
import dev.matheusrrt.restaurante.domain.enums.StatusItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

     List<PedidoItem> findByPedidoId(Long pedidoId);

     List<PedidoItem> findByStatusItemPedidoOrderByIdAsc(StatusItemPedido statusItemPedido);

}
