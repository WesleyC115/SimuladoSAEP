package com.example.demo.controller;


import com.example.demo.entity.Aluno;
import com.example.demo.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @PostMapping
    public ResponseEntity<Aluno> criar(@RequestBody Aluno aluno) {
        Aluno novoAluno = alunoService.criarAluno(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAluno);
    }

    @GetMapping
    public List<Aluno> listarTodos() {
        return  alunoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        return alunoService.buscarPorId(id)
                .map(alunoEncontrado -> ResponseEntity.ok(alunoEncontrado))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody Aluno aluno) {
        try {
            Aluno alunoAtualizado = alunoService.atualizarAluno(id, aluno);
            return ResponseEntity.ok(alunoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            alunoService.deletarAluno(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
