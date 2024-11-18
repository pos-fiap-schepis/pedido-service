package com.pedido.core.gateways;

import com.pedido.core.entities.Pedido;
import com.pedido.core.enums.StatusPedidoEnum;
import java.util.List;
import java.util.Optional;

public interface PedidoRepositoryGateway {

    Pedido salvar(Pedido pedido);

    List<Pedido> obterPedidos(List<StatusPedidoEnum> statusList);

    Optional<Pedido> obterPorId(Long id);
}
