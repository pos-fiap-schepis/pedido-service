package com.pedido.infrastructure.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pedido.core.enums.SituacaoPagamentoEnum;
import com.pedido.core.enums.StatusPedidoEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDto(
        Long id,
        Long clienteId,
        BigDecimal valor,
        SituacaoPagamentoEnum situacaoPagamento,
        StatusPedidoEnum status,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy: HH:mm")
        LocalDateTime dataHoraPagamento,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy: HH:mm")
        LocalDateTime dataHoraCriacao,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy: HH:mm")
        LocalDateTime dataHoraEntrega,
        List<ItemPedidoDto> itens) {

}
