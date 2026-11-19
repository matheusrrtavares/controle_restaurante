package dev.matheusrrt.restaurante.dto.request;

import dev.matheusrrt.restaurante.domain.entity.CategoriaProduto;
import dev.matheusrrt.restaurante.domain.entity.Produto;

import java.math.BigDecimal;

public record ProdutoRequest(
        Long categoriaId,
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean disponivel,
        Integer tempoPreparoMinutos
) {

    public Produto toEntity(CategoriaProduto categoria){
        Produto produto = new Produto();
        preencher(produto, categoria);
        return produto;
    }

    public void preencher(Produto produto, CategoriaProduto categoria){
        produto.setCategoriaProduto(categoria);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setDisponivel(disponivel != null ? disponivel : false);
        produto.setTempoPreparoMinutos(tempoPreparoMinutos);
    }

}
