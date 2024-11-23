package com.pedido;

import com.pedido.core.entities.Pedido;
import com.pedido.core.enums.SituacaoPagamentoEnum;
import com.pedido.core.enums.StatusPagamentoEnum;
import com.pedido.core.gateways.PagamentoServiceGateway;
import com.pedido.infrastructure.controllers.PagamentoController;
import com.pedido.infrastructure.controllers.commands.RealizarPagamentoCommand;
import java.util.ArrayList;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PagamentoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PagamentoServiceGateway pagamentoServiceGateway;

    @InjectMocks
    private PagamentoController pagamentoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController).build();
    }

    @Test
    void testAtualizarPedidoSePagamentoAprovado() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setSituacaoPagamento(SituacaoPagamentoEnum.PAGO);
        pedido.setItens(new ArrayList<>()); // Inicializa a lista de itens

        when(pagamentoServiceGateway.atualizarPedidoSePagamentoAprovado(anyLong(), any(StatusPagamentoEnum.class))).thenReturn(pedido);

        mockMvc.perform(put("/api/pagamentos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"pedidoId\":1,\"statusPagamento\":\"A\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.situacaoPagamento").value("Pago"));
    }

    @Test
    void testObterStatusPagamento() throws Exception {
        when(pagamentoServiceGateway.obterSituacaoPagamento(anyLong())).thenReturn("APROVADO");

        mockMvc.perform(get("/api/pagamentos/1/obter-status-pagamento")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("APROVADO"));
    }
}