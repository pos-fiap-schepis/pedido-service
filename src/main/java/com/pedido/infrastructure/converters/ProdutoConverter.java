package com.pedido.infrastructure.converters;

import com.pedido.core.entities.Produto;
import com.pedido.infrastructure.dtos.ProdutoDto;

public class ProdutoConverter {

    public static Produto converterDtoToProduto(ProdutoDto produtoDto) {
        return new Produto.Builder()
                .id(produtoDto.getId())
                .nome(produtoDto.getNome())
                .descricao(produtoDto.getDescricao())
                .valor(produtoDto.getValor())
                .categoria(produtoDto.getCategoria())
                .build();
    }
}
