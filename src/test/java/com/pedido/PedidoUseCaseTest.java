package com.pedido;

import com.pedido.core.entities.Cliente;
import com.pedido.core.entities.ItemPedido;
import com.pedido.core.entities.Pedido;
import com.pedido.core.entities.Produto;
import com.pedido.core.enums.StatusPedidoEnum;
import com.pedido.core.exceptions.BusinessException;
import com.pedido.core.gateways.*;
import com.pedido.core.usecases.PedidoUseCase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static com.pedido.core.enums.SituacaoPagamentoEnum.PENDENTE;
import static com.pedido.core.enums.StatusPedidoEnum.ANDAMENTO;
import static com.pedido.core.enums.StatusPedidoEnum.ENTREGUE;
import static com.pedido.core.enums.StatusPedidoEnum.PRONTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoUseCaseTest {

    @Mock
    private PedidoRepositoryGateway pedidoRepositoryGateway;

    @Mock
    private ProdutoServicoExternoGateway produtoServicoExternoGateway;

    @Mock
    private PagamentoServicoExternoGateway pagamentoServicoExternoGateway;

    @Mock
    private NotificacaoSonoraGateway notificacaoSonoraGateway;

    @Mock
    private ClienteServicoExternoGateway clienteServicoExternoGateway;

    @InjectMocks
    private PedidoUseCase pedidoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvar() {
        Pedido pedido = new Pedido();
        pedido.setClienteId(1L);
        when(pedidoRepositoryGateway.salvar(any(Pedido.class))).thenReturn(pedido);

        pedido.setClienteId(1L);
        Cliente cliente = new Cliente(1L, "Nome", "", "");

        when(clienteServicoExternoGateway.getClienteById(1L)).thenReturn(cliente);
        when(pedidoRepositoryGateway.salvar(any(Pedido.class))).thenReturn(pedido);

        Pedido result = pedidoUseCase.salvar(pedido);

        assertNotNull(result);
        verify(pedidoRepositoryGateway, times(1)).salvar(pedido);
    }

    @Test
    void testAdicionarItem() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setValor(BigDecimal.ZERO);
        pedido.setItens(new ArrayList<>()); // Inicializa a lista de itens
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPedidoId(1L);
        itemPedido.setProdutoId("1L");
        itemPedido.setQuantidade(1);
        Produto produto = new Produto();
        produto.setId("1L");
        produto.setValor(BigDecimal.TEN);

        when(pedidoRepositoryGateway.obterPorId(1L)).thenReturn(Optional.of(pedido));
        when(produtoServicoExternoGateway.getProdutoById("1L")).thenReturn(produto);
        when(pedidoRepositoryGateway.salvar(any(Pedido.class))).thenReturn(pedido);

        Pedido result = pedidoUseCase.adicionarItem(itemPedido);

        assertNotNull(result);
        assertEquals(BigDecimal.TEN, result.getValor());
        verify(pedidoRepositoryGateway, times(1)).salvar(pedido);
    }

    @Test
    void testRemoverItem() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setValor(BigDecimal.TEN);
        pedido.setItens(new ArrayList<>()); // Inicializa a lista de itens
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPedidoId(1L);
        itemPedido.setProdutoId("1L");
        itemPedido.setQuantidade(1);
        Produto produto = new Produto();
        produto.setId("1L");
        produto.setValor(BigDecimal.TEN);

        // Adiciona o item ao pedido antes de tentar removê-lo
        pedido.adicionarItemPedido(itemPedido);

        when(pedidoRepositoryGateway.obterPorId(1L)).thenReturn(Optional.of(pedido));
        when(produtoServicoExternoGateway.getProdutoById("1L")).thenReturn(produto);
        when(pedidoRepositoryGateway.salvar(any(Pedido.class))).thenReturn(pedido);

        Pedido result = pedidoUseCase.removerItem(itemPedido);

        assertNotNull(result);
        assertEquals(BigDecimal.TEN, result.getValor());
        verify(pedidoRepositoryGateway, times(1)).salvar(pedido);
    }

    @Test
    void testObterPedidos() {
        // Arrange
        Pedido pedido1 = new Pedido();
        pedido1.setStatus(PRONTO);
        Pedido pedido2 = new Pedido();
        pedido2.setStatus(ANDAMENTO);
        Pedido pedido3 = new Pedido();
        pedido3.setStatus(ENTREGUE);

        when(pedidoRepositoryGateway.obterPedidos(Arrays.asList(PRONTO, ANDAMENTO, ENTREGUE)))
                .thenReturn(Arrays.asList(pedido1, pedido2, pedido3));

        // Act
        List<Pedido> pedidos = pedidoUseCase.obterPedidos();

        // Assert
        assertThat(pedidos).hasSize(3);
        assertThat(pedidos.get(0).getStatus()).isEqualTo(PRONTO);
        assertThat(pedidos.get(1).getStatus()).isEqualTo(ANDAMENTO);
        assertThat(pedidos.get(2).getStatus()).isEqualTo(ENTREGUE);
    }

    @Test
    void testCancelarPedido() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(StatusPedidoEnum.INICIADO);

        when(pedidoRepositoryGateway.obterPorId(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepositoryGateway.salvar(any(Pedido.class))).thenReturn(pedido);

        Pedido result = pedidoUseCase.cancelarPedido(1L);

        assertNotNull(result);
        assertEquals(StatusPedidoEnum.CANCELADO, result.getStatus());
        verify(pedidoRepositoryGateway, times(1)).salvar(pedido);
    }

    @Test
    void testCheckoutPedido() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setValor(BigDecimal.TEN);

        when(pedidoRepositoryGateway.obterPorId(1L)).thenReturn(Optional.of(pedido));
        when(pagamentoServicoExternoGateway.efetuarPagamento(anyString(), anyLong(), any(BigDecimal.class))).thenReturn(true);
        when(pedidoRepositoryGateway.salvar(any(Pedido.class))).thenReturn(pedido);

        Pedido result = pedidoUseCase.checkoutPedido(1L);

        assertNotNull(result);
        verify(pagamentoServicoExternoGateway, times(1)).efetuarPagamento(anyString(), anyLong(), any());
    }

    @Test
    void testEfetuarEntrega() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(StatusPedidoEnum.PRONTO);
        pedido.setSituacaoPagamento(PENDENTE);

        when(pedidoRepositoryGateway.obterPorId(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepositoryGateway.salvar(any(Pedido.class))).thenReturn(pedido);

        BusinessException exception = assertThrows(BusinessException.class, () -> pedidoUseCase.efetuarEntrega(1L));
        assertEquals("Pagamento esta pendente!", exception.getMessage());
    }

    @Test
    void testAtualizarPedidoPronto() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setStatus(StatusPedidoEnum.ANDAMENTO);

        when(pedidoRepositoryGateway.obterPorId(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepositoryGateway.salvar(any(Pedido.class))).thenReturn(pedido);

        Pedido result = pedidoUseCase.atualizarPedidoPronto(1L);

        assertNotNull(result);
        assertEquals(StatusPedidoEnum.PRONTO, result.getStatus());
        verify(notificacaoSonoraGateway, times(1)).notificar();
        verify(pedidoRepositoryGateway, times(1)).salvar(pedido);
    }
}