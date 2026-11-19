package dev.matheusrrt.restaurante.repository;

import dev.matheusrrt.restaurante.domain.entity.CategoriaProduto;
import dev.matheusrrt.restaurante.domain.entity.Produto;
import dev.matheusrrt.restaurante.dto.response.ProdutoResponse;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {}
