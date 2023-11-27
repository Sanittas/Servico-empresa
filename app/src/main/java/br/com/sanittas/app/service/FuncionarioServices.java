package br.com.sanittas.app.service;

import br.com.sanittas.app.api.configuration.security.jwt.GerenciadorTokenJwt;
import br.com.sanittas.app.model.Empresa;
import br.com.sanittas.app.model.Funcionario;
import br.com.sanittas.app.repository.EmpresaRepository;
import br.com.sanittas.app.repository.FuncionarioRepository;
import br.com.sanittas.app.service.funcionario.dto.FuncionarioCriacaoDto;
import br.com.sanittas.app.service.funcionario.dto.FuncionarioMapper;
import br.com.sanittas.app.service.funcionario.dto.ListaFuncionario;
import br.com.sanittas.app.service.funcionario.dto.ListaFuncionarioAtualizacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

    public List<ListaFuncionario> listaFuncionarios() {
        var funcionarios = repository.findAll();
        List<ListaFuncionario> listaFunc = new ArrayList<>();
        for (Funcionario funcionario : funcionarios) {
            criarDtoFuncionarios(funcionario, listaFunc);
        }
        return listaFunc;
    }

    private static void criarDtoFuncionarios(Funcionario funcionario,  List<ListaFuncionario> listaFuncionarios) {
        var funcionarioDto = new ListaFuncionario(
                funcionario.getId(),
                funcionario.getFuncional(),
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getRg(),
                funcionario.getEmail(),
                funcionario.getNumeroRegistroAtuacao(),
                funcionario.getIdEmpresa()
        );
        listaFuncionarios.add(funcionarioDto);
    }

    public ListaFuncionarioAtualizacao atualizar(Integer id, Funcionario dados) {
        try{
        var funcionario = repository.findById(id);
        if (funcionario.isPresent()) {
            funcionario.get().setFuncional(dados.getFuncional());
            funcionario.get().setNome(dados.getNome());
            funcionario.get().setCpf(dados.getCpf());
            funcionario.get().setRg(dados.getRg());
            funcionario.get().setEmail(dados.getEmail());
            funcionario.get().setNumeroRegistroAtuacao(dados.getNumeroRegistroAtuacao());

            ListaFuncionarioAtualizacao FuncionarioDto = new ListaFuncionarioAtualizacao(
                    funcionario.get().getId(),
                    funcionario.get().getFuncional(),
                    funcionario.get().getNome(),
                    funcionario.get().getCpf(),
                    funcionario.get().getRg(),
                    funcionario.get().getEmail(),
                    funcionario.get().getNumeroRegistroAtuacao()
            );
            repository.save(funcionario.get());
            return FuncionarioDto;
        }
        return null;
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }

    public Funcionario buscarPorId(Integer id) {
        try{
            var funcionario = repository.findById(id);
            return funcionario.get();
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    public void cadastrar(FuncionarioCriacaoDto funcionarioCriacaoDto, String token) {
        final Empresa empresa = empresaRepository.findByCnpj(gerenciadorTokenJwt.obterNomeUsuarioDoToken(token)).get();
        final Funcionario novoFuncionario = FuncionarioMapper.of(funcionarioCriacaoDto);
        novoFuncionario.setIdEmpresa(empresa);
        repository.save(novoFuncionario);
    }

    public List<ListaFuncionario> listaFuncionariosPorEmpresa(Integer idEmpresa) {
        try {
            Empresa empresa = empresaRepository.findById(idEmpresa).get();
            List<Funcionario> funcionarios = repository.findAllByIdEmpresa(empresa);
            List<ListaFuncionario> listaFunc = new ArrayList<>();
            for (Funcionario funcionario : funcionarios) {
                criarDtoFuncionarios(funcionario, listaFunc);
            }
            return listaFunc;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
