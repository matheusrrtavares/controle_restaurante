package dev.matheusrrt.restaurante.service;

import dev.matheusrrt.restaurante.domain.entity.Mesa;
import dev.matheusrrt.restaurante.domain.entity.Pedido;
import dev.matheusrrt.restaurante.domain.entity.PedidoItem;
import dev.matheusrrt.restaurante.domain.entity.Produto;
import dev.matheusrrt.restaurante.domain.enums.StatusItemPedido;
import dev.matheusrrt.restaurante.domain.enums.StatusMesa;
import dev.matheusrrt.restaurante.domain.enums.StatusPedido;
import dev.matheusrrt.restaurante.dto.request.PedidoItemRequest;
import dev.matheusrrt.restaurante.dto.request.PedidoRequest;
import dev.matheusrrt.restaurante.dto.response.PedidoItemResponse;
import dev.matheusrrt.restaurante.dto.response.PedidoResponse;
import dev.matheusrrt.restaurante.exception.RegraNegocioException;
import dev.matheusrrt.restaurante.repository.MesaRepository;
import dev.matheusrrt.restaurante.repository.PedidoItemRepository;
import dev.matheusrrt.restaurante.repository.PedidoRepository;
import dev.matheusrrt.restaurante.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoItemRepository pedidoItemRepository;

    public PedidoService(PedidoRepository pedidoRepository, MesaRepository mesaRepository, ProdutoRepository produtoRepository, PedidoItemRepository pedidoItemRepository) {
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
    }

    public PedidoResponse abrirPedido(PedidoRequest request) {

        Mesa mesa = mesaRepository.findById(request.mesaId())
                .orElseThrow(() -> new RegraNegocioException("Mesa não encontrada"));

        if (mesa.getStatus() != StatusMesa.LIVRE) {
            throw new RegraNegocioException("Mesa não disponível para abertura de pedido");
        }

        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setStatusPedido(StatusPedido.ABERTO);
        pedido.setObservacao(request.observacao());
        pedido.setDataAbertura(LocalDateTime.now());

        mesa.setStatus(StatusMesa.OCUPADA);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        Mesa mesaSalva = mesaRepository.save(mesa);

        return PedidoResponse.fromEntity(pedido);

    }

    public Page<PedidoResponse> listarPedidos(Pageable pageable) {
        return pedidoRepository.findAll(pageable).map(PedidoResponse::fromEntity);
    }

    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = buscarPedidoPorId(id);

        return PedidoResponse.fromEntity(pedido);

    }

    public PedidoItemResponse adicionarItem(Long pedidoId, PedidoItemRequest request) {

        Pedido pedido = buscarPedidoPorId(pedidoId);

        if (pedido.getStatusPedido() != StatusPedido.ABERTO) {
            throw new RegraNegocioException("O pedido já foi fechado, não é possível adicionar mais itens!");
        }

        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new RegraNegocioException("Produto não encontrado"));

        if (!produto.getDisponivel()) {
            throw new RegraNegocioException("Produto indisponível");
        }

        if (request.quantidade() == null || request.quantidade() <= 0) {
            throw new RegraNegocioException("A quantidade deve ser maior que zero");
        }

        PedidoItem item = new PedidoItem();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(request.quantidade());
        item.setPrecoUnitario(produto.getPreco());
        item.setObservacao(request.observacao());
        item.setStatusItemPedido(StatusItemPedido.PENDENTE);
        PedidoItem itemSalvo = pedidoItemRepository.save(item);

        return PedidoItemResponse.fromEntity(itemSalvo);

    }

    public List<PedidoItemResponse> listaItens(Long pedidoId) {
        buscarPedidoPorId(pedidoId);
        return pedidoItemRepository.findByPedidoId(pedidoId).stream()
                .map(PedidoItemResponse::fromEntity)
                .collect(Collectors.toList());
    }

    private Pedido buscarPedidoPorId(Long pedidoId){
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RegraNegocioException("Pedido não encontrado"));
    }


}
