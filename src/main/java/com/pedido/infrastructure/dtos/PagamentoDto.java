package com.pedido.infrastructure.dtos;

import lombok.Builder;

@Builder
public record PagamentoDto(Long id,
                           String situacaoPagamento) {
}