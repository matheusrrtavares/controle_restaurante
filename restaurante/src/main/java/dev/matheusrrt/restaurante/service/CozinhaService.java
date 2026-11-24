package dev.matheusrrt.restaurante.service;

import dev.matheusrrt.restaurante.domain.entity.PedidoItem;
import dev.matheusrrt.restaurante.domain.enums.StatusItemPedido;
import dev.matheusrrt.restaurante.dto.response.CozinhaItemResponse;
import dev.matheusrrt.restaurante.exception.RegraNegocioException;
import dev.matheusrrt.restaurante.repository.PedidoItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CozinhaService {

    private final PedidoItemRepository pedidoItemRepository;

    public CozinhaService(PedidoItemRepository pedidoItemRepository) {
        this.pedidoItemRepository = pedidoItemRepository;
    }

    public List<CozinhaItemResponse> listarItensPendentes() {
        return pedidoItemRepository.findByStatusItemPedidoOrderByIdAsc(StatusItemPedido.PENDENTE)
                .stream()
                .map(CozinhaItemResponse::fromEntity)
                .toList();
    }

    public List<CozinhaItemResponse> listarItensEmPreparo() {
        return pedidoItemRepository.findByStatusItemPedidoOrderByIdAsc(StatusItemPedido.EM_PREPARO)
                .stream()
                .map(CozinhaItemResponse::fromEntity)
                .toList();
    }

    public CozinhaItemResponse iniciarPreparo(Long itemId) {
        PedidoItem item = buscarItemPorId(itemId);

        if (item.getStatusItemPedido() != StatusItemPedido.PENDENTE) {
            throw new RegraNegocioException("Somente itens pendentes podem inicar preparo");
        }
        item.setStatusItemPedido(StatusItemPedido.EM_PREPARO);
        item.setDataInicioPreparo(LocalDateTime.now());

        return CozinhaItemResponse.fromEntity(pedidoItemRepository.save(item));
    }

    public CozinhaItemResponse marcarComoPronto(Long itemId) {

        PedidoItem item = buscarItemPorId(itemId);

        if (item.getStatusItemPedido() != StatusItemPedido.EM_PREPARO) {
            throw new RegraNegocioException("Apenas itens em preparo podem ser marcados como pronto");
        }
        item.setStatusItemPedido(StatusItemPedido.PRONTO);
        item.setDataPronto(LocalDateTime.now());

        return CozinhaItemResponse.fromEntity(pedidoItemRepository.save(item));

    }

    public CozinhaItemResponse entregarItem(Long itemId) {

        PedidoItem item = buscarItemPorId(itemId);

        if (item.getStatusItemPedido() != StatusItemPedido.PRONTO) {
            throw new RegraNegocioException("Apenas itens com status pronto podem ser entregues");
        }

        item.setStatusItemPedido(StatusItemPedido.ENTREGUE);
        item.setDataEntrega(LocalDateTime.now());

        return CozinhaItemResponse.fromEntity(pedidoItemRepository.save(item));

    }

    private PedidoItem buscarItemPorId(Long id) {
        return pedidoItemRepository.findById(id).orElseThrow(
                () -> new RegraNegocioException("Item não encontrado"));
    }

}
