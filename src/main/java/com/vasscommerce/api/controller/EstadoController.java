package com.vasscommerce.api.controller;

import com.vasscommerce.api.model.*;
import com.vasscommerce.api.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/estado")
public class EstadoController {

    private final EstadoRepository estados;
    private final CidadeRepository cidades;

    public EstadoController(EstadoRepository estados, CidadeRepository cidades) {
        this.estados = estados;
        this.cidades = cidades;
    }

    // GET /estado
    @GetMapping
    public List<Estado> listar() {
        return estados.findAll(Sort.by("nome"));
    }

    // GET /estado/1/cidade
    @GetMapping("/{id}/cidade")
    public List<Cidade> cidadesDoEstado(@PathVariable Long id) {
        if (!estados.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado não encontrado");
        }
        return cidades.findByEstadoIdOrderByNome(id);
    }
}
