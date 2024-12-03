package com.pedido;

import com.pedido.core.entities.Cliente;
import com.pedido.infrastructure.converters.ClienteConverter;
import com.pedido.infrastructure.dtos.ClienteDto;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClienteConverterTest {

    @Test
    public void testConverterClienteDtoToCliente() {
        ClienteDto clienteDto = new ClienteDto(1L, "Nome Teste", "email@teste.com", "12345678900");

        Cliente cliente = ClienteConverter.converterClienteDtoToCliente(clienteDto);

        assertThat(cliente.id()).isEqualTo(clienteDto.id());
        assertThat(cliente.nome()).isEqualTo(clienteDto.nome());
        assertThat(cliente.email()).isEqualTo(clienteDto.email());
        assertThat(cliente.cpf()).isEqualTo(clienteDto.cpf());
    }
}
