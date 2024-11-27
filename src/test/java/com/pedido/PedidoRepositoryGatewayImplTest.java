package com.pedido;

import com.pedido.core.entities.Pedido;
import com.pedido.core.enums.StatusPedidoEnum;
import com.pedido.infrastructure.converters.PedidoConverter;
import com.pedido.infrastructure.repositories.pedido.PedidoEntity;
import com.pedido.infrastructure.repositories.pedido.PedidoRepository;
import com.pedido.infrastructure.repositories.pedido.PedidoRepositoryGatewayImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoRepositoryGatewayImplTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PedidoConverter pedidoConverter;

    @InjectMocks
    private PedidoRepositoryGatewayImpl pedidoRepositoryGatewayImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvar() {
        Pedido pedido = new Pedido();
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setItens(List.of());
        when(pedidoConverter.converterPedidoToEntity(any(Pedido.class))).thenReturn(pedidoEntity);
        when(pedidoRepository.save(any(PedidoEntity.class))).thenReturn(pedidoEntity);

        Pedido result = pedidoRepositoryGatewayImpl.salvar(pedido);

        assertEquals(pedido.getId(), result.getId());
        verify(pedidoRepository, times(1)).save(pedidoEntity);
    }

    @Test
    void testObterPedidos() {
        List<StatusPedidoEnum> statusList = List.of(StatusPedidoEnum.ANDAMENTO);
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setItens(List.of());
        List<PedidoEntity> pedidoEntities = List.of();

        when(pedidoRepository.findByStatus(any())).thenReturn(pedidoEntities);

        pedidoRepositoryGatewayImpl.obterPedidos(statusList);

        verify(pedidoRepository, times(1)).findByStatus(statusList);
    }

    @Test
    void testObterPorId() {
        Long id = 1L;
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(id);
        pedidoEntity.setItens(List.of());
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(pedidoEntity));

        Optional<Pedido> result = pedidoRepositoryGatewayImpl.obterPorId(id);

        assertEquals(Optional.of(pedido).get().getId(), result.get().getId());
        verify(pedidoRepository, times(1)).findById(id);
    }
}
