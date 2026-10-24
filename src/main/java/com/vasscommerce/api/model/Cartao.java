package com.vasscommerce.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataCriacao;
    private boolean excluido;

    @Enumerated(EnumType.STRING)
    private TipoCartao tipo;

    @JsonIgnore
    @ManyToOne
    private Cliente titular;
}
