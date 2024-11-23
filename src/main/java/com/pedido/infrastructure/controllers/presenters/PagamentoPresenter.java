package com.pedido.infrastructure.controllers.presenters;

import com.pedido.core.entities.Pedido;
import com.pedido.infrastructure.dtos.PagamentoDto;

public class PagamentoPresenter {


    public static PagamentoDto converterPedidoToPagamentoDto(Pedido pedido) {
        return PagamentoDto.builder().id(pedido.getId()).situacaoPagamento(pedido.getSituacaoPagamento().getDescricao()).build();
    }

}
