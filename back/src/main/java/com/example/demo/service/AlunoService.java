package com.example.demo.service;

import com.example.demo.entity.Aluno;
import com.example.demo.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlunoService {
    private final AlunoRepository alunoRepository;

    @Transactional
    public Aluno criarAluno(Aluno aluno) {
        Optional<Aluno> alunoExistente = alunoRepository.findByCpf(aluno.getCpf());
        if (alunoExistente.isPresent()) {
            throw new IllegalStateException("Esse aluno ja existe");
        }

        return alunoRepository.save(aluno);
    }

    @Transactional(readOnly = true)
    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    @Transactional
    public Aluno atualizarAluno(Long id, Aluno alunoAtualizado) {
        Aluno alunoExistente = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com o id:" + id));

        alunoExistente.setNome(alunoAtualizado.getNome());
        alunoExistente.setTelefone(alunoAtualizado.getTelefone());
        alunoExistente.setEndereco(alunoAtualizado.getEndereco());
        alunoExistente.setDataNascimento(alunoAtualizado.getDataNascimento());
        alunoExistente.setCpf(alunoAtualizado.getCpf());

        return alunoRepository.save(alunoExistente);
    }

    @Transactional
    public void deletarAluno(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new RuntimeException("Usuario nao encontrado com o id:" + id);
        }
        alunoRepository.deleteById(id);

    }
}
