package dev.matheusrrt.restaurante.controller;

import dev.matheusrrt.restaurante.dto.request.ProdutoRequest;
import dev.matheusrrt.restaurante.dto.response.ProdutoResponse;
import dev.matheusrrt.restaurante.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponse cadastrar(@RequestBody ProdutoRequest request) {
        return service.cadastrar(request);
    }

    @GetMapping
    public Page<ProdutoResponse> listar(Pageable p){
        return service.listar(p);
    }

    @GetMapping("/{id}")
    public ProdutoResponse buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProdutoResponse atualizar(@PathVariable Long id, ProdutoRequest request){
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir (@PathVariable Long id){
         service.excluir(id);
    }

}
