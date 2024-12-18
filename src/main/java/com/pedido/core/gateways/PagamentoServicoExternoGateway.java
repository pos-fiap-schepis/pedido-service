package com.pedido.core.gateways;

import java.math.BigDecimal;

public interface PagamentoServicoExternoGateway {
    Boolean efetuarPagamento(String qrcode, Long idPedido, BigDecimal valor);
}
