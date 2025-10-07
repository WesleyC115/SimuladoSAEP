package com.example.demo.service;

import com.example.demo.entity.Aluno;
import com.example.demo.repository.AlunoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    @Test
    @DisplayName("Deve criar aluno com sucesso")
    void deveCriarAlunoComSucesso() {
        Aluno alunoParaSalvar = new Aluno();
        alunoParaSalvar.setNome("Wesley de Moura");

        Aluno alunoSalvo = new Aluno();
        alunoSalvo.setId(1L);
        alunoSalvo.setNome("Wesley de Moura");

        when(alunoRepository.save(any(Aluno.class))).thenReturn(alunoSalvo);

        Aluno resultado = alunoService.criarAluno(alunoParaSalvar);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Wesley de Moura", resultado.getNome());

    }

    @Test
    @DisplayName("Deve buscar um aluno por ID com sucesso")
    void deveBuscarAlunoPorIdComSucesso() {
        Aluno alunoExistente = new Aluno();
        alunoExistente.setId(1L);
        alunoExistente.setNome("Celia Alves");

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(alunoExistente));

        Optional<Aluno> resultado = alunoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        assertEquals("Celia Alves", resultado.get().getNome());
    }

    // ... dentro da classe AlunoServiceTest

    @Test
    @DisplayName("Deve atualizar um aluno com sucesso")
    void deveAtualizarAlunoComSucesso() {

        Long alunoId = 1L;
        Aluno alunoExistente = new Aluno();
        alunoExistente.setId(alunoId);
        alunoExistente.setNome("Chris Camargo");

        Aluno dadosNovos = new Aluno();
        dadosNovos.setNome("Chris Camargo Atualizado");

        when(alunoRepository.findById(alunoId)).thenReturn(Optional.of(alunoExistente));
        when(alunoRepository.save(any(Aluno.class))).thenReturn(alunoExistente);

        Aluno resultado = alunoService.atualizarAluno(alunoId, dadosNovos);

        assertNotNull(resultado);
        assertEquals(alunoId, resultado.getId());
        assertEquals("Chris Camargo Atualizado", resultado.getNome());
    }

    // ... dentro da classe AlunoServiceTest

    @Test
    @DisplayName("Deve deletar um aluno com sucesso")
    void deveDeletarAlunoComSucesso() {

        Long alunoId = 1L;
        when(alunoRepository.existsById(alunoId)).thenReturn(true);
        doNothing().when(alunoRepository).deleteById(alunoId);

        assertDoesNotThrow(() -> alunoService.deletarAluno(alunoId));

        verify(alunoRepository, times(1)).deleteById(alunoId);
    }
}
