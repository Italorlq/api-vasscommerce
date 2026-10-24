package com.vasscommerce.api.controller;

import com.vasscommerce.api.model.*;
import com.vasscommerce.api.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoRepository produtos;

    public ProdutoController(ProdutoRepository produtos) {
        this.produtos = produtos;
    }

    // GET /produto/10
    @GetMapping("/{id}")
    public Produto buscar(@PathVariable Long id) {
        return produtos.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }
}
