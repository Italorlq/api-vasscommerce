package com.vasscommerce.api.service;

import com.vasscommerce.api.dto.ProdutoCreateRequest;
import com.vasscommerce.api.model.Produto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/** Contrato da regra de negócio de Produto. O controller depende só desta interface. */
public interface ProdutoModel {

    Produto criar(ProdutoCreateRequest req);

    Optional<Produto> obterPorId(Long id);

    List<Produto> buscar(String nome, BigDecimal valorMinimo, BigDecimal valorMaximo);
}
