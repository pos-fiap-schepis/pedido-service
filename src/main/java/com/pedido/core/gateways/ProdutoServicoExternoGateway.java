package com.pedido.core.gateways;

import com.pedido.core.entities.Produto;

public interface ProdutoServicoExternoGateway {

    Produto getProdutoById(Long id);

}
