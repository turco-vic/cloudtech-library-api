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
