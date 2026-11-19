package dev.matheusrrt.restaurante.repository;

import dev.matheusrrt.restaurante.domain.entity.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {
}
