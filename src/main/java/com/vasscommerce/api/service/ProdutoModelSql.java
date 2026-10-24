package com.vasscommerce.api.service;

import com.vasscommerce.api.dto.ProdutoCreateRequest;
import com.vasscommerce.api.mapper.ProdutoMapper;
import com.vasscommerce.api.model.Categoria;
import com.vasscommerce.api.model.Produto;
import com.vasscommerce.api.repository.CategoriaRepository;
import com.vasscommerce.api.repository.ProdutoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/** Implementação que usa o banco PostgreSQL de verdade, por meio dos repositories JPA. */
@Service("produtoModelSql")
@Profile("sql")
public class ProdutoModelSql implements ProdutoModel {

    private final ProdutoRepository produtos;
    private final CategoriaRepository categorias;

    public ProdutoModelSql(ProdutoRepository produtos, CategoriaRepository categorias) {
        this.produtos = produtos;
        this.categorias = categorias;
    }

    @Override
    public Produto criar(ProdutoCreateRequest req) {
        Categoria categoria = categorias.findById(req.idCategoria())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Categoria não encontrada: " + req.idCategoria()));

        Produto p = ProdutoMapper.toEntity(req, null);   // id null: o banco gera
        p.setCategoria(categoria);
        return produtos.save(p);
    }

    @Override
    public Optional<Produto> obterPorId(Long id) {
        return produtos.findById(id);
    }

    @Override
    public List<Produto> buscar(String nome, BigDecimal valorMinimo, BigDecimal valorMaximo) {
        return ProdutoFiltro.aplicar(produtos.findAll(Sort.by("id")), nome, valorMinimo, valorMaximo);
    }
}
