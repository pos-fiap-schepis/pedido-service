package com.pedido.infrastructure.converters;

import com.pedido.core.entities.ItemPedido;
import com.pedido.core.entities.Pedido;
import com.pedido.infrastructure.controllers.commands.AdicionarItemCommand;
import com.pedido.infrastructure.controllers.commands.CriarPedidoCommand;
import com.pedido.infrastructure.controllers.commands.RemoverItemCommand;
import com.pedido.infrastructure.feign.ProdutoServicoExterno;
import com.pedido.infrastructure.repositories.pedido.ItemPedidoEntity;
import com.pedido.infrastructure.repositories.pedido.PedidoEntity;
import com.pedido.infrastructure.repositories.pedido.PedidoRepository;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PedidoConverter {

    private PedidoRepository pedidoRepository;

    private ProdutoServicoExterno produtoServicoExterno;

    public PedidoConverter(PedidoRepository pedidoRepository, ProdutoServicoExterno produtoServicoExterno) {
        this.pedidoRepository = pedidoRepository;
        this.produtoServicoExterno = produtoServicoExterno;
    }

    public static ItemPedido converterAdicionarItemCommandToItemPedido(AdicionarItemCommand adicionarItemCommand, Long pedidoId) {
        return new ItemPedido.Builder()
                .produto(adicionarItemCommand.getProdutoId())
                .pedido(pedidoId)
                .quantidade(adicionarItemCommand.getQuantidade())
                .build();
    }

    public static ItemPedido converterRemoverItemCommandToItemPedido(RemoverItemCommand removerItemCommand, Long pedidoId) {
        return new ItemPedido.Builder()
                .produto(removerItemCommand.getProdutoId())
                .pedido(pedidoId)
                .quantidade(removerItemCommand.getQuantidade())
                .build();
    }

    public static Pedido converterCommandToPedido(CriarPedidoCommand command) {
        return new Pedido.Builder()
                .clienteId(command.getClienteId())
                .valor(BigDecimal.ZERO)
                .build();
    }

    public PedidoEntity converterPedidoToEntity(Pedido pedido) {
        return PedidoEntity.builder()
                .id(pedido.getId())
                .clienteId(pedido.getClienteId())
                .valor(pedido.getValor())
                .situacaoPagamento(pedido.getSituacaoPagamento())
                .status(pedido.getStatus())
                .dataHoraPagamento(pedido.getDataHoraPagamento())
                .dataHoraCriacao(pedido.getDataHoraCriacao())
                .dataHoraEntrega(pedido.getDataHoraEntrega())
                .itens(pedido.getItens().stream().map(item -> ItemPedidoEntity.builder()
                        .id(item.getId())
                        .produto(produtoServicoExterno.findById(item.getProdutoId()).getId())
                        .pedido(pedidoRepository.findById(item.getPedidoId()).get())
                        .quantidade(item.getQuantidade())
                        .build()).toList())
                .build();
    }

    public static Pedido converterPedidoEntityToPedido(PedidoEntity pedido) {
        return new Pedido.Builder()
                .id(pedido.getId())
                .clienteId(pedido.getClienteId())
                .valor(pedido.getValor())
                .situacaoPagamento(pedido.getSituacaoPagamento())
                .status(pedido.getStatus())
                .dataHoraPagamento(pedido.getDataHoraPagamento())
                .dataHoraCriacao(pedido.getDataHoraCriacao())
                .dataHoraEntrega(pedido.getDataHoraEntrega())
                .itens(pedido.getItens().stream().map(item ->
                                new ItemPedido(item.getId(), item.getProduto(), item.getPedido().getId(), item.getQuantidade()))
                        .collect(Collectors.toList()))
                .build();
    }
}
