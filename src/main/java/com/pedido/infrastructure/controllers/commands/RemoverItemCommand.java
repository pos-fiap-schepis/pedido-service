package com.pedido.infrastructure.controllers.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoverItemCommand {
    private String produtoId;
    private Integer quantidade;
}
