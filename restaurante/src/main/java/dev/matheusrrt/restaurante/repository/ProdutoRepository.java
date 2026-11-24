package dev.matheusrrt.restaurante.repository;

import dev.matheusrrt.restaurante.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {}
