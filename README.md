# Biblioteca CloudTech Solutions - API REST

API REST desenvolvida em Java com Spring Boot para o gerenciamento do acervo de livros da Biblioteca CloudTech Solutions. A aplicação permite cadastrar, listar, consultar e alterar livros por meio de requisições HTTP, substituindo o controle manual do acervo.

O projeto também aplica práticas de DevOps: versionamento com Git e GitHub, testes automatizados com JUnit 5 e MockMvc, integração contínua com GitHub Actions e publicação na plataforma Railway.

> **Aviso:** os dados são armazenados em memória (em uma `ArrayList`). Não há banco de dados, portanto todos os livros cadastrados são perdidos quando a aplicação é reiniciada, e a aplicação sempre inicia com a lista vazia.

## Tecnologias

- Java 21
- Spring Boot 3.5.16 (spring-boot-starter-web)
- Maven
- JUnit 5 e MockMvc (spring-boot-starter-test)
- Git e GitHub
- GitHub Actions
- Railway

## Estrutura do projeto

```
cloudtech-library-api/
|-- .github/
|   `-- workflows/
|       `-- pipeline.yml                  -> workflow de integração contínua
|-- src/
|   |-- main/
|   |   |-- java/com/cloudtech/biblioteca/
|   |   |   |-- BibliotecaApplication.java -> classe principal da aplicação
|   |   |   |-- controller/
|   |   |   |   `-- LivroController.java   -> endpoints da API
|   |   |   `-- models/
|   |   |       `-- Livro.java             -> modelo do livro
|   |   `-- resources/
|   |       `-- application.properties     -> configuração da porta
|   `-- test/
|       `-- java/com/cloudtech/biblioteca/
|           `-- LivroControllerTest.java   -> testes com JUnit 5 e MockMvc
|-- .gitignore
|-- pom.xml                                -> dependências e build do projeto
`-- README.md
```

### Modelo Livro

| Atributo    | Tipo   | Descrição                                   |
|-------------|--------|---------------------------------------------|
| `id`        | Long   | Código de identificação, gerado automaticamente |
| `titulo`    | String | Título do livro                             |
| `autor`     | String | Nome do autor                               |
| `editora`   | String | Editora responsável pela publicação         |
| `ano`       | int    | Ano de publicação                           |
| `categoria` | String | Categoria do livro                          |
| `status`    | String | Situação do livro: `Disponível` ou `Emprestado` |

## Endpoints

| Método | Rota                    | Descrição                                   | Códigos de resposta |
|--------|-------------------------|---------------------------------------------|---------------------|
| GET    | `/`                     | Exibe a mensagem de boas-vindas             | 200 OK              |
| GET    | `/livros`               | Lista todos os livros cadastrados           | 200 OK              |
| POST   | `/livros/cadastrar`     | Cadastra um novo livro                      | 201 Created         |
| PUT    | `/livros/alterar/{id}`  | Atualiza as informações de um livro         | 200 OK, 404 Not Found |
| GET    | `/livros/{id}`          | Consulta um livro pelo ID                   | 200 OK, 404 Not Found |
| GET    | `/documentacao`         | Apresenta a lista de endpoints disponíveis  | 200 OK              |

Todas as requisições e respostas utilizam o formato JSON. Nas requisições com corpo (POST e PUT), deve ser enviado o cabeçalho `Content-Type: application/json`.

### GET /

Retorna a mensagem de boas-vindas ao sistema.

Resposta (200 OK):

```json
{
  "mensagem": "Bem-vindo ao sistema de gerenciamento da Biblioteca CloudTech Solutions"
}
```

### GET /livros

Retorna a lista de livros cadastrados. Caso nenhum livro tenha sido cadastrado, retorna uma lista vazia (`[]`).

Resposta (200 OK):

```json
[
  {
    "id": 1,
    "titulo": "Banco de Dados para Iniciantes",
    "autor": "Maria Souza",
    "editora": "EduTech",
    "ano": 2023,
    "categoria": "Informática",
    "status": "Disponível"
  }
]
```

### POST /livros/cadastrar

Cadastra um novo livro. O `id` não deve ser enviado, pois é gerado automaticamente pela aplicação, iniciando em 1.

Requisição:

```json
{
  "titulo": "Banco de Dados para Iniciantes",
  "autor": "Maria Souza",
  "editora": "EduTech",
  "ano": 2023,
  "categoria": "Informática",
  "status": "Disponível"
}
```

Resposta (201 Created), com o livro cadastrado e o `id` gerado:

```json
{
  "id": 1,
  "titulo": "Banco de Dados para Iniciantes",
  "autor": "Maria Souza",
  "editora": "EduTech",
  "ano": 2023,
  "categoria": "Informática",
  "status": "Disponível"
}
```

### PUT /livros/alterar/{id}

Substitui o título, o autor, a editora, o ano, a categoria e o status do livro informado na URL. O `id` do livro é mantido; caso um `id` seja enviado no corpo da requisição, ele é ignorado.

Requisição para `PUT /livros/alterar/1`:

```json
{
  "titulo": "Banco de Dados Essencial",
  "autor": "Maria Souza",
  "editora": "EduTech",
  "ano": 2024,
  "categoria": "Banco de Dados",
  "status": "Emprestado"
}
```

Resposta (200 OK), com o livro atualizado:

```json
{
  "id": 1,
  "titulo": "Banco de Dados Essencial",
  "autor": "Maria Souza",
  "editora": "EduTech",
  "ano": 2024,
  "categoria": "Banco de Dados",
  "status": "Emprestado"
}
```

Caso não exista livro com o ID informado, a resposta é 404 Not Found, sem corpo.

### GET /livros/{id}

Retorna os dados do livro com o ID informado.

Resposta para `GET /livros/1` (200 OK):

```json
{
  "id": 1,
  "titulo": "Banco de Dados para Iniciantes",
  "autor": "Maria Souza",
  "editora": "EduTech",
  "ano": 2023,
  "categoria": "Informática",
  "status": "Disponível"
}
```

Caso não exista livro com o ID informado, a resposta é 404 Not Found, sem corpo.

### GET /documentacao

Retorna a lista de endpoints disponíveis na aplicação.

Resposta (200 OK):

```json
{
  "endpoints": [
    "/",
    "/livros",
    "/livros/cadastrar",
    "/livros/alterar/{id}",
    "/livros/{id}",
    "/documentacao"
  ]
}
```
