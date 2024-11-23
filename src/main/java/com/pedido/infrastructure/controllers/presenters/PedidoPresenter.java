package com.pedido.infrastructure.controllers.presenters;

import com.pedido.core.entities.Pedido;
import com.pedido.infrastructure.dtos.ItemPedidoDto;
import com.pedido.infrastructure.dtos.PedidoDto;
import java.util.stream.Collectors;

public class PedidoPresenter {

    public static PedidoDto converterDomainToDto(Pedido pedido) {
        return new PedidoDto(
                pedido.getId(),
                pedido.getClienteId(),
                pedido.getValor(),
                pedido.getSituacaoPagamento(),
                pedido.getStatus(),
                pedido.getDataHoraPagamento(),
                pedido.getDataHoraCriacao(),
                pedido.getDataHoraEntrega(),
                pedido.getItens().stream().map(i -> new ItemPedidoDto(i.getId())).collect(Collectors.toList())
        );
    }

}
