package com.pedido.infrastructure.externals;

import com.pedido.core.gateways.NotificacaoSonoraGateway;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoSonoraServicoExterno implements NotificacaoSonoraGateway {

    @Override
    public void notificar() {
        System.out.println("Notificação sonora enviada");
    }

}
