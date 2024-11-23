package com.pedido.infrastructure.controllers.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdicionarItemCommand {
    private String produtoId;
    private Integer quantidade;
}
