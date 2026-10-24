package com.vasscommerce.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PedidoStatus {
    AGUARDANDO_PAGAMENTO(1),
    SEPARANDO_ESTOQUE(2),
    ENTREGUE_TRANSPORTADORA(3),
    ENTREGUE_CLIENTE(4);

    private final int id;

    PedidoStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return name();
    }
}
