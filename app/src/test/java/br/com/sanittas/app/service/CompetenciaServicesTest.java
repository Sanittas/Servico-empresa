package br.com.sanittas.app.service;

import br.com.sanittas.app.model.Competencia;
import br.com.sanittas.app.repository.CompetenciaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompetenciaServicesTest {

    @Mock
    private CompetenciaRepository competenciaRepository;

    @InjectMocks
    private CompetenciaServices competenciaServices;

    @Test
    void listar_DeveRetornarListaDeCompetencias_QuandoChamado() {
        // Given
        when(competenciaRepository.findAll()).thenReturn(Collections.singletonList(new Competencia()));

        // When
        var result = competenciaServices.listar();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(competenciaRepository, times(1)).findAll();
    }

    @Test
    void listar_DeveLancarResponseStatusException_QuandoErroAoBuscarCompetencias() {
        // Given
        when(competenciaRepository.findAll()).thenThrow(new RuntimeException("Erro ao buscar competencias"));

        // When/Then
        assertThrows(ResponseStatusException.class, () -> competenciaServices.listar());
    }

    @Test
    void cadastrar_DeveCadastrarCompetencia_QuandoChamado() {
        // Given
        Competencia competencia = new Competencia();

        // When
        when(competenciaRepository.save(competencia)).thenReturn(competencia);
        var result = competenciaServices.cadastrar(competencia);

        // Then
        assertNotNull(result);
        verify(competenciaRepository, times(1)).save(competencia);
    }

    @Test
    void cadastrar_DeveLancarResponseStatusException_QuandoErroAoCadastrarCompetencia() {
        // Given
        Competencia competencia = new Competencia();

        // When
        when(competenciaRepository.save(competencia)).thenThrow(new RuntimeException("Erro ao cadastrar competencia"));

        // When/Then
        assertThrows(ResponseStatusException.class, () -> competenciaServices.cadastrar(competencia));
    }

    @Test
    void atualizar_DeveAtualizarCompetencia_QuandoChamado() {
        // Given
        Integer id = 1;
        Competencia competencia = new Competencia();
        competencia.setDescricao("Nova descrição");

        Competencia competenciaExistente = new Competencia();
        when(competenciaRepository.findById(id)).thenReturn(Optional.of(competenciaExistente));
        when(competenciaRepository.save(competenciaExistente)).thenReturn(competenciaExistente);

        // When
        var result = competenciaServices.atualizar(id, competencia);

        // Then
        assertNotNull(result);
        assertEquals("Nova descrição", result.getDescricao());
        verify(competenciaRepository, times(1)).findById(id);
        verify(competenciaRepository, times(1)).save(competenciaExistente);
    }

    @Test
    void atualizar_DeveLancarResponseStatusException_QuandoCompetenciaNaoEncontrada() {
        // Given
        Integer id = 1;
        Competencia competencia = new Competencia();
        competencia.setDescricao("Nova descrição");

        // When
        when(competenciaRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResponseStatusException.class, () -> competenciaServices.atualizar(id, competencia));
    }

    @Test
    void deletar_DeveDeletarCompetencia_QuandoChamado() {
        // Given
        Integer id = 1;

        // When
        doNothing().when(competenciaRepository).deleteById(id);
        competenciaServices.deletar(id);

        // Then
        verify(competenciaRepository, times(1)).deleteById(id);
    }

    @Test
    void deletar_DeveLancarResponseStatusException_QuandoErroAoDeletarCompetencia() {
        // Given
        Integer id = 1;

        // When
        doThrow(new RuntimeException("Erro ao deletar competencia")).when(competenciaRepository).deleteById(id);

        // When/Then
        assertThrows(ResponseStatusException.class, () -> competenciaServices.deletar(id));
    }
}
