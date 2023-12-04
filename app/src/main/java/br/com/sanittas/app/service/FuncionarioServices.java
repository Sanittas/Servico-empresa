package br.com.sanittas.app.service;

import br.com.sanittas.app.api.configuration.security.jwt.GerenciadorTokenJwt;
import br.com.sanittas.app.model.Empresa;
import br.com.sanittas.app.model.Funcionario;
import br.com.sanittas.app.repository.EmpresaRepository;
import br.com.sanittas.app.repository.FuncionarioRepository;
import br.com.sanittas.app.service.funcionario.dto.FuncionarioCriacaoDto;
import br.com.sanittas.app.service.funcionario.dto.FuncionarioMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        if (repository.existsByRg(dados.getRg())) {
            log.error("RG já cadastrado");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        funcionario.setRg(dados.getRg());
        if (repository.existsByEmail(dados.getEmail())) {
            log.error("Email já cadastrado");
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

    public void cadastrar(@Valid FuncionarioCriacaoDto funcionarioCriacaoDto, String token) {
        final Empresa empresa = empresaRepository.findByCnpj(gerenciadorTokenJwt.obterNomeUsuarioDoToken(token)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (repository.existsByFuncional(funcionarioCriacaoDto.getFuncional())) {
            log.error("Funcional já cadastrado");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (repository.existsByCpf(funcionarioCriacaoDto.getCpf())) {
            log.error("CPF já cadastrado");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (repository.existsByRg(funcionarioCriacaoDto.getRg())) {
            log.error("RG já cadastrado");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (repository.existsByEmail(funcionarioCriacaoDto.getEmail())) {
            log.error("Email já cadastrado");
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
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

    public Integer countFuncionariosEmpresa(String jwtToken) {
        final Empresa empresa = empresaRepository.findByCnpj(gerenciadorTokenJwt.obterNomeUsuarioDoToken(jwtToken)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return repository.countByIdEmpresa(empresa);
    }

    public void cadastrarFuncionarioEmLote(MultipartFile file, String jwtToken) {
        try (InputStream inputStream = file.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
                FuncionarioCriacaoDto func = new FuncionarioCriacaoDto(
                        linha.substring(2,47).strip(),
                        linha.substring(48,92).strip(),
                        linha.substring(93,104).strip(),
                        linha.substring(104,113).strip(),
                        linha.substring(113,157).strip(),
                        linha.substring(157,202).strip()
                );
                log.info("cadastrando funcionário: " + func);

                cadastrar(func, jwtToken);
                log.info("Funcionario cadastrado");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
