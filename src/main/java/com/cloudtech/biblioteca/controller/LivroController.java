package com.cloudtech.biblioteca.controller;

import com.cloudtech.biblioteca.models.Livro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LivroController {

    private List<Livro> livros = new ArrayList<>();
    private Long proximoId = 1L;

    @GetMapping("/")
    public Map<String, String> paginaInicial() {
        Map<String, String> resposta = new LinkedHashMap<>();
        resposta.put("mensagem", "Bem-vindo ao sistema de gerenciamento da Biblioteca CloudTech Solutions");
        return resposta;
    }

    @GetMapping("/livros")
    public List<Livro> listarLivros() {
        return livros;
    }

    @PostMapping("/livros/cadastrar")
    public ResponseEntity<Livro> cadastrarLivro(@RequestBody Livro livro) {
        livro.setId(proximoId);
        proximoId++;
        livros.add(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(livro);
    }
}
