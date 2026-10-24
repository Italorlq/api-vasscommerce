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
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaRepository categorias;
    private final ProdutoRepository produtos;

    public CategoriaController(CategoriaRepository categorias, ProdutoRepository produtos) {
        this.categorias = categorias;
        this.produtos = produtos;
    }

    // GET /categoria  e  GET /categoria?nome=Eletr
    @GetMapping
    public List<Categoria> listar(@RequestParam(required = false) String nome) {
        if (nome == null || nome.isBlank()) {
            return categorias.findAll(Sort.by("id"));
        }
        return categorias.findByNomeContainingIgnoreCaseOrderById(nome);
    }

    // GET /categoria/1/produto
    @GetMapping("/{id}/produto")
    public List<Produto> produtosDaCategoria(@PathVariable Long id) {
        if (!categorias.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
        }
        return produtos.findByCategoriaIdOrderById(id);
    }
}
