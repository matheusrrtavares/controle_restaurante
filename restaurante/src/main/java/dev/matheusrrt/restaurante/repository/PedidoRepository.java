package dev.matheusrrt.restaurante.repository;

import dev.matheusrrt.restaurante.domain.entity.CategoriaProduto;
import dev.matheusrrt.restaurante.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
