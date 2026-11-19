package dev.matheusrrt.restaurante.repository;

import dev.matheusrrt.restaurante.domain.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesaRepository  extends JpaRepository<Mesa, Long> {
}
