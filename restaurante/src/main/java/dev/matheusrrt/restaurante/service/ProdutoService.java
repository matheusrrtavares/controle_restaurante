package dev.matheusrrt.restaurante.service;

import dev.matheusrrt.restaurante.domain.entity.CategoriaProduto;
import dev.matheusrrt.restaurante.domain.entity.Produto;
import dev.matheusrrt.restaurante.dto.request.ProdutoRequest;
import dev.matheusrrt.restaurante.dto.response.ProdutoResponse;
import dev.matheusrrt.restaurante.repository.CategoriaProdutoRepository;
import dev.matheusrrt.restaurante.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    public final ProdutoRepository produtoRepository;
    public final CategoriaProdutoRepository categoriaProdutoRepository;

    public ProdutoResponse cadastrar(ProdutoRequest request) {
        CategoriaProduto categoria = buscarCategoriaPorId(request.categoriaId());

        Produto produto = request.toEntity(categoria);
        Produto produtoSalvo = produtoRepository.save(produto);
        return ProdutoResponse.fromEntity(produtoSalvo);

    }

    public Page<ProdutoResponse> listar(Pageable pageable) {
        return produtoRepository.findAll(pageable).map(ProdutoResponse::fromEntity);
    }

    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = buscarProdutoPorId(id);
        return ProdutoResponse.fromEntity(produto);
    }

    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {
        Produto produto = buscarProdutoPorId(id);
        CategoriaProduto categoriaProduto = buscarCategoriaPorId(request.categoriaId());

        request.preencher(produto, categoriaProduto);

        Produto produtoAtualizado = produtoRepository.save(produto);
        return ProdutoResponse.fromEntity(produtoAtualizado);
    }

    public void excluir(Long id) {
        Produto produto = buscarProdutoPorId(id);
        produtoRepository.delete(produto);
    }

    private Produto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Produto não encontrado!"));
    }

    private CategoriaProduto buscarCategoriaPorId(Long id){
        return categoriaProdutoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Categoria não encontrada"));
    }

}
