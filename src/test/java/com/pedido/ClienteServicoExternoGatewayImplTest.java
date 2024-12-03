package com.pedido;

import com.pedido.core.entities.Cliente;
import com.pedido.infrastructure.dtos.ClienteDto;
import com.pedido.infrastructure.feign.ClienteServicoExterno;
import com.pedido.infrastructure.feign.ClienteServicoExternoGatewayImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class ClienteServicoExternoGatewayImplTest {

    @Mock
    private ClienteServicoExterno clienteServicoExterno;

    @InjectMocks
    private ClienteServicoExternoGatewayImpl clienteServicoExternoGatewayImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClienteById() {
        ClienteDto clienteDto = new ClienteDto(1L, "Cliente Teste", "Descrição Teste", "teste");
        when(clienteServicoExterno.findById(anyLong())).thenReturn(clienteDto);

        Cliente cliente = clienteServicoExternoGatewayImpl.getClienteById(1L);

        assertEquals(clienteDto.id(), cliente.id());
        assertEquals(clienteDto.nome(), cliente.nome());
    }
}
