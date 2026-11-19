package dev.matheusrrt.restaurante.repository;

import dev.matheusrrt.restaurante.domain.entity.CategoriaProduto;
import dev.matheusrrt.restaurante.domain.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
