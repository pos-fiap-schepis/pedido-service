package com.pedido.infrastructure.repositories.pedido;

import com.pedido.core.entities.Pedido;
import com.pedido.core.enums.StatusPedidoEnum;
import com.pedido.core.gateways.PedidoRepositoryGateway;
import com.pedido.infrastructure.converters.PedidoConverter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PedidoRepositoryGatewayImpl implements PedidoRepositoryGateway {

    private final PedidoRepository pedidoRepository;
    private final PedidoConverter pedidoConverter;

    @Override
    public Pedido salvar(Pedido pedido) {
        var novoPedido = pedidoRepository.save(pedidoConverter.converterPedidoToEntity(pedido));
        return PedidoConverter.converterPedidoEntityToPedido(novoPedido);
    }

    @Override
    public List<Pedido> obterPedidos(List<StatusPedidoEnum> statusList) {
        return pedidoRepository.findByStatus(statusList)
                .stream()
                .map(PedidoConverter::converterPedidoEntityToPedido)
                .toList();
    }

    @Override
    @Transactional
    public Optional<Pedido> obterPorId(Long id) {
        return pedidoRepository.findById(id)
                .map(PedidoConverter::converterPedidoEntityToPedido);
    }
}
