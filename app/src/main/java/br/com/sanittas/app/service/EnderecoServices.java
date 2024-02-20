package br.com.sanittas.app.service;

import br.com.sanittas.app.model.EnderecoEmpresa;
import br.com.sanittas.app.repository.EmpresaRepository;
import br.com.sanittas.app.repository.EnderecoEmpresaRepository;
import br.com.sanittas.app.service.endereco.dto.EnderecoCriacaoDto;
import br.com.sanittas.app.service.endereco.dto.EnderecoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class EnderecoServices {

    @Autowired
    private EnderecoEmpresaRepository repository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public void cadastrarEnderecoEmpresa(EnderecoCriacaoDto enderecoCriacaoDto, Integer empresa_id) {
        log.info("Cadastrando endereço para a empresa com ID: {}", empresa_id);
        var endereco = EnderecoMapper.of(enderecoCriacaoDto);
        var empresa = empresaRepository.findById(empresa_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        empresa.addEndereco(endereco);
        repository.save(endereco);
        empresaRepository.save(empresa);
        log.info("Endereço cadastrado com sucesso para a empresa com ID: {}", empresa_id);
    }

    public EnderecoEmpresa atualizar(EnderecoCriacaoDto enderecoCriacaoDto, Integer id) {

        log.info("Atualizando endereço com ID: {}", id);
        var endereco = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        endereco.setLogradouro(enderecoCriacaoDto.getLogradouro());
        endereco.setNumero(enderecoCriacaoDto.getNumero());
        endereco.setComplemento(enderecoCriacaoDto.getComplemento());
        endereco.setCidade(enderecoCriacaoDto.getCidade());
        endereco.setEstado(enderecoCriacaoDto.getEstado());
        return repository.save(endereco);
    }

    public void deletarEndereco(Integer id) {
        log.info("Deletando endereço com ID: {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Endereço deletado com sucesso. ID: {}", id);
        } else {
            log.warn("Tentativa de deletar endereço com ID {}, mas não encontrado.", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<EnderecoEmpresa> listarEnderecosPorEmpresa(Integer idEmpresa) {
            log.info("Listando endereços para a empresa com ID: {}", idEmpresa);
            var empresa = empresaRepository.findById(idEmpresa).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            return empresa.getEnderecos();
    }
}
