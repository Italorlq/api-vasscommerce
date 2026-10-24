package com.vasscommerce.api.mapper;

import com.vasscommerce.api.dto.ProdutoCreateRequest;
import com.vasscommerce.api.dto.ProdutoResponse;
import com.vasscommerce.api.model.Categoria;
import com.vasscommerce.api.model.Produto;

import java.time.LocalDateTime;

/** Converte Request DTO -> Entidade e Entidade -> Response DTO. */
public final class ProdutoMapper {

    private ProdutoMapper() {
    }

    public static Produto toEntity(ProdutoCreateRequest req, Long id) {
        LocalDateTime agora = LocalDateTime.now();

        Categoria categoria = new Categoria();
        categoria.setId(req.idCategoria());

        Produto p = new Produto();
        p.setId(id);
        p.setNome(req.nome());
        p.setDescricao(req.descricao());
        p.setFotoUrl(req.fotoUrl());
        p.setValorUnitario(req.valorUnitario());
        p.setCategoria(categoria);
        p.setDataCadastro(agora);
        p.setDataUltimaAtualizacao(agora);
        return p;
    }

    public static ProdutoResponse toResponse(Produto p) {
        Long idCategoria = p.getCategoria() != null ? p.getCategoria().getId() : null;
        return new ProdutoResponse(
                p.getId(),
                p.getNome(),
                p.getDescricao(),
                p.getFotoUrl(),
                p.getDataCadastro(),
                p.getDataUltimaAtualizacao(),
                p.getValorUnitario(),
                idCategoria);
    }
}
