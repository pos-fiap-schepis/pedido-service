package com.pedido;

import com.pedido.core.entities.Pedido;
import com.pedido.core.gateways.PedidoServiceGateway;
import com.pedido.infrastructure.controllers.PedidoController;
import com.pedido.infrastructure.controllers.commands.AdicionarItemCommand;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PedidoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoServiceGateway pedidoServiceGateway;

    @InjectMocks
    private PedidoController pedidoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
    }

    @Test
    void testSalvar() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setItens(new ArrayList<>());

        when(pedidoServiceGateway.salvar(any(Pedido.class))).thenReturn(pedido);

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testObterPedidos() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setItens(new ArrayList<>()); // Inicializa a lista de itens

        when(pedidoServiceGateway.obterPedidos()).thenReturn(Collections.singletonList(pedido));

        mockMvc.perform(get("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testAdicionarItem() throws Exception {
        AdicionarItemCommand command = new AdicionarItemCommand();
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setItens(new ArrayList<>()); // Inicializa a lista de itens

        when(pedidoServiceGateway.adicionarItem(any())).thenReturn(pedido);

        mockMvc.perform(put("/api/pedidos/1/adicionar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testRemoverItem() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setItens(new ArrayList<>()); //
        when(pedidoServiceGateway.removerItem(any())).thenReturn(pedido);

        mockMvc.perform(delete("/api/pedidos/1/remover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testCheckoutPedido() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setItens(new ArrayList<>()); //

        when(pedidoServiceGateway.checkoutPedido(anyLong())).thenReturn(pedido);

        mockMvc.perform(put("/api/pedidos/1/checkout")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testCancelarPedido() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setItens(new ArrayList<>()); //

        when(pedidoServiceGateway.cancelarPedido(anyLong())).thenReturn(pedido);

        mockMvc.perform(put("/api/pedidos/1/cancelar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testEfetuarEntrega() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setItens(new ArrayList<>()); //

        when(pedidoServiceGateway.efetuarEntrega(anyLong())).thenReturn(pedido);

        mockMvc.perform(put("/api/pedidos/1/entrega")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testAtualizarPedidoPronto() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setItens(new ArrayList<>()); //
        when(pedidoServiceGateway.atualizarPedidoPronto(anyLong())).thenReturn(pedido);

        mockMvc.perform(put("/api/pedidos/1/atualizar-pedido-pronto")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}