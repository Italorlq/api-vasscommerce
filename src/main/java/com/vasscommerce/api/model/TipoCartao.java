package com.vasscommerce.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoCartao {
    DEBITO(1),
    CREDITO(2);

    private final int id;

    TipoCartao(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return name();
    }
}
