package com.vasscommerce.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valorPago;

    @JsonIgnore
    @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private Cartao cartao;
}
