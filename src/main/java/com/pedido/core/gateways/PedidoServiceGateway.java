package com.pedido.core.gateways;

import com.pedido.core.entities.ItemPedido;
import com.pedido.core.entities.Pedido;
import java.util.List;

public interface PedidoServiceGateway {
    Pedido salvar(Pedido pedido);

    List<Pedido> obterPedidos();

    Pedido adicionarItem(ItemPedido itemPedido);

    Pedido removerItem(ItemPedido itemPedido);

    Pedido cancelarPedido(Long id);

    Pedido checkoutPedido(Long id);

    Pedido efetuarEntrega(Long id);

    Pedido atualizarPedidoPronto(Long id);
}
