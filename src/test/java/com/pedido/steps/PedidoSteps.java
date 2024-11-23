package com.pedido.steps;

import com.pedido.core.entities.Pedido;
import com.pedido.core.exceptions.BusinessException;
import com.pedido.core.gateways.PedidoRepositoryGateway;
import com.pedido.core.usecases.PedidoUseCase;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PedidoSteps {

    @Mock
    private PedidoRepositoryGateway pedidoRepositoryGateway;

    @InjectMocks
    private PedidoUseCase pedidoUseCase;

    private Pedido pedido;
    private Exception exception;

    @Before("")
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("um pedido válido")
    public void umPedidoValido() {
        pedido = new Pedido();
        pedido.setClienteId(1L);
        // Adicione outros atributos necessários
    }

    @Given("um pedido sem cliente")
    public void umPedidoSemCliente() {
        pedido = new Pedido();
        // Adicione outros atributos necessários
    }

    @When("o pedido é salvo")
    public void oPedidoESalvo() {
        try {
            when(pedidoRepositoryGateway.salvar(any(Pedido.class))).thenReturn(pedido);
            pedidoUseCase.salvar(pedido);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("o pedido deve ser salvo com sucesso")
    public void oPedidoDeveSerSalvoComSucesso() {
        Assertions.assertNotNull(pedido);
        Assertions.assertNull(exception);
    }

    @Then("deve ocorrer um erro indicando que o cliente é obrigatório")
    public void deveOcorrerUmErroIndicandoQueOClienteEObrigatorio() {
        Assertions.assertNotNull(exception);
        Assertions.assertTrue(exception instanceof BusinessException);
        Assertions.assertEquals("O cliente é obrigatório!", exception.getMessage());
    }
}