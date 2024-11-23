Feature: Criação de Pedido

  Scenario: Criar um pedido com sucesso
    Given um pedido válido
    When o pedido é salvo
    Then o pedido deve ser salvo com sucesso

  Scenario: Criar um pedido sem cliente
    Given um pedido sem cliente
    When o pedido é salvo
    Then deve ocorrer um erro indicando que o cliente é obrigatório