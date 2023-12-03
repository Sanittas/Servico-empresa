package br.com.sanittas.app.service;

import br.com.sanittas.app.model.CategoriaServico;
import br.com.sanittas.app.repository.CategoriaServicoRepository;
import br.com.sanittas.app.service.categoria.dto.CategoriaServicoCriacaoDto;
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
class CategoriaServicoServicesTest {

    @Mock
    private CategoriaServicoRepository categoriaServicoRepository;

    @InjectMocks
    private CategoriaServicoServices categoriaServicoServices;

    @Test
    void cadastrar_DeveCadastrarCategoria_QuandoChamado() {
        // Given
        CategoriaServicoCriacaoDto dados = new CategoriaServicoCriacaoDto();
        dados.setAreaSaude("Área de Saúde");

        // When
        categoriaServicoServices.cadastrar(dados);

        // Then
        verify(categoriaServicoRepository, times(1)).save(any(CategoriaServico.class));
    }

    @Test
    void cadastrar_DeveLancarResponseStatusException_QuandoErroAoCadastrarCategoria() {
        // Given
        CategoriaServicoCriacaoDto dados = new CategoriaServicoCriacaoDto();
        dados.setAreaSaude("Área de Saúde");

        // Simula o comportamento do save lançando exceção
        when(categoriaServicoRepository.save(any(CategoriaServico.class))).thenThrow(new RuntimeException("Erro ao cadastrar categoria"));

        // When/Then
        assertThrows(ResponseStatusException.class, () -> categoriaServicoServices.cadastrar(dados));
    }

    @Test
    void listar_DeveRetornarListaDeCategorias_QuandoChamado() {
        // Given
        when(categoriaServicoRepository.findAll()).thenReturn(Collections.singletonList(new CategoriaServico()));

        // When
        var result = categoriaServicoServices.listar();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(categoriaServicoRepository, times(1)).findAll();
    }

    @Test
    void atualizar_DeveAtualizarCategoria_QuandoChamado() {
        // Given
        Integer id = 1;
        CategoriaServicoCriacaoDto categoriaServico = new CategoriaServicoCriacaoDto();
        categoriaServico.setAreaSaude("Nova Área de Saúde");

        CategoriaServico categoriaExistente = new CategoriaServico();
        when(categoriaServicoRepository.findById(id)).thenReturn(Optional.of(categoriaExistente));
        when(categoriaServicoRepository.save(categoriaExistente)).thenReturn(categoriaExistente);

        // When
        categoriaServicoServices.atualizar(id, categoriaServico);

        // Then
        verify(categoriaServicoRepository, times(1)).findById(id);
        verify(categoriaServicoRepository, times(1)).save(categoriaExistente);
    }

    @Test
    void atualizar_DeveLancarResponseStatusException_QuandoCategoriaNaoEncontrada() {
        // Given
        Integer id = 1;
        CategoriaServicoCriacaoDto categoriaServico = new CategoriaServicoCriacaoDto();
        categoriaServico.setAreaSaude("Nova Área de Saúde");

        // Simula o findById retornando um Optional vazio
        when(categoriaServicoRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResponseStatusException.class, () -> categoriaServicoServices.atualizar(id, categoriaServico));
    }

    @Test
    void deletar_DeveDeletarCategoria_QuandoChamado() {
        // Given
        Integer id = 1;

        // When
        when(categoriaServicoRepository.findById(id)).thenReturn(Optional.of(new CategoriaServico()));
        doNothing().when(categoriaServicoRepository).deleteById(id);
        categoriaServicoServices.deletar(id);

        // Then
        verify(categoriaServicoRepository, times(1)).deleteById(id);
    }

    @Test
    void deletar_DeveLancarResponseStatusException_QuandoCategoriaNaoEncontrada() {
        // Given
        Integer id = 1;

        // Simula o findById retornando um Optional vazio
        when(categoriaServicoRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResponseStatusException.class, () -> categoriaServicoServices.deletar(id));
    }
}
