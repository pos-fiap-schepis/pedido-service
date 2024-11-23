package com.pedido;

import com.pedido.core.entities.Pedido;
import com.pedido.core.enums.SituacaoPagamentoEnum;
import com.pedido.core.enums.StatusPagamentoEnum;
import com.pedido.core.exceptions.BusinessException;
import com.pedido.core.gateways.PedidoRepositoryGateway;
import com.pedido.core.usecases.PagamentoUseCase;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static com.pedido.core.enums.SituacaoPagamentoEnum.PAGO;
import static com.pedido.core.enums.StatusPedidoEnum.ANDAMENTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PagamentoUseCaseTest {

    @Mock
    private PedidoRepositoryGateway pedidoRepositoryGateway;

    @InjectMocks
    private PagamentoUseCase pagamentoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAtualizarPedidoSePagamentoAprovado() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setSituacaoPagamento(SituacaoPagamentoEnum.PENDENTE);
        pedido.setValor(BigDecimal.TEN);

        when(pedidoRepositoryGateway.obterPorId(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepositoryGateway.salvar(any(Pedido.class))).thenReturn(pedido);

        Pedido result = pagamentoUseCase.atualizarPedidoSePagamentoAprovado(1L, StatusPagamentoEnum.A);

        assertNotNull(result);
        assertEquals(PAGO, result.getSituacaoPagamento());
        assertEquals(ANDAMENTO, result.getStatus());
        assertNotNull(result.getDataHoraPagamento());
        verify(pedidoRepositoryGateway, times(1)).salvar(pedido);
    }

    @Test
    void testAtualizarPedidoSePagamentoRecusado() {
        BusinessException exception = assertThrows(BusinessException.class, () ->
                pagamentoUseCase.atualizarPedidoSePagamentoAprovado(1L, StatusPagamentoEnum.R)
        );
        assertEquals("Pagamento não efetuado.. foi recusado pela operadora!", exception.getMessage());
    }

    @Test
    void testAtualizarPedidoSePagamentoJaEfetuado() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setSituacaoPagamento(PAGO);

        when(pedidoRepositoryGateway.obterPorId(1L)).thenReturn(Optional.of(pedido));

        BusinessException exception = assertThrows(BusinessException.class, () ->
                pagamentoUseCase.atualizarPedidoSePagamentoAprovado(1L, StatusPagamentoEnum.A)
        );
        assertEquals("Pagamento do pedido já foi efetuado!", exception.getMessage());
    }

    @Test
    void testObterSituacaoPagamento() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setSituacaoPagamento(PAGO);

        when(pedidoRepositoryGateway.obterPorId(1L)).thenReturn(Optional.of(pedido));

        String situacaoPagamento = pagamentoUseCase.obterSituacaoPagamento(1L);

        assertEquals(PAGO.getDescricao(), situacaoPagamento);
    }

    @Test
    void testObterSituacaoPagamentoPedidoNaoEncontrado() {
        when(pedidoRepositoryGateway.obterPorId(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () ->
                pagamentoUseCase.obterSituacaoPagamento(1L)
        );
        assertEquals("Pedido não encontrado.", exception.getMessage());
    }

    @Test
    void testObterSituacaoPagamentoSemStatus() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setSituacaoPagamento(null);

        when(pedidoRepositoryGateway.obterPorId(1L)).thenReturn(Optional.of(pedido));

        BusinessException exception = assertThrows(BusinessException.class, () ->
                pagamentoUseCase.obterSituacaoPagamento(1L)
        );
        assertEquals("Pedido não possui nenhum status de pagamento.", exception.getMessage());
    }
}
