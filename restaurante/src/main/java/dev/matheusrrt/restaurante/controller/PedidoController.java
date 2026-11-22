package dev.matheusrrt.restaurante.controller;

import dev.matheusrrt.restaurante.dto.request.PedidoItemRequest;
import dev.matheusrrt.restaurante.dto.request.PedidoRequest;
import dev.matheusrrt.restaurante.dto.response.PedidoItemResponse;
import dev.matheusrrt.restaurante.dto.response.PedidoResponse;
import dev.matheusrrt.restaurante.service.PedidoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse abrirPedido(@RequestBody PedidoRequest request){
        return service.abrirPedido(request);
    }

    @GetMapping
    public Page<PedidoResponse> listarPedidos(Pageable pageable){
        return service.listarPedidos(pageable);
    }

    @GetMapping("/{id}")
    public PedidoResponse buscarPedidoPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PostMapping("/{pedidoId}/itens")
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoItemResponse adicionarItemPedido(@PathVariable Long pedidoId, @RequestBody PedidoItemRequest request){
        return service.adicionarItem(pedidoId, request);
    }

    @GetMapping("/{pedidoId}/itens")
    public List<PedidoItemResponse> listarItensPedido(@PathVariable Long pedidoId){
        return service.listaItens(pedidoId);
    }

}
