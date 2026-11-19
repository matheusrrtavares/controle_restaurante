package dev.matheusrrt.restaurante.dto.response;

import dev.matheusrrt.restaurante.domain.entity.Produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean disponivel,
        Integer tempoPreparoMinutos,
        Long categoriaId,
        String nomeCategoria,
        LocalDateTime criadoEm
) {

    public static ProdutoResponse fromEntity(Produto produto){
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getDisponivel(),
                produto.getTempoPreparoMinutos(),
                produto.getCategoriaProduto().getId(),
                produto.getCategoriaProduto().getNome(),
                produto.getCriadoEm()
        );
    }

}
