package dev.matheusrrt.restaurante.service;

import dev.matheusrrt.restaurante.domain.entity.FechamentoConta;
import dev.matheusrrt.restaurante.domain.entity.Pedido;
import dev.matheusrrt.restaurante.domain.entity.PedidoItem;
import dev.matheusrrt.restaurante.domain.enums.StatusItemPedido;
import dev.matheusrrt.restaurante.domain.enums.StatusPedido;
import dev.matheusrrt.restaurante.dto.request.FechamentoContaRequest;
import dev.matheusrrt.restaurante.dto.response.FechamentoContaResponse;
import dev.matheusrrt.restaurante.exception.RegraNegocioException;
import dev.matheusrrt.restaurante.repository.FechamentoContaRepository;
import dev.matheusrrt.restaurante.repository.PedidoItemRepository;
import dev.matheusrrt.restaurante.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FechamentoContaService {

    private final FechamentoContaRepository fechamentoContaRepository;
    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;


    public FechamentoContaService(FechamentoContaRepository fechamentoContaRepository, PedidoRepository pedidoRepository, PedidoItemRepository pedidoItemRepository) {
        this.fechamentoContaRepository = fechamentoContaRepository;
        this.pedidoRepository = pedidoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
    }

    public FechamentoContaResponse fecharConta(Long id, FechamentoContaRequest request) {
        Pedido pedido = buscarPedidoPorId(id);
        if (pedido.getStatusPedido() == StatusPedido.FECHADO) {
            throw new RegraNegocioException("Não é possível fechar pedidos que já estejam fechados");
        }

        if (pedido.getStatusPedido() == StatusPedido.CANCELADO) {
            throw new RegraNegocioException("Pedido cancelado não pode ser fechado");
        }

        if (fechamentoContaRepository.existsByPedidoId(id)) {
            throw new RegraNegocioException("Já existe um fechamento com esse pedido");
        }

        List<PedidoItem> itens = pedidoItemRepository.findByPedidoId(id);
        if (itens.isEmpty()) {
            throw new RegraNegocioException("Não é possível fechar uma conta sem itens");
        }

        List<PedidoItem> itensNaoEntregues = pedidoItemRepository.findByPedidoIdAndStatusItemPedidoNot(id, StatusItemPedido.ENTREGUE);
        if (!itensNaoEntregues.isEmpty()) {
            throw new RegraNegocioException("Não é possível fechar conta com pedidos não entregues");
        }

        BigDecimal subTotal = itens.stream().map(
                item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()))).reduce(BigDecimal.ZERO /*valor inicial da soma*/, BigDecimal::add /*função de soma*/);

        BigDecimal taxaServico = request.taxaServico() != null ? request.taxaServico() : BigDecimal.ZERO;
        BigDecimal desconto = request.desconto() != null ? request.desconto() : BigDecimal.ZERO;

        if (taxaServico.compareTo(BigDecimal.ZERO) < 0) { //retorna 0, -1, 1
            throw new RegraNegocioException("A taxa de serviço não pode ter o valor negativo");
        }

        if (desconto.compareTo(BigDecimal.ZERO) < 0) {
            throw new RegraNegocioException("O desconto não pode ser negativo");
        }

        BigDecimal total = subTotal.add(taxaServico.subtract(desconto));

        if (total.compareTo(BigDecimal.ZERO) < 0) {
            throw new RegraNegocioException("O valor total não pode ser negativo");
        }

        FechamentoConta fechamentoConta = new FechamentoConta();
        fechamentoConta.setPedido(pedido);
        fechamentoConta.setSubtotal(subTotal);
        fechamentoConta.setTotal(total);
        fechamentoConta.setTaxaServico(taxaServico);
        fechamentoConta.setDesconto(desconto);

        pedido.setStatusPedido(StatusPedido.FECHADO);
        pedido.setDataFechamento(LocalDateTime.now());

        FechamentoConta fechamentoSalvo = fechamentoContaRepository.save(fechamentoConta);
        pedidoRepository.save(pedido);
        return FechamentoContaResponse.fromEntity(fechamentoSalvo);

    }

    private Pedido buscarPedidoPorId(Long pedidoId){
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RegraNegocioException("Pedido não encontrado"));
    }

    public FechamentoContaResponse buscarPorPedido(Long id) {
        FechamentoConta fechamento = fechamentoContaRepository.findByPedidoId(id)
                .orElseThrow(() -> new RegraNegocioException("Fechamento não encontrado"));
        return FechamentoContaResponse.fromEntity(fechamento);
    }

}
