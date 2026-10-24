package com.vasscommerce.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Corpo devolvido pelas rotas de produto. */
public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        String fotoUrl,
        LocalDateTime dataCadastro,
        LocalDateTime dataUltimaAtualizacao,
        BigDecimal valorUnitario,
        Long idCategoria
) {
}
