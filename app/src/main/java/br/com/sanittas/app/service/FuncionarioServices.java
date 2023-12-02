package br.com.sanittas.app.service;

import br.com.sanittas.app.api.configuration.security.jwt.GerenciadorTokenJwt;
import br.com.sanittas.app.model.Empresa;
import br.com.sanittas.app.model.Funcionario;
import br.com.sanittas.app.repository.EmpresaRepository;
import br.com.sanittas.app.repository.FuncionarioRepository;
import br.com.sanittas.app.service.funcionario.dto.FuncionarioCriacaoDto;
import br.com.sanittas.app.service.funcionario.dto.FuncionarioMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class FuncionarioServices {
    @Autowired
    private FuncionarioRepository repository;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    public List<Funcionario> listaFuncionarios() {
        var funcionarios = repository.findAll();
        return funcionarios;
    }

    public Funcionario atualizar(Integer id, FuncionarioCriacaoDto dados) {
        var funcionario = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        funcionario.setFuncional(dados.getFuncional());
        funcionario.setNome(dados.getNome());
        if (repository.existsByCpf(dados.getCpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        funcionario.setCpf(dados.getCpf());
        funcionario.setRg(dados.getRg());
        if (repository.existsByEmail(dados.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        funcionario.setEmail(dados.getEmail());
        funcionario.setNumeroRegistroAtuacao(dados.getNumeroRegistroAtuacao());
        return repository.save(funcionario);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }

    public Funcionario buscarPorId(Integer id) {
            var funcionario = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            return funcionario;
    }

    public void cadastrar(FuncionarioCriacaoDto funcionarioCriacaoDto, String token) {
        final Empresa empresa = empresaRepository.findByCnpj(gerenciadorTokenJwt.obterNomeUsuarioDoToken(token)).get();
        final Funcionario novoFuncionario = FuncionarioMapper.of(funcionarioCriacaoDto);
        novoFuncionario.setIdEmpresa(empresa);
        repository.save(novoFuncionario);
    }

    public List<Funcionario> listaFuncionariosPorEmpresa(Integer idEmpresa) {
            Empresa empresa = empresaRepository.findById(idEmpresa).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            List<Funcionario> funcionarios = repository.findAllByIdEmpresa(empresa);
            return funcionarios;
    }

    public Integer buscarPorCpf(String cpf) {
        Funcionario funcionario = repository.findByCpf(cpf).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return funcionario.getId();
    }
}
