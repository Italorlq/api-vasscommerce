package com.vasscommerce.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rua;
    private Integer numero;
    private String cep;
    private String complemento;
    private String telefone;
    private String bairro;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataUltimaAtualizacao;

    @ManyToOne
    private Cidade cidade;
}
