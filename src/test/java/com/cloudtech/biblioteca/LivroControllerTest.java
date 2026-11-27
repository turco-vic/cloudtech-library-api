package com.cloudtech.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String livroJson = """
            {
              "titulo": "Banco de Dados para Iniciantes",
              "autor": "Maria Souza",
              "editora": "EduTech",
              "ano": 2023,
              "categoria": "Informática",
              "status": "Disponível"
            }
            """;

    @Test
    void paginaInicialDeveRetornar200() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Bem-vindo ao sistema de gerenciamento da Biblioteca CloudTech Solutions"));
    }

    @Test
    void listagemDeLivrosDeveRetornarListaE200() throws Exception {
        mockMvc.perform(post("/livros/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Banco de Dados para Iniciantes"));
    }

    @Test
    void cadastroDeLivroDeveRetornar201() throws Exception {
        mockMvc.perform(post("/livros/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Banco de Dados para Iniciantes"))
                .andExpect(jsonPath("$.autor").value("Maria Souza"))
                .andExpect(jsonPath("$.status").value("Disponível"));
    }

    @Test
    void consultaPorIdDeveRetornarDadosDoLivro() throws Exception {
        mockMvc.perform(post("/livros/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroJson))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/livros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Banco de Dados para Iniciantes"))
                .andExpect(jsonPath("$.autor").value("Maria Souza"))
                .andExpect(jsonPath("$.editora").value("EduTech"))
                .andExpect(jsonPath("$.ano").value(2023))
                .andExpect(jsonPath("$.categoria").value("Informática"))
                .andExpect(jsonPath("$.status").value("Disponível"));
    }

    @Test
    void alteracaoDeLivroDeveAtualizarInformacoes() throws Exception {
        mockMvc.perform(post("/livros/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroJson))
                .andExpect(status().isCreated());

        String livroAlteradoJson = """
                {
                  "titulo": "Banco de Dados Essencial",
                  "autor": "Maria Souza",
                  "editora": "EduTech",
                  "ano": 2024,
                  "categoria": "Banco de Dados",
                  "status": "Emprestado"
                }
                """;

        mockMvc.perform(put("/livros/alterar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(livroAlteradoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Banco de Dados Essencial"))
                .andExpect(jsonPath("$.ano").value(2024))
                .andExpect(jsonPath("$.categoria").value("Banco de Dados"))
                .andExpect(jsonPath("$.status").value("Emprestado"));

        mockMvc.perform(get("/livros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Banco de Dados Essencial"))
                .andExpect(jsonPath("$.status").value("Emprestado"));
    }

    @Test
    void consultaDeIdInexistenteDeveRetornar404() throws Exception {
        mockMvc.perform(get("/livros/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void documentacaoDeveRetornarEndpoints() throws Exception {
        mockMvc.perform(get("/documentacao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.endpoints.length()").value(6))
                .andExpect(jsonPath("$.endpoints[0]").value("/"))
                .andExpect(jsonPath("$.endpoints[1]").value("/livros"))
                .andExpect(jsonPath("$.endpoints[2]").value("/livros/cadastrar"))
                .andExpect(jsonPath("$.endpoints[3]").value("/livros/alterar/{id}"))
                .andExpect(jsonPath("$.endpoints[4]").value("/livros/{id}"))
                .andExpect(jsonPath("$.endpoints[5]").value("/documentacao"));
    }
}
