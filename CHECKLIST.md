# CHECKLIST - IoC/DI, Controllers x Models

API do VassCommerce | Laboratório de Programação FullStack | Prof. Me. Tássio Auad

- [x] Controladores **não** fazem `new` de implementações; dependem de interfaces.
  `ProdutoController` recebe apenas `ProdutoModel` (interface).
- [x] Há ao menos **duas** implementações `@Service` de `ProdutoModel`:
  `ProdutoModelMemoria` (lista em memória) e `ProdutoModelSql` (PostgreSQL via JPA).
- [x] A seleção é feita por **`@Profile`**: `mem` ativa a memória e `sql` ativa o PostgreSQL.
  A troca é feita em `application.properties` (`spring.profiles.active=mem` ou `sql`).
- [x] Injeção é **por construtor**; nenhum `@Autowired` em campo.
- [x] `ProdutoController` continua retornando **201 / 200 / 404** corretos
  (`POST` -> 201 com `Location`, `GET /{id}` -> 200 ou 404, `GET` lista -> 200).
- [x] Validação em `ProdutoCreateRequest` continua ativa (`@Valid` + Bean Validation).
- [x] JSON de erro **400** continua padronizado via `@RestControllerAdvice`
  (`ApiExceptionHandler`, formato `ApiErro`).

## Como comprovar a troca de implementação

1. Em `application.properties`, deixe `spring.profiles.active=mem` e rode a aplicação.
   Crie um produto com `POST /produto`: o `id` começa em 1 e o produto some quando a aplicação para.
2. Troque para `spring.profiles.active=sql` e rode de novo.
   O produto é gravado no PostgreSQL, e o `id` vem do banco (continua depois de reiniciar).
3. O controller não muda em nada entre os dois testes.

As requisições de teste estão em `requests.http` e em `postman`.
