package com.pedido.infrastructure.feign;

import com.pedido.core.entities.Cliente;
import com.pedido.core.gateways.ClienteServicoExternoGateway;
import com.pedido.infrastructure.converters.ClienteConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteServicoExternoGatewayImpl implements ClienteServicoExternoGateway {

    @Autowired
    private ClienteServicoExterno clienteServicoExterno;


    @Override
    public Cliente getClienteById(Long id) {
        return ClienteConverter.converterClienteDtoToCliente(clienteServicoExterno.findById(id));
    }
}
