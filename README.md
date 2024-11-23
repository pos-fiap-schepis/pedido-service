# Pedido Service

## Descrição
O Pedido Service é um serviço responsável por gerenciar pedidos em um sistema de fast food. Ele permite criar, atualizar, listar e excluir pedidos, além de gerenciar o status de pagamento dos pedidos.

## Requisitos
- Java 17
- Maven 3.6+
- Docker
- Docker Compose

## Configuração

### Banco de Dados
O serviço utiliza um banco de dados PostgreSQL. A configuração do banco de dados está definida no arquivo `docker-compose.yaml`.

### Variáveis de Ambiente
As seguintes variáveis de ambiente são utilizadas para configurar o banco de dados:
- `POSTGRES_PASSWORD`: Senha do usuário PostgreSQL
- `POSTGRES_USER`: Usuário PostgreSQL
- `POSTGRES_DB`: Nome do banco de dados

## Execução

### Docker Compose
Para iniciar o serviço e o banco de dados, execute o comando:
```sh
docker-compose up
```

### Maven
Para compilar e executar o serviço localmente, execute os seguintes comandos:
```sh
mvn clean install
mvn spring-boot:run
```

## Testes

### Testes Unitários
Para executar os testes unitários, utilize o comando:
```sh
mvn test
```

### Testes BDD com Cucumber
Para executar os testes BDD com Cucumber, utilize o comando:
```sh
mvn test
```

## Endpoints
A seguir estão os principais endpoints do serviço:

### Pedido
- `POST /api/pedidos`: Cria um novo pedido
- `PUT /api/pedidos/{id}`: Atualiza um pedido existente
- `GET /api/pedidos`: Lista todos os pedidos
- `DELETE /api/pedidos/{id}`: Exclui um pedido

### Pagamento
- `PUT /api/pagamentos/{id}`: Atualiza o status de pagamento de um pedido
- `GET /api/pagamentos/{id}/obter-status-pagamento`: Obtém o status de pagamento de um pedido

## Contribuição
Para contribuir com o projeto, siga os passos abaixo:
1. Faça um fork do repositório
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`)
4. Faça o push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request
```