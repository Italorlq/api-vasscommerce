# API do VassCommerce

API REST de um pequeno e-commerce, feita com **Java 21**, **Spring Boot** e **PostgreSQL**.
Laboratório de Programação FullStack, Prof. Me. Tássio Auad.

## Como rodar

1. Abra o **Docker Desktop**.
2. Na pasta do projeto, suba o banco:
   ```
   docker compose up -d
   ```
3. Rode a aplicação (pelo IntelliJ, executando `ApiApplication`, ou pelo terminal):
   ```
   ./mvnw spring-boot:run
   ```
4. A API fica em `http://localhost:8080`.

Na primeira execução, as tabelas são criadas automaticamente e alguns dados de exemplo são inseridos.

## Rotas (todas GET, respondem em JSON)

| Rota | Descrição |
|---|---|
| `/categoria` | Lista todas as categorias |
| `/categoria?nome=Eletr` | Busca categorias por parte do nome |
| `/categoria/{id}/produto` | Lista os produtos de uma categoria |
| `/produto/{id}` | Dados de um produto |
| `/cliente/{id}` | Dados de um cliente |
| `/cliente/{id}/formas-de-pagamento` | Cartões do cliente (exceto os excluídos) |
| `/cliente/{id}/endereco` | Endereço do cliente |
| `/tipo-cartao` | Lista os tipos de cartão |
| `/estado` | Lista os estados |
| `/estado/{id}/cidade` | Lista as cidades de um estado |
| `/cliente/{id}/pedido` | Pedidos do cliente, com itens e pagamentos |

Quando o id não existe, a API responde `404 Not Found`.

## Tecnologias

- Spring Web, Spring Data JPA, Lombok
- PostgreSQL 16 (via Docker Compose)