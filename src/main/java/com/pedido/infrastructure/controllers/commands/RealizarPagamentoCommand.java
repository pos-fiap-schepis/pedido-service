package com.pedido.infrastructure.controllers.commands;

import com.pedido.core.enums.StatusPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RealizarPagamentoCommand {

    private Long pedidoId;
    private StatusPagamentoEnum statusPagamento;

}
