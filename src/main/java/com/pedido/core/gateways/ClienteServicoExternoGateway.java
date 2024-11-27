package com.pedido.core.gateways;

import com.pedido.core.entities.Cliente;

public interface ClienteServicoExternoGateway {

    Cliente getClienteById(Long id);
}
