package com.vasscommerce.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/** Formato único de erro devolvido pela API. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErro(
        LocalDateTime timestamp,
        int status,
        String erro,
        String mensagem,
        Map<String, String> campos
) {
}
