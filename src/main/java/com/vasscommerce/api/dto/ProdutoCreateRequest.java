package com.vasscommerce.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/** Corpo enviado no POST /produto. */
public record ProdutoCreateRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
        String nome,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String descricao,

        @Size(max = 255, message = "A URL da foto deve ter no máximo 255 caracteres")
        String fotoUrl,

        @NotNull(message = "O valor unitário é obrigatório")
        @Positive(message = "O valor unitário deve ser maior que zero")
        BigDecimal valorUnitario,

        @NotNull(message = "A categoria é obrigatória")
        Long idCategoria
) {
}
