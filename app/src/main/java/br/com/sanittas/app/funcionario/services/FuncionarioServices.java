package br.com.sanittas.app.funcionario.services;

import br.com.sanittas.app.funcionario.model.ContatoFuncionario;
import br.com.sanittas.app.empresa.model.Empresa;
import br.com.sanittas.app.funcionario.model.Funcionario;
import br.com.sanittas.app.funcionario.repository.ContatoFuncionarioRepository;
import br.com.sanittas.app.empresa.repository.EmpresaRepository;
import br.com.sanittas.app.funcionario.repository.FuncionarioRepository;
import br.com.sanittas.app.funcionario.services.dto.FuncionarioCriacaoDto;
import br.com.sanittas.app.funcionario.services.dto.FuncionarioMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private ContatoFuncionarioRepository contatoFuncionarioRepository;

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
        funcionario.addContato(ContatoFuncionario.builder().email(dados.getEmail()).tel(null).build());
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

    //Empresa era obtida através de token jwt
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
        repository.save(novoFuncionario);
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
    //Empresa era obtida pelo token jwt
    public Integer countFuncionariosEmpresa(Integer idEmpresa) {
        final Empresa empresa =
                empresaRepository.findById(idEmpresa).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return repository.countByfkEmpresa(empresa);
    }

    //Erro ao cadastrar em lote pois foi retirado atributo RG

//    public void cadastrarFuncionarioEmLote(MultipartFile file, String jwtToken) {
//        try (InputStream inputStream = file.getInputStream();
//             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//
//            String linha;
//            while ((linha = reader.readLine()) != null) {
//                System.out.println(linha);
//                FuncionarioCriacaoDto func = new FuncionarioCriacaoDto(
//                        linha.substring(2,47).strip(),
//                        linha.substring(48,92).strip(),
//                        linha.substring(93,104).strip(),
//                        linha.substring(157,202).strip()
//                );
//                log.info("cadastrando funcionário: " + func);
//
//                cadastrar(func, jwtToken);
//                log.info("Funcionario cadastrado");
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//    }
}
