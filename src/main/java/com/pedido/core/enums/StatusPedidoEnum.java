package com.pedido.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPedidoEnum {

    INICIADO("Iniciado"),
    ANDAMENTO("Andamento"),
    PRONTO("Pronto"),
    CANCELADO("Cancelado"),
    CONCLUIDO("Concluído"),
    ENTREGUE("Entregue");

    private String descricao;
}
