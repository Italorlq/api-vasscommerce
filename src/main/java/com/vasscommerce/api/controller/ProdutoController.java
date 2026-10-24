package com.vasscommerce.api.controller;

import com.vasscommerce.api.dto.ProdutoCreateRequest;
import com.vasscommerce.api.dto.ProdutoResponse;
import com.vasscommerce.api.mapper.ProdutoMapper;
import com.vasscommerce.api.model.Produto;
import com.vasscommerce.api.service.ProdutoModel;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/produto", produces = "application/json")
public class ProdutoController {

    private final ProdutoModel model;

    // Injeção por construtor: o controller só conhece a interface
    public ProdutoController(ProdutoModel model) {
        this.model = model;
    }

    // POST /produto -> 201 Created + Location
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ProdutoResponse> create(@Valid @RequestBody ProdutoCreateRequest body) {
        Produto created = model.criar(body);
        URI location = URI.create("/produto/" + created.getId());
        return ResponseEntity.created(location).body(ProdutoMapper.toResponse(created));
    }

    // GET /produto/{id} -> 200 ou 404
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> get(@PathVariable Long id) {
        return model.obterPorId(id)
                .map(p -> ResponseEntity.ok(ProdutoMapper.toResponse(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET /produto?nome=&valorMinimo=&valorMaximo= -> 200
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> list(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) BigDecimal valorMinimo,
            @RequestParam(required = false) BigDecimal valorMaximo) {

        List<Produto> found = model.buscar(nome, valorMinimo, valorMaximo);
        return ResponseEntity.ok(found.stream().map(ProdutoMapper::toResponse).toList());
    }
}
