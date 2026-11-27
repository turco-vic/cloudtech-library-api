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

## Como executar

Pré-requisitos: Java 21 e Maven instalados.

A aplicação utiliza a porta definida na variável de ambiente `PORT` e, caso ela não exista, a porta 8080 (`server.port=${PORT:8080}` no arquivo `application.properties`). Essa configuração permite que a plataforma Railway defina a porta automaticamente.

### Com o Maven

Na pasta raiz do projeto, execute:

```
mvn spring-boot:run
```

A aplicação ficará disponível em `http://localhost:8080`.

### Pelo arquivo .jar

Para gerar o arquivo .jar (este comando também executa os testes):

```
mvn package
```

Para executar o arquivo gerado:

```
java -jar target/biblioteca-api-1.0.0.jar
```

## Como testar

Os testes automatizados foram desenvolvidos com JUnit 5 e MockMvc, na classe `src/test/java/com/cloudtech/biblioteca/LivroControllerTest.java`. Para executá-los:

```
mvn test
```

Cada teste é executado com o contexto da aplicação recriado (`@DirtiesContext`), ou seja, sempre começa com a lista de livros vazia e com o próximo ID igual a 1.

| Nº | Teste                                     | O que verifica |
|----|-------------------------------------------|----------------|
| 1  | `paginaInicialDeveRetornar200`            | `GET /` retorna HTTP 200 e a mensagem de boas-vindas |
| 2  | `listagemDeLivrosDeveRetornarListaE200`   | `GET /livros` retorna HTTP 200 e uma lista com o livro cadastrado |
| 3  | `cadastroDeLivroDeveRetornar201`          | `POST /livros/cadastrar` cadastra o livro, gera o ID 1 e retorna HTTP 201 |
| 4  | `consultaPorIdDeveRetornarDadosDoLivro`   | `GET /livros/1` retorna HTTP 200 e todos os dados do livro |
| 5  | `alteracaoDeLivroDeveAtualizarInformacoes`| `PUT /livros/alterar/1` retorna HTTP 200, atualiza os dados, mantém o ID e a alteração aparece na consulta seguinte |
| 6  | `consultaDeIdInexistenteDeveRetornar404`  | `GET /livros/999` retorna HTTP 404 |
| 7  | `documentacaoDeveRetornarEndpoints`       | `GET /documentacao` retorna HTTP 200 e os 6 endpoints da aplicação |

Os endpoints também podem ser testados manualmente com a extensão Thunder Client do Visual Studio Code, utilizando os exemplos de requisição da seção [Endpoints](#endpoints).

## Fluxo de branches

O repositório utiliza as seguintes branches:

| Branch                 | Finalidade |
|------------------------|------------|
| `main`                 | Versão estável da aplicação |
| `develop`              | Integração das funcionalidades em desenvolvimento |
| `feature/livros`       | Cadastro, listagem, consulta e alteração de livros |
| `feature/documentacao` | Endpoint `/documentacao` |
| `feature/testes`       | Testes automatizados com JUnit 5 e MockMvc |
| `feature/pipeline`     | Workflow de integração contínua no GitHub Actions |
| `feature/readme`       | Documentação do projeto neste README |

Cada funcionalidade foi desenvolvida em uma branch `feature/*` criada a partir da `develop` atualizada. Ao final de cada funcionalidade, foi aberto um pull request para a `develop`, integrado por merge commit. Após a conclusão de todas as funcionalidades, a `develop` foi integrada à `main` por meio de um pull request.

## Pipeline de integração contínua

O workflow `CI - Build e Testes`, definido em `.github/workflows/pipeline.yml`, é executado automaticamente no GitHub Actions a cada push enviado ao repositório, em qualquer branch. Suas etapas são:

1. **Baixar o código-fonte:** obtém o código do repositório (`actions/checkout`).
2. **Configurar o Java 21:** prepara o ambiente com o Java 21 da distribuição Temurin (`actions/setup-java`).
3. **Instalar dependências, compilar, executar os testes e gerar o .jar:** executa `mvn -B package`, que baixa as dependências do `pom.xml`, compila a aplicação, executa os testes automatizados e gera o arquivo `biblioteca-api-1.0.0.jar`. Caso algum teste falhe, o pipeline é interrompido.
4. **Publicar o .jar gerado:** disponibiliza o arquivo .jar como artefato `biblioteca-api-jar`, que pode ser baixado na página da execução (`actions/upload-artifact`).

O resultado de cada execução pode ser consultado na aba **Actions** do repositório no GitHub.
