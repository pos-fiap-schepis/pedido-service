package com.pedido.infrastructure.converters;

import com.pedido.core.entities.Cliente;
import com.pedido.infrastructure.dtos.ClienteDto;

public class ClienteConverter {

    public static Cliente converterClienteDtoToCliente(ClienteDto clienteDto) {
        return new Cliente.Builder()
                .id(clienteDto.id())
                .nome(clienteDto.nome())
                .cpf(clienteDto.cpf())
                .email(clienteDto.email())
                .build();
    }
}
