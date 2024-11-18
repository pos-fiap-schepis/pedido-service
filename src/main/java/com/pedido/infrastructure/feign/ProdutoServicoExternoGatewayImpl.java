package com.pedido.infrastructure.feign;

import com.pedido.core.entities.Produto;
import com.pedido.core.gateways.ProdutoServicoExternoGateway;
import com.pedido.infrastructure.converters.ProdutoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoServicoExternoGatewayImpl implements ProdutoServicoExternoGateway {

    @Autowired
    private ProdutoServicoExterno produtoServicoExterno;

    @Override
    public Produto getProdutoById(Long id) {
        return ProdutoConverter.converterDtoToProduto(produtoServicoExterno.findById(id));
    }
}
