package com.pedido.infrastructure.controllers;

import com.pedido.core.entities.Pedido;
import com.pedido.core.gateways.PagamentoServiceGateway;
import com.pedido.infrastructure.controllers.commands.RealizarPagamentoCommand;
import com.pedido.infrastructure.controllers.presenters.PagamentoPresenter;
import com.pedido.infrastructure.dtos.PagamentoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoServiceGateway pagamentoServiceGateway;

    @Operation(summary = "Webhook que recebe confirmação de pagamento se foi aprovado ou recusado através da enum StatusPagamentoEnum com o tipo A ou R")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento realizado com sucesso", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos", content = @Content),
            @ApiResponse(responseCode = "412", description = "Pagamento não efetuado!", content = @Content)})

    @PutMapping("/{id}")
    public PagamentoDto atualizarPedidoSePagamentoAprovado(@RequestBody RealizarPagamentoCommand command) {
        Pedido pedido = pagamentoServiceGateway.atualizarPedidoSePagamentoAprovado(command.getPedidoId(), command.getStatusPagamento());
        return PagamentoPresenter.converterPedidoToPagamentoDto(pedido);
    }

    @Operation(summary = "Obtem o status do pagamento do pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do pagamento obtido com sucesso", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Pedido.class))}),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos", content = @Content),
            @ApiResponse(responseCode = "412", description = "Status do pagamento não informado", content = @Content)})

    @GetMapping("/{id}/obter-status-pagamento")
    public String obterStatusPagamento(@PathVariable Long id) {
        return pagamentoServiceGateway.obterSituacaoPagamento(id);
    }
}
