package com.vasscommerce.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/** Padroniza o JSON de todos os erros da API. */
@RestControllerAdvice
public class ApiExceptionHandler {

    // Falha no @Valid do corpo da requisição -> 400 com a lista de campos inválidos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErro> validacao(MethodArgumentNotValidException ex) {
        Map<String, String> campos = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> campos.putIfAbsent(e.getField(), e.getDefaultMessage()));
        return resposta(HttpStatus.BAD_REQUEST, "Dados inválidos", campos);
    }

    // JSON malformado ou com tipo errado -> 400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErro> jsonIlegivel(HttpMessageNotReadableException ex) {
        return resposta(HttpStatus.BAD_REQUEST, "JSON malformado ou com tipos inválidos", null);
    }

    // Erros lançados com ResponseStatusException (404, 400 de regra de negócio, etc.)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErro> status(ResponseStatusException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        String mensagem = ex.getReason() != null ? ex.getReason() : status.getReasonPhrase();
        return resposta(status, mensagem, null);
    }

    private ResponseEntity<ApiErro> resposta(HttpStatus status, String mensagem, Map<String, String> campos) {
        ApiErro erro = new ApiErro(LocalDateTime.now(), status.value(), status.getReasonPhrase(), mensagem, campos);
        return ResponseEntity.status(status).body(erro);
    }
}
