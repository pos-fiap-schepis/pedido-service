CREATE SEQUENCE sq_item_pedido
    INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE item_pedido
(
    id         BIGINT PRIMARY KEY,
    id_produto BIGINT  NOT NULL,
    id_pedido  BIGINT  NOT NULL,
    quantidade INTEGER NOT NULL
);

ALTER TABLE item_pedido ADD CONSTRAINT fk_item_pedido_pedido FOREIGN KEY (id_pedido) REFERENCES fastfood.pedido (id);

COMMENT ON COLUMN item_pedido.id IS 'Identificador único para cada item do pedido';
COMMENT ON COLUMN item_pedido.id_produto IS 'Identificador do produto no item do pedido';
COMMENT ON COLUMN item_pedido.id_pedido IS 'Identificador do pedido ao qual o item pertence';
COMMENT ON COLUMN item_pedido.quantidade IS 'Quantidade do produto no item do pedido';

COMMENT ON TABLE item_pedido IS 'Tabela que armazena informações dos itens dos pedidos';

COMMENT ON TABLE sq_item_pedido IS 'Sequência usada para gerar IDs únicos para a tabela de itens dos pedidos';