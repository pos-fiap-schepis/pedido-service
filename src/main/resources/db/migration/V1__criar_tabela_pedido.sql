CREATE SEQUENCE sq_pedido
    INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE pedido (
                        id BIGSERIAL PRIMARY KEY,
                        cliente_id BIGINT NOT NULL,
                        valor DECIMAL(19, 2),
                        situacao_pagamento VARCHAR(40) NOT NULL,
                        status VARCHAR(20) NOT NULL,
                        data_hora_pagamento TIMESTAMP,
                        data_hora_criacao TIMESTAMP NOT NULL,
                        data_hora_entrega TIMESTAMP
);

COMMENT ON SEQUENCE sq_pedido IS 'Sequência usada para gerar IDs únicos para a tabela de pedidos';
COMMENT ON TABLE pedido IS 'Tabela que armazena informações dos pedidos';

COMMENT ON COLUMN pedido.id IS 'Identificador único para cada pedido';
COMMENT ON COLUMN pedido.cliente_id IS 'Identificador do cliente que fez o pedido';
COMMENT ON COLUMN pedido.valor IS 'Valor total do pedido';
COMMENT ON COLUMN pedido.situacao_pagamento IS 'Situação do pagamento do pedido';
COMMENT ON COLUMN pedido.status IS 'Status atual do pedido';
COMMENT ON COLUMN pedido.data_hora_pagamento IS 'Data e hora do pagamento do pedido';
COMMENT ON COLUMN pedido.data_hora_criacao IS 'Data e hora da criação do pedido';
COMMENT ON COLUMN pedido.data_hora_entrega IS 'Data e hora da entrega do pedido';