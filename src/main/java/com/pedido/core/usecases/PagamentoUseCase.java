package com.pedido.core.usecases;

import com.pedido.core.entities.Pedido;
import com.pedido.core.enums.StatusPagamentoEnum;
import com.pedido.core.exceptions.BusinessException;
import com.pedido.core.gateways.PagamentoServiceGateway;
import com.pedido.core.gateways.PedidoRepositoryGateway;
import java.time.LocalDateTime;
import static com.pedido.core.enums.SituacaoPagamentoEnum.PAGO;
import static com.pedido.core.enums.StatusPedidoEnum.ANDAMENTO;
import static java.util.Objects.isNull;

public class PagamentoUseCase implements PagamentoServiceGateway {

    private final PedidoRepositoryGateway pedidoRepositoryGateway;

    public PagamentoUseCase(PedidoRepositoryGateway pedidoRepositoryGateway) {
        this.pedidoRepositoryGateway = pedidoRepositoryGateway;
    }

    @Override
    public Pedido atualizarPedidoSePagamentoAprovado(Long id, StatusPagamentoEnum pagamentoEnum) {
        if (pagamentoEnum.equals(StatusPagamentoEnum.R)) {
            throw new BusinessException("Pagamento não efetuado.. foi recusado pela operadora!");
        }

        Pedido pedido = pedidoRepositoryGateway.obterPorId(id).orElseThrow(() -> new BusinessException("Pedido não encontrado."));

        if (pedido.getSituacaoPagamento().equals(PAGO)) {
            throw new BusinessException("Pagamento do pedido já foi efetuado!");
        }

        pedido.setSituacaoPagamento(PAGO);
        pedido.setStatus(ANDAMENTO);
        pedido.setDataHoraPagamento(LocalDateTime.now());
        return pedidoRepositoryGateway.salvar(pedido);
    }

    @Override
    public String obterSituacaoPagamento(Long idPedido) {

        Pedido pedido = pedidoRepositoryGateway.obterPorId(idPedido).orElseThrow(() -> new BusinessException("Pedido não encontrado."));

        if (isNull(pedido.getSituacaoPagamento())) {
            throw new BusinessException("Pedido não possui nenhum status de pagamento.");
        }

        return pedido.getSituacaoPagamento().getDescricao();
    }
}
