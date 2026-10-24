package com.vasscommerce.api.service;

import com.vasscommerce.api.model.Produto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/** Filtro por nome e faixa de valor, reaproveitado pelas duas implementações. */
final class ProdutoFiltro {

    private ProdutoFiltro() {
    }

    static List<Produto> aplicar(Collection<Produto> origem, String nome,
                                 BigDecimal valorMinimo, BigDecimal valorMaximo) {
        return origem.stream()
                .filter(p -> nome == null || nome.isBlank()
                        || (p.getNome() != null && p.getNome().toLowerCase().contains(nome.toLowerCase())))
                .filter(p -> valorMinimo == null || p.getValorUnitario().compareTo(valorMinimo) >= 0)
                .filter(p -> valorMaximo == null || p.getValorUnitario().compareTo(valorMaximo) <= 0)
                .toList();
    }
}
