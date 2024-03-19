package br.com.sanittas.app.funcionario.services;

import br.com.sanittas.app.funcionario.model.Competencia;
import br.com.sanittas.app.funcionario.model.ContatoFuncionario;
import br.com.sanittas.app.empresa.model.Empresa;
import br.com.sanittas.app.funcionario.model.Funcionario;
import br.com.sanittas.app.funcionario.repository.CompetenciaRepository;
import br.com.sanittas.app.funcionario.repository.ContatoFuncionarioRepository;
import br.com.sanittas.app.empresa.repository.EmpresaRepository;
import br.com.sanittas.app.funcionario.repository.FuncionarioRepository;
import br.com.sanittas.app.funcionario.services.dto.FuncionarioCriacaoDto;
import br.com.sanittas.app.funcionario.services.dto.FuncionarioMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class FuncionarioServices {
    private final CompetenciaRepository competenciaRepository;
    private final FuncionarioRepository repository;
    private final EmpresaRepository empresaRepository;
    private final ContatoFuncionarioRepository contatoFuncionarioRepository;

    public List<Funcionario> listaFuncionarios() {
        var funcionarios = repository.findAll();
        return funcionarios;
    }

    public Funcionario atualizar(Integer id, FuncionarioCriacaoDto dados) {
        var funcionario = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (repository.existsByFuncional(dados.getFuncional())) {
            log.error("Funcional já cadastrado");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        funcionario.setFuncional(dados.getFuncional());
        funcionario.setNome(dados.getNome());
        if (repository.existsByCpf(dados.getCpf())) {
            log.error("CPF já cadastrado");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        funcionario.setCpf(dados.getCpf());
        if (contatoFuncionarioRepository.existsByEmail(dados.getEmail())) {
            log.error("Email já cadastrado");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        funcionario.addContato(ContatoFuncionario.builder().email(dados.getEmail()).tel(dados.getTelefone()).build());
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

    public void cadastrar(@Valid FuncionarioCriacaoDto funcionarioCriacaoDto) {
        final Empresa empresa =
                empresaRepository.findById(funcionarioCriacaoDto.getEmpresaId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (repository.existsByFuncional(funcionarioCriacaoDto.getFuncional())) {
            log.error("Funcional já cadastrado");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (repository.existsByCpf(funcionarioCriacaoDto.getCpf())) {
            log.error("CPF já cadastrado");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (contatoFuncionarioRepository.existsByEmail(funcionarioCriacaoDto.getEmail())) {
            log.error("Email já cadastrado");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        final Funcionario novoFuncionario = FuncionarioMapper.of(funcionarioCriacaoDto);
        novoFuncionario.setFkEmpresa(empresa);
        final Funcionario funcSalvo = repository.save(novoFuncionario);
        final Competencia competencia = Competencia.builder()
                .especializacao(funcionarioCriacaoDto.getEspecializacao())
                .registroAtuacao(funcionarioCriacaoDto.getRegistroAtuacao())
                .build();
        final ContatoFuncionario contatoFuncionario = ContatoFuncionario.builder()
                .email(funcionarioCriacaoDto.getEmail())
                .tel(funcionarioCriacaoDto.getTelefone()).build();
        funcSalvo.addContato(contatoFuncionario);
        funcSalvo.addCompetencia(competencia);
        contatoFuncionarioRepository.save(contatoFuncionario);
        competenciaRepository.save(competencia);
        repository.save(funcSalvo);
    }

    public List<Funcionario> listaFuncionariosPorEmpresa(Integer idEmpresa) {
            Empresa empresa = empresaRepository.findById(idEmpresa).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            List<Funcionario> funcionarios = repository.findAllByfkEmpresa(empresa);
            return funcionarios;
    }

    public Integer buscarPorCpf(String cpf) {
        Funcionario funcionario = repository.findByCpf(cpf).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return funcionario.getId();
    }

    public Integer countFuncionariosEmpresa(Integer idEmpresa) {
        final Empresa empresa =
                empresaRepository.findById(idEmpresa).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return repository.countByfkEmpresa(empresa);
    }

    public List<ContatoFuncionario> listaContatoFuncionario(Integer id) {
        final Funcionario funcionario =
                repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return contatoFuncionarioRepository.findAllByFkFuncionario_Id(id);
    }
}
