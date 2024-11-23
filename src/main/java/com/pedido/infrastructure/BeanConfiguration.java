package com.pedido.infrastructure;


import com.pedido.core.gateways.NotificacaoSonoraGateway;
import com.pedido.core.gateways.PagamentoServiceGateway;
import com.pedido.core.gateways.PagamentoServicoExternoGateway;
import com.pedido.core.gateways.PedidoRepositoryGateway;
import com.pedido.core.gateways.PedidoServiceGateway;
import com.pedido.core.gateways.ProdutoServicoExternoGateway;
import com.pedido.core.usecases.PagamentoUseCase;
import com.pedido.core.usecases.PedidoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {


    @Bean
    public PedidoServiceGateway pedidoServiceImpl(PedidoRepositoryGateway pedidoRepositoryGateway, ProdutoServicoExternoGateway produtoServiceExternoGateway,
                                                  PagamentoServicoExternoGateway pagamentoServicoExternoGateway, NotificacaoSonoraGateway notificacaoSonoraGateway) {
        return new PedidoUseCase(pedidoRepositoryGateway, produtoServiceExternoGateway, pagamentoServicoExternoGateway, notificacaoSonoraGateway);
    }

    @Bean
    public PagamentoServiceGateway pagamentoServiceImpl(PedidoRepositoryGateway pedidoRepositoryGateway){
        return new PagamentoUseCase(pedidoRepositoryGateway);
    }
}
