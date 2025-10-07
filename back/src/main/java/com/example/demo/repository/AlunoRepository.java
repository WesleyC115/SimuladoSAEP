package com.example.demo.repository;

import com.example.demo.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository <Aluno, Long> {
    Optional<Aluno> findByCpf(String cpf);
}
