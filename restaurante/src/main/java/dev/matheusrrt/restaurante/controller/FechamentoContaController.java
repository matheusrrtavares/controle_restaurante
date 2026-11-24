package dev.matheusrrt.restaurante.controller;

import dev.matheusrrt.restaurante.domain.entity.FechamentoConta;
import dev.matheusrrt.restaurante.dto.request.FechamentoContaRequest;
import dev.matheusrrt.restaurante.dto.response.FechamentoContaResponse;
import dev.matheusrrt.restaurante.service.FechamentoContaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{pedidoId}/fechamento")
public class FechamentoContaController {

    private final FechamentoContaService service;

    public FechamentoContaController(FechamentoContaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FechamentoContaResponse fecharConta(@PathVariable Long pedidoId, @RequestBody FechamentoContaRequest request){
        return service.fecharConta(pedidoId, request);
    }

    @GetMapping
    public FechamentoContaResponse   buscarPorPedido(@PathVariable Long pedidoId){
        return service.buscarPorPedido(pedidoId);
    }

}
