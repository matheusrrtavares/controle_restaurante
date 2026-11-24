package dev.matheusrrt.restaurante.dto.request;

import java.math.BigDecimal;

public record FechamentoContaRequest (
        BigDecimal taxaServico,
        BigDecimal desconto
) {
}
