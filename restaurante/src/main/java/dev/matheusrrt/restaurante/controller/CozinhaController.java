package dev.matheusrrt.restaurante.controller;

import dev.matheusrrt.restaurante.dto.response.CozinhaItemResponse;
import dev.matheusrrt.restaurante.service.CozinhaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinha")
public class CozinhaController {

    private final CozinhaService service;

    public CozinhaController(CozinhaService service) {
        this.service = service;
    }

    @GetMapping("itens/itens-pendentes")
    public List<CozinhaItemResponse> listarItensPendentes() {
        return service.listarItensPendentes();
    }

    @GetMapping("itens/itens-em-preparo")
    public List<CozinhaItemResponse> listarItensEmPreparo() {
        return service.listarItensEmPreparo();
    }

    @PatchMapping("/itens/{itemId}/iniciar-preparo")
    public CozinhaItemResponse inicarPreparo(@PathVariable Long itemId) {
        return service.iniciarPreparo(itemId);
    }

    @PatchMapping("/itens/{itemId}/marcar-pronto")
    public CozinhaItemResponse marcarComoPronto(@PathVariable Long itemId){
        return service.marcarComoPronto(itemId);
    }

    @PatchMapping("/itens/{itemId}/entregar")
    public CozinhaItemResponse entregarItem(@PathVariable Long itemId){
        return service.entregarItem(itemId);
    }

}
