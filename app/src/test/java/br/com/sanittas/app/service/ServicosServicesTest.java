package br.com.sanittas.app.service;

import br.com.sanittas.app.model.Servico;
import br.com.sanittas.app.repository.ServicoRepository;
import br.com.sanittas.app.service.servico.dto.ServicoCriacaoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicosServicesTest {

    @Mock
    private ServicoRepository servicoRepository;

    @Mock
    private CategoriaServicoRepository categoriaServicoRepository;

    @InjectMocks
    private ServicosServices servicosServices;

    @Test
    void listar_DeveRetornarListaDeServicos_QuandoChamado() {
        // Given
        List<Servico> servicos = Collections.singletonList(new Servico());
        when(servicoRepository.findAll()).thenReturn(servicos);

        // When
        List<Servico> result = servicosServices.listar();

        // Then
        assertEquals(servicos, result);
    }

    @Test
    void listar_DeveLancarResponseStatusException_QuandoErroAoBuscarServicos() {
        // Given
        when(servicoRepository.findAll()).thenThrow(new RuntimeException("Erro ao buscar servicos"));

        // When/Then
        assertThrows(ResponseStatusException.class, () -> servicosServices.listar());
    }

    @Test
    void listarServicoCategoria_DeveRetornarListaDeServicos_QuandoChamado() {
        // Given
        when(servicoRepository.findAlllJoinServicoCategoria()).thenReturn(Collections.singletonList(new Servico()));

        // When
        servicosServices.listarServicoCategoria();

        // Then
        verify(servicoRepository, times(1)).findAlllJoinServicoCategoria();
    }

    @Test
    void cadastrar_DeveCadastrarNovoServico_QuandoChamado() {
        // Given
        ServicoCriacaoDto dados = new ServicoCriacaoDto();
        dados.setFkCategoriaServico(1);
        dados.setDescricao("Descrição do Serviço");

        CategoriaServico categoriaServico = new CategoriaServico();
        when(categoriaServicoRepository.findById(1)).thenReturn(Optional.of(categoriaServico));

        // When
        servicosServices.cadastrar(dados);

        // Then
        verify(categoriaServicoRepository, times(1)).findById(1);
        verify(servicoRepository, times(1)).save(any(Servico.class));
    }

    @Test
    void cadastrar_DeveJogarExceptionQuandoNaoForEncontradoUmaCategoria() {
        // Given
        ServicoCriacaoDto dados = new ServicoCriacaoDto();
        dados.setFkCategoriaServico(1);
        dados.setDescricao("Descrição do Serviço");

        CategoriaServico categoriaServico = new CategoriaServico();
        when(categoriaServicoRepository.findById(1)).thenReturn(Optional.empty());

        // When
        assertThrows(ResponseStatusException.class,() -> {
            servicosServices.cadastrar(dados);
        });

        // Then
        verify(categoriaServicoRepository, times(1)).findById(1);
        verifyNoMoreInteractions(servicoRepository);
    }

    @Test
    void atualizar_DeveAtualizarServico_QuandoChamado() {
        // Given
        ServicoCriacaoDto dados = new ServicoCriacaoDto();
        dados.setFkCategoriaServico(1);
        dados.setDescricao("Nova descrição do Serviço");

        CategoriaServico categoriaServico = new CategoriaServico();
        when(categoriaServicoRepository.findById(1)).thenReturn(Optional.of(categoriaServico));

        Servico servicoExistente = new Servico();
        when(servicoRepository.findById(1)).thenReturn(Optional.of(servicoExistente));

        // When
        servicosServices.atualizar(1, dados);

        // Then
        verify(servicoRepository, times(1)).findById(1);
        verify(servicoRepository, times(1)).save(servicoExistente);
    }

    @Test
    void atualizar_DeveLancarResponseStatusException_QuandoCategoriaServicoNaoEncontrado() {
        // Given
        ServicoCriacaoDto dados = new ServicoCriacaoDto();
        dados.setFkCategoriaServico(1);
        dados.setDescricao("Nova descrição do Serviço");

        when(categoriaServicoRepository.findById(1)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResponseStatusException.class, () -> servicosServices.atualizar(1, dados));
    }

    @Test
    void atualizar_DeveLancarResponseStatusException_QuandoServicoNaoEncontrado() {
        // Given
        ServicoCriacaoDto dados = new ServicoCriacaoDto();
        dados.setFkCategoriaServico(1);
        dados.setDescricao("Nova descrição do Serviço");

        CategoriaServico categoriaServico = new CategoriaServico();
        when(categoriaServicoRepository.findById(1)).thenReturn(Optional.of(categoriaServico));
        when(servicoRepository.findById(1)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResponseStatusException.class, () -> servicosServices.atualizar(1, dados));
    }

    @Test
    void deletar_DeveDeletarServico_QuandoChamado() {
        // Given
        Servico servico = new Servico();
        when(servicoRepository.findById(1)).thenReturn(Optional.of(servico));

        // When
        servicosServices.deletar(1);

        // Then
        verify(servicoRepository, times(1)).deleteById(1);
    }

    @Test
    void deletar_DeveLancarResponseStatusException_QuandoServicoNaoEncontrado() {
        // Given
        when(servicoRepository.findById(1)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResponseStatusException.class, () -> servicosServices.deletar(1));
    }

}
