package com.vasscommerce.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Cliente extends Usuario {
    private LocalDate dataNascimento;
    private String cpf;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
}
