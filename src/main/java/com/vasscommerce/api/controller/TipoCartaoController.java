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
@RequestMapping("/tipo-cartao")
public class TipoCartaoController {

    // GET /tipo-cartao
    @GetMapping
    public List<TipoCartao> listar() {
        return Arrays.asList(TipoCartao.values());
    }
}
