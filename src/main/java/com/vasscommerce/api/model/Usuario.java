package com.vasscommerce.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private String email;

    @JsonIgnore
    private String senha;

    private String fotoUrl;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataUltimaAtualizacao;
}
