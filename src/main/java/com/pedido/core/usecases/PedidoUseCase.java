package com.pedido.core.usecases;

import com.pedido.core.entities.ItemPedido;
import com.pedido.core.entities.Pedido;
import com.pedido.core.entities.Produto;
import com.pedido.core.enums.StatusPedidoEnum;
import com.pedido.core.exceptions.BusinessException;
import com.pedido.core.gateways.NotificacaoSonoraGateway;
import com.pedido.core.gateways.PagamentoServicoExternoGateway;
import com.pedido.core.gateways.PedidoRepositoryGateway;
import com.pedido.core.gateways.PedidoServiceGateway;
import com.pedido.core.gateways.ProdutoServicoExternoGateway;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.web.client.HttpClientErrorException;
import static com.pedido.core.enums.SituacaoPagamentoEnum.PENDENTE;
import static com.pedido.core.enums.StatusPedidoEnum.ANDAMENTO;
import static com.pedido.core.enums.StatusPedidoEnum.CANCELADO;
import static com.pedido.core.enums.StatusPedidoEnum.ENTREGUE;
import static com.pedido.core.enums.StatusPedidoEnum.INICIADO;
import static com.pedido.core.enums.StatusPedidoEnum.PRONTO;
import static java.util.Objects.isNull;

public class PedidoUseCase implements PedidoServiceGateway {

    private final PedidoRepositoryGateway pedidoRepositoryGateway;

    private final ProdutoServicoExternoGateway produtoServicoExternoGateway;

    private final PagamentoServicoExternoGateway pagamentoServicoExternoGateway;

    private final NotificacaoSonoraGateway notificacaoSonoraGateway;

    public PedidoUseCase(PedidoRepositoryGateway pedidoRepositoryGateway, ProdutoServicoExternoGateway produtoServicoExternoGateway,
                         PagamentoServicoExternoGateway pagamentoServicoExterno, NotificacaoSonoraGateway notificacaoSonoraGateway) {
        this.pedidoRepositoryGateway = pedidoRepositoryGateway;
        this.produtoServicoExternoGateway = produtoServicoExternoGateway;
        this.pagamentoServicoExternoGateway = pagamentoServicoExterno;
        this.notificacaoSonoraGateway = notificacaoSonoraGateway;
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        if (pedido.getClienteId() == null) {
            throw new BusinessException("O cliente é obrigatório!");
        }
        iniciarPedido(pedido);
        return pedidoRepositoryGateway.salvar(pedido);
    }

    @Override
    public List<Pedido> obterPedidos() {
        List<Pedido> pedidos = new ArrayList<>(pedidoRepositoryGateway.obterPedidos(List.of(PRONTO, ANDAMENTO, ENTREGUE)));

        Map<StatusPedidoEnum, Integer> statusOrdem = new HashMap<>();
        statusOrdem.put(PRONTO, 1);
        statusOrdem.put(ANDAMENTO, 2);
        statusOrdem.put(ENTREGUE, 3);

        pedidos.sort(Comparator.comparing(pedido -> statusOrdem.getOrDefault(pedido.getStatus(), Integer.MAX_VALUE)));

        return pedidos;
    }

    @Override
    public Pedido adicionarItem(ItemPedido itemPedido) {
        Pedido pedido = obterPedido(itemPedido.getPedidoId());
        Produto produto = obterProduto(itemPedido);

        if (isNull(produto)) {
            throw new BusinessException("Produto não encontrado!");
        }

        ItemPedido itemJaInclusoNoPedido = obterItemJaInclusoPedido(itemPedido, pedido);
        if (Objects.nonNull(itemJaInclusoNoPedido) && itemJaInclusoNoPedido.getProdutoId().equals(itemPedido.getProdutoId())) {
            itemJaInclusoNoPedido.setQuantidade(itemJaInclusoNoPedido.getQuantidade() + itemPedido.getQuantidade());
        } else {
            pedido.adicionarItemPedido(itemPedido);
        }

        pedido.setValor(pedido.getValor().add(produto.getValor().multiply(new BigDecimal(itemPedido.getQuantidade()))));
        return pedidoRepositoryGateway.salvar(pedido);
    }

    private static ItemPedido obterItemJaInclusoPedido(ItemPedido itemPedido, Pedido pedido) {
        return pedido.getItens().stream().filter(item -> item.getProdutoId().equals(itemPedido.getProdutoId())).findFirst()
                .orElse(null);
    }

    @Override
    public Pedido removerItem(ItemPedido itemPedido) {
        Pedido pedido = obterPedido(itemPedido.getPedidoId());
        Produto produto = obterProduto(itemPedido);

        if (isNull(produto)) {
            throw new BusinessException("Produto não encontrado!");
        }

        ItemPedido itemDoPedido = obterItemJaInclusoPedido(itemPedido, pedido);

        if (itemDoPedido.getQuantidade().equals(itemPedido.getQuantidade())) {
            pedido.removerItemPedido(itemPedido);
        }

        itemDoPedido.setQuantidade(itemDoPedido.getQuantidade() - itemPedido.getQuantidade());
        pedido.setValor(pedido.getValor().subtract(produto.getValor().multiply(new BigDecimal(itemPedido.getQuantidade()))));

        return pedidoRepositoryGateway.salvar(pedido);
    }

    private Produto obterProduto(ItemPedido itemPedido) {
        return produtoServicoExternoGateway.getProdutoById(itemPedido.getProdutoId());
    }

    private Pedido obterPedido(Long id) {
        return pedidoRepositoryGateway.obterPorId(id).orElseThrow(() -> new BusinessException("Pedido não encontrado!"));
    }

    @Override
    public Pedido cancelarPedido(Long id) {
        Pedido pedido = obterPedido(id);

        return cancelarPedido(pedido);
    }

    @Override
    public Pedido checkoutPedido(Long id) {
        Pedido pedido = obterPedido(id);

        try {
            boolean pagamentoEfetuado = pagamentoServicoExternoGateway.efetuarPagamento("QrCode", id, pedido.getValor());

            if (!pagamentoEfetuado) {
                throw new BusinessException("Pedido não realizado, houve erro no pagamento!");
            }
        } catch (HttpClientErrorException e) {
            throw new BusinessException(e.getMessage());
        }

        return obterPedido(id);
    }

    @Override
    public Pedido efetuarEntrega(Long id) {
        Pedido pedido = obterPedido(id);

        if (!pedido.getStatus().equals(PRONTO)) {
            throw new BusinessException("Pedido não esta pronto para entrega!");
        }

        if (pedido.getSituacaoPagamento().equals(PENDENTE)) {
            throw new BusinessException("Pagamento esta pendente!");
        }

        pedido.setStatus(ENTREGUE);
        pedido.setDataHoraEntrega(LocalDateTime.now());

        return pedidoRepositoryGateway.salvar(pedido);
    }

    @Override
    public Pedido atualizarPedidoPronto(Long id) {
        Pedido pedido = obterPedido(id);

        if (!pedido.getStatus().equals(ANDAMENTO)) {
            throw new BusinessException("Pedido não está com status em andamento");
        }

        pedido.setStatus(PRONTO);
        notificacaoSonoraGateway.notificar();
        return pedidoRepositoryGateway.salvar(pedido);
    }

    private Pedido cancelarPedido(Pedido pedido) {
        if (pedido.getStatus().equals(CANCELADO)) {
            throw new BusinessException("Pedido já foi cancelado!");
        }

        if (pedido.getStatus().equals(ENTREGUE)) {
            throw new BusinessException("Pedido já foi entregue!");
        }

        pedido.setStatus(CANCELADO);
        return pedidoRepositoryGateway.salvar(pedido);
    }

    private static void iniciarPedido(Pedido pedido) {
        pedido.setDataHoraCriacao(LocalDateTime.now());
        pedido.setSituacaoPagamento(PENDENTE);
        pedido.setStatus(INICIADO);
    }
}
