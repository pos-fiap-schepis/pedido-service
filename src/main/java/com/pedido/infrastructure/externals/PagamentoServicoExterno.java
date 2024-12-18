package com.pedido.infrastructure.externals;

import com.pedido.core.enums.SituacaoPagamentoEnum;
import com.pedido.core.enums.StatusPagamentoEnum;
import com.pedido.core.exceptions.BusinessException;
import com.pedido.core.gateways.PagamentoServicoExternoGateway;
import com.pedido.infrastructure.controllers.commands.RealizarPagamentoCommand;
import com.pedido.infrastructure.dtos.PagamentoDto;
import java.math.BigDecimal;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PagamentoServicoExterno implements PagamentoServicoExternoGateway {

    @Value("${context.url}")
    private String url;

    @Override
    public Boolean efetuarPagamento(String qrcode, Long idPedido, BigDecimal valor) {
        try {
            //SIMULAÇÃO ENDPOINT EXTERNO ENVIANDO O ID DO PEDIDO E O STATUS DO PAGAMENTO
            // SUPOSIÇÃO DE CHAMADA EM ENDPOINT EXTERNO DEVOLVENDO O STATUS DE PAGAMENTO COMO APROVADO
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            //TODO Mudar url de localhost para o ip do cluster
            HttpEntity<RealizarPagamentoCommand> requestEntity = new HttpEntity<>(new RealizarPagamentoCommand(idPedido, StatusPagamentoEnum.A), headers);
            ResponseEntity<PagamentoDto> responseEntity = restTemplate.exchange(url + "/api/pagamentos/" + idPedido, HttpMethod.PUT, requestEntity, PagamentoDto.class);

            return Objects.requireNonNull(responseEntity.getBody()).situacaoPagamento().equals(SituacaoPagamentoEnum.PAGO.getDescricao());
        } catch (Exception e) {
            throw new BusinessException("Erro ao efetuar pagamento, provavelmente o ip do cluster está errado");
        }
    }
}
