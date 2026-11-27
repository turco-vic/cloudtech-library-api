package com.cloudtech.biblioteca.controller;

import com.cloudtech.biblioteca.models.Livro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/livros/{id}")
    public ResponseEntity<Livro> buscarLivroPorId(@PathVariable Long id) {
        for (Livro livro : livros) {
            if (livro.getId().equals(id)) {
                return ResponseEntity.ok(livro);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/livros/alterar/{id}")
    public ResponseEntity<Livro> alterarLivro(@PathVariable Long id, @RequestBody Livro dadosNovos) {
        for (Livro livro : livros) {
            if (livro.getId().equals(id)) {
                livro.setTitulo(dadosNovos.getTitulo());
                livro.setAutor(dadosNovos.getAutor());
                livro.setEditora(dadosNovos.getEditora());
                livro.setAno(dadosNovos.getAno());
                livro.setCategoria(dadosNovos.getCategoria());
                livro.setStatus(dadosNovos.getStatus());
                return ResponseEntity.ok(livro);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/documentacao")
    public Map<String, List<String>> documentacao() {
        List<String> endpoints = new ArrayList<>();
        endpoints.add("/");
        endpoints.add("/livros");
        endpoints.add("/livros/cadastrar");
        endpoints.add("/livros/alterar/{id}");
        endpoints.add("/livros/{id}");
        endpoints.add("/documentacao");

        Map<String, List<String>> resposta = new LinkedHashMap<>();
        resposta.put("endpoints", endpoints);
        return resposta;
    }
}
