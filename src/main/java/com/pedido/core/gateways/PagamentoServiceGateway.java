package com.pedido.core.gateways;

import com.pedido.core.entities.Pedido;
import com.pedido.core.enums.StatusPagamentoEnum;

public interface PagamentoServiceGateway {

    Pedido atualizarPedidoSePagamentoAprovado(Long id, StatusPagamentoEnum statusPagamento);

    String obterSituacaoPagamento(Long idPedido);


}
