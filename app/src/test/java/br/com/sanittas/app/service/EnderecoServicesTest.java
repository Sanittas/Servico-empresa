package br.com.sanittas.app.service;

import br.com.sanittas.app.model.Empresa;
import br.com.sanittas.app.model.Endereco;
import br.com.sanittas.app.repository.EmpresaRepository;
import br.com.sanittas.app.repository.EnderecoRepository;
import br.com.sanittas.app.service.endereco.dto.EnderecoCriacaoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class EnderecoServicesTest {

    @Mock
    private EnderecoRepository repository;

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private EnderecoServices enderecoServices;

    @Test
    void cadastrarEnderecoEmpresa_DeveCadastrarEndereco_QuandoChamado() {
        // Given
        EnderecoCriacaoDto enderecoCriacaoDto = new EnderecoCriacaoDto();
        enderecoCriacaoDto.setLogradouro("Rua Teste");
        enderecoCriacaoDto.setNumero("123");
        enderecoCriacaoDto.setComplemento("Apto 1");
        enderecoCriacaoDto.setCidade("Cidade Teste");
        enderecoCriacaoDto.setEstado("TS");

        Integer empresaId = 1;

        // Simula o comportamento do findById retornando uma empresa
        when(empresaRepository.findById(empresaId)).thenReturn(Optional.of(new Empresa()));

        // When
        enderecoServices.cadastrarEnderecoEmpresa(enderecoCriacaoDto, empresaId);

        // Then
        verify(repository, times(1)).save(any(Endereco.class));
    }

    @Test
    void cadastrarEnderecoEmpresa_DeveLancarResponseStatusException_QuandoEmpresaNaoEncontrada() {
        // Given
        EnderecoCriacaoDto enderecoCriacaoDto = new EnderecoCriacaoDto();
        Integer empresaId = 1;

        // Simula o comportamento do findById retornando um Optional vazio
        when(empresaRepository.findById(empresaId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResponseStatusException.class, () -> enderecoServices.cadastrarEnderecoEmpresa(enderecoCriacaoDto, empresaId));
    }

    @Test
    void atualizar_DeveAtualizarEndereco_QuandoChamado() {
        // Given
        Long enderecoId = 1L;
        EnderecoCriacaoDto enderecoCriacaoDto = new EnderecoCriacaoDto();
        enderecoCriacaoDto.setLogradouro("Rua Atualizada");

        Endereco endereco = new Endereco();
        endereco.setId(enderecoId);
        endereco.setLogradouro("Rua Teste");

        Endereco enderecoAtualizado = new Endereco();
        enderecoAtualizado.setId(enderecoId);
        enderecoAtualizado.setLogradouro("Rua Atualizada");

        // Simula o comportamento do findById retornando um endereço
        when(repository.findById(enderecoId)).thenReturn(Optional.of(endereco));
        when(repository.save(any(Endereco.class))).thenReturn(enderecoAtualizado);

        // When
        var result = enderecoServices.atualizar(enderecoCriacaoDto, enderecoId);

        // Then
        assertNotNull(result);
        assertEquals("Rua Atualizada", result.getLogradouro());
        verify(repository, times(1)).save(any(Endereco.class));
    }

    @Test
    void atualizar_DeveLancarResponseStatusException_QuandoEnderecoNaoEncontrado() {
        // Given
        Long enderecoId = 1L;
        EnderecoCriacaoDto enderecoCriacaoDto = new EnderecoCriacaoDto();
        enderecoCriacaoDto.setLogradouro("Rua Atualizada");

        // Simula o comportamento do findById retornando um Optional vazio
        when(repository.findById(enderecoId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResponseStatusException.class, () -> enderecoServices.atualizar(enderecoCriacaoDto, enderecoId));
    }

    @Test
    //deletar endereço
    void deletarEndereco_DeveDeletarEndereco_QuandoChamado() {
        // Given
        Long enderecoId = 1L;

        // Simula o comportamento do findById retornando um endereço
        when(repository.existsById(enderecoId)).thenReturn(true);
        doNothing().when(repository).deleteById(enderecoId);

        // When
        enderecoServices.deletarEndereco(enderecoId);

        // Then
        verify(repository, times(1)).deleteById(enderecoId);
    }

    @Test
    void deletarEndereco_DeveLancarResponseStatusException_QuandoEnderecoNaoEncontrado() {
        // Given
        Long enderecoId = 1L;

        // Simula o comportamento do findById retornando um Optional vazio
        when(repository.existsById(enderecoId)).thenReturn(false);

        // When/Then
        assertThrows(ResponseStatusException.class, () -> enderecoServices.deletarEndereco(enderecoId));
    }

    @Test
    void listarEnderecosPorEmpresa_DeveListarEnderecos_QuandoChamado() {
        // Given
        Integer empresaId = 1;

        // Simula o comportamento do findById retornando uma empresa
        when(empresaRepository.findById(empresaId)).thenReturn(Optional.of(new Empresa()));

        // When
        var result = enderecoServices.listarEnderecosPorEmpresa(empresaId);

        // Then
        assertNotNull(result);
        verify(empresaRepository, times(1)).findById(empresaId);
    }

    @Test
    void listarEnderecosPorEmpresa_DeveLancarResponseStatusException_QuandoEmpresaNaoEncontrada() {
        // Given
        Integer empresaId = 1;

        // Simula o comportamento do findById retornando um Optional vazio
        when(empresaRepository.findById(empresaId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ResponseStatusException.class, () -> enderecoServices.listarEnderecosPorEmpresa(empresaId));
    }
}
