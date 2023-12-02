package br.com.sanittas.app.service;

import br.com.sanittas.app.model.Endereco;
import br.com.sanittas.app.repository.EmpresaRepository;
import br.com.sanittas.app.repository.EnderecoRepository;
import br.com.sanittas.app.service.endereco.dto.EnderecoCriacaoDto;
import br.com.sanittas.app.service.endereco.dto.EnderecoMapper;
import br.com.sanittas.app.service.endereco.dto.ListaEndereco;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EnderecoServices {

    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public void cadastrarEnderecoEmpresa(EnderecoCriacaoDto enderecoCriacaoDto, Integer empresa_id) {
        try {
            log.info("Cadastrando endereço para a empresa com ID: {}", empresa_id);
            var endereco = EnderecoMapper.of(enderecoCriacaoDto);
            var empresa = empresaRepository.findById(empresa_id);

            if (empresa.isPresent()) {
                endereco.setEmpresa(empresa.get());
                repository.save(endereco);
                log.info("Endereço cadastrado com sucesso para a empresa com ID: {}", empresa_id);
            } else {
                log.warn("Tentativa de cadastrar endereço para empresa com ID {}, mas a empresa não foi encontrada.", empresa_id);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public Endereco atualizar(EnderecoCriacaoDto enderecoCriacaoDto, Long id) {

        log.info("Atualizando endereço com ID: {}", id);
        var endereco = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        endereco.setLogradouro(enderecoCriacaoDto.getLogradouro());
        endereco.setNumero(enderecoCriacaoDto.getNumero());
        endereco.setComplemento(enderecoCriacaoDto.getComplemento());
        endereco.setCidade(enderecoCriacaoDto.getCidade());
        endereco.setEstado(enderecoCriacaoDto.getEstado());
        return repository.save(endereco);
    }

    public void deletarEndereco(Long id) {
        log.info("Deletando endereço com ID: {}", id);

        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Endereço deletado com sucesso. ID: {}", id);
        } else {
            log.warn("Tentativa de deletar endereço com ID {}, mas não encontrado.", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<Endereco> listarEnderecosPorEmpresa(Integer idEmpresa) {
        try {
            log.info("Listando endereços para a empresa com ID: {}", idEmpresa);
            var empresa = empresaRepository.findById(idEmpresa).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            return empresa.getEnderecos();
        } catch (Exception e) {
            log.error("Erro ao listar endereços para a empresa com ID: {}", idEmpresa, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
