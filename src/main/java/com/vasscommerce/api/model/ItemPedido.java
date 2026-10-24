package com.vasscommerce.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private BigDecimal valorUnitario;

    @JsonIgnore
    @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private Produto produto;
}
