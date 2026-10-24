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
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteRepository clientes;
    private final CartaoRepository cartoes;
    private final PedidoRepository pedidos;

    public ClienteController(ClienteRepository clientes, CartaoRepository cartoes, PedidoRepository pedidos) {
        this.clientes = clientes;
        this.cartoes = cartoes;
        this.pedidos = pedidos;
    }

    private Cliente buscarCliente(Long id) {
        return clientes.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    // GET /cliente/1
    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable Long id) {
        return buscarCliente(id);
    }

    // GET /cliente/1/formas-de-pagamento
    @GetMapping("/{id}/formas-de-pagamento")
    public List<Cartao> formasDePagamento(@PathVariable Long id) {
        buscarCliente(id);
        return cartoes.findByTitularIdAndExcluidoFalseOrderById(id);
    }

    // GET /cliente/1/endereco
    @GetMapping("/{id}/endereco")
    public Endereco endereco(@PathVariable Long id) {
        Endereco endereco = buscarCliente(id).getEndereco();
        if (endereco == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não cadastrado");
        }
        return endereco;
    }

    // GET /cliente/1/pedido
    @GetMapping("/{id}/pedido")
    public List<Pedido> pedidosDoCliente(@PathVariable Long id) {
        buscarCliente(id);
        return pedidos.findByClienteIdOrderByDataCadastroDesc(id);
    }
}
