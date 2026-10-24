package com.vasscommerce.api.service;

import com.vasscommerce.api.dto.ProdutoCreateRequest;
import com.vasscommerce.api.mapper.ProdutoMapper;
import com.vasscommerce.api.model.Produto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/** Implementação em memória: os produtos ficam numa lista e somem quando a aplicação para. */
@Service("produtoModelMemoria")
@Profile("mem")
public class ProdutoModelMemoria implements ProdutoModel {

    private final List<Produto> banco = new CopyOnWriteArrayList<>();
    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public Produto criar(ProdutoCreateRequest req) {
        Long novoId = seq.incrementAndGet();
        Produto p = ProdutoMapper.toEntity(req, novoId);
        banco.add(p);
        return p;
    }

    @Override
    public Optional<Produto> obterPorId(Long id) {
        return banco.stream().filter(p -> Objects.equals(p.getId(), id)).findFirst();
    }

    @Override
    public List<Produto> buscar(String nome, BigDecimal valorMinimo, BigDecimal valorMaximo) {
        return ProdutoFiltro.aplicar(banco, nome, valorMinimo, valorMaximo);
    }
}
