package br.com.sanittas.app.service;

import br.com.sanittas.app.model.AgendamentoServico;
import br.com.sanittas.app.model.Usuario;
import br.com.sanittas.app.repository.AgendamentoRepository;
import br.com.sanittas.app.repository.UsuarioRepository;
import br.com.sanittas.app.service.agendamento.dto.AgendamentoCriacaoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class AgendamentoServicoServicesTest {

    @Mock
    private AgendamentoRepository repository;

    @Mock
    private ServicoEmpresaRepository servicoEmpresaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ServicoEmpresaServices servicoEmpresaServices;

    @InjectMocks
    private AgendamentoServicoServices agendamentoServicoServices;

    @Test
    void listar_DeveRetornarListaDeAgendamentos_QuandoChamado() {
        // Given
        when(repository.findAll()).thenReturn(Collections.singletonList(new AgendamentoServico()));
        when(servicoEmpresaServices.listar()).thenReturn(Collections.emptyList());

        // When
        var result = agendamentoServicoServices.listar();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(repository, times(1)).findAll();
        verify(servicoEmpresaServices, times(1)).listar();
    }

    @Test
    void listar_DeveLancarResponseStatusException_QuandoErroAoListarAgendamentos() {
        // Given
        when(repository.findAll()).thenThrow(new RuntimeException("Erro ao listar agendamentos"));

        // When/Then
        assertThrows(ResponseStatusException.class, () -> agendamentoServicoServices.listar());
    }

    @Test
    void cadastrar_DeveCadastrarAgendamento_QuandoChamado() {
        // Given
        AgendamentoCriacaoDto dados = new AgendamentoCriacaoDto();
        dados.setIdServicoEmpresa(1);
        dados.setIdUsuario(1);
        dados.setDataAgendamento(LocalDateTime.parse("2023-12-31T23:59:59"));

        when(servicoEmpresaRepository.findById(1)).thenReturn(Optional.of(new ServicoEmpresa()));
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(new Usuario()));

        // When
        agendamentoServicoServices.cadastrar(dados);

        // Then
        verify(repository, times(1)).save(any(AgendamentoServico.class));
    }

    @Test
    void cadastrar_DeveLancarResponseStatusException_QuandoErroAoCadastrarAgendamento() {
        // Given
        AgendamentoCriacaoDto dados = new AgendamentoCriacaoDto();
        dados.setIdServicoEmpresa(1);
        dados.setIdUsuario(1);
        dados.setDataAgendamento(LocalDateTime.parse("2023-12-31T23:59:59"));

        when(servicoEmpresaRepository.findById(1)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResponseStatusException.class, () -> agendamentoServicoServices.cadastrar(dados));
    }

    @Test
    void cadastrar_DeveLancarResponseStatusException_QuandoErroAoCadastrarAgendamento2() {
        // Given
        AgendamentoCriacaoDto dados = new AgendamentoCriacaoDto();
        dados.setIdServicoEmpresa(1);
        dados.setIdUsuario(1);
        dados.setDataAgendamento(LocalDateTime.parse("2023-12-31T23:59:59"));

        when(servicoEmpresaRepository.findById(1)).thenReturn(Optional.of(new ServicoEmpresa()));
        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResponseStatusException.class, () -> agendamentoServicoServices.cadastrar(dados));
    }

    @Test
    void atualizar_DeveAtualizarAgendamento_QuandoChamado() {
        // Given
        Integer id = 1;
        AgendamentoCriacaoDto agendamento = new AgendamentoCriacaoDto();
        agendamento.setDataAgendamento(LocalDateTime.parse("2023-12-31T23:59:59"));

        AgendamentoServico agendamentoExistente = new AgendamentoServico();
        when(repository.findById(id)).thenReturn(Optional.of(agendamentoExistente));
        when(repository.save(agendamentoExistente)).thenReturn(agendamentoExistente);

        // When
        agendamentoServicoServices.atualizar(id, agendamento);

        // Then
        verify(repository, times(1)).save(any(AgendamentoServico.class));
    }

    @Test
    void atualizar_DeveLancarResponseStatusException_QuandoAgendamentoNaoEncontrado() {
        // Given
        Integer id = 1;
        AgendamentoCriacaoDto agendamento = new AgendamentoCriacaoDto();
        agendamento.setDataAgendamento(LocalDateTime.parse("2023-12-31T23:59:59"));

        // Simula o findById retornando um Optional vazio
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResponseStatusException.class, () -> agendamentoServicoServices.atualizar(id, agendamento));
    }

    @Test
    void deletar_DeveDeletarAgendamento_QuandoChamado() {
        // Given
        Integer id = 1;

        // When
        when(repository.findById(id)).thenReturn(Optional.of(new AgendamentoServico()));

        // Then
        assertDoesNotThrow(() -> agendamentoServicoServices.deletar(id));
    }

    @Test
    void deletar_DeveLancarResponseStatusException_QuandoAgendamentoNaoEncontrado() {
        // Given
        Integer id = 1;

        // Simula o findById retornando um Optional vazio
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResponseStatusException.class, () -> agendamentoServicoServices.deletar(id));
    }
}
