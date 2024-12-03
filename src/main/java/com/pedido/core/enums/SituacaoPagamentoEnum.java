package com.pedido.core.enums;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SituacaoPagamentoEnum {

    PENDENTE("Pendente"),
    PAGO("Pago");

    private String descricao;
}
