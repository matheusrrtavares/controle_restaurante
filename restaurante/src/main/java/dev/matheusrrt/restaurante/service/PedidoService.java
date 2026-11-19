package dev.matheusrrt.restaurante.service;

import dev.matheusrrt.restaurante.domain.entity.Mesa;
import dev.matheusrrt.restaurante.domain.entity.Pedido;
import dev.matheusrrt.restaurante.domain.enums.StatusMesa;
import dev.matheusrrt.restaurante.domain.enums.StatusPedido;
import dev.matheusrrt.restaurante.dto.request.PedidoRequest;
import dev.matheusrrt.restaurante.dto.response.PedidoResponse;
import dev.matheusrrt.restaurante.exception.RegraNegocioException;
import dev.matheusrrt.restaurante.repository.MesaRepository;
import dev.matheusrrt.restaurante.repository.PedidoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;

    public PedidoService(PedidoRepository pedidoRepository, MesaRepository mesaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
    }

    public PedidoResponse abrirPedido(PedidoRequest request){

        Mesa mesa = mesaRepository.findById(request.mesaId())
                .orElseThrow(() -> new RegraNegocioException("Mesa não encontrada"));

        if(mesa.getStatus() != StatusMesa.LIVRE){
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

    public Page<PedidoResponse> listarPedidos(Pageable pageable){
        return pedidoRepository.findAll(pageable).map(PedidoResponse::fromEntity);
    }

    public PedidoResponse buscarPedidoPorId(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Pedido não encontrado"));

        return PedidoResponse.fromEntity(pedido);

    }

}
