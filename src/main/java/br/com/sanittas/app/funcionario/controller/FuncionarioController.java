package br.com.sanittas.app.funcionario.controller;

import br.com.sanittas.app.api.configuration.security.roles.EmpresaRole;
import br.com.sanittas.app.funcionario.model.ContatoFuncionario;
import br.com.sanittas.app.funcionario.model.Funcionario;
import br.com.sanittas.app.funcionario.services.FuncionarioServices;
import br.com.sanittas.app.funcionario.services.dto.EspecializacaoDto;
import br.com.sanittas.app.funcionario.services.dto.FuncionarioCriacaoDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@Slf4j
@AllArgsConstructor
public class FuncionarioController {
    private final FuncionarioServices services;

    @EmpresaRole
    @GetMapping("/")
    public ResponseEntity<List<Funcionario>> listar() {
        try {
            var response = services.listaFuncionarios();
            if (!response.isEmpty()) {
                log.info("Funcionarios encontrados" + response);
                return ResponseEntity.status(200).body(response);
            }
            log.info("Nenhum funcionario encontrado");
            return ResponseEntity.status(204).body(response);
        } catch (ResponseStatusException e) {
            log.error("Erro ao buscar funcionarios", e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @PostMapping("/servico")
    public ResponseEntity<List<Funcionario>> listarFuncionariosPorServico(@RequestBody @Valid EspecializacaoDto especializacao) {
        try {
            List<Funcionario> response = services.listaFuncionariosPorServico(especializacao.especializacao());
            if (!response.isEmpty()) {
                log.info("Funcionarios encontrados" + response);
                return ResponseEntity.status(200).body(response);
            }
            log.info("Nenhum funcionario encontrado");
            return ResponseEntity.status(204).body(response);
        } catch (ResponseStatusException e) {
            log.error("Erro ao buscar funcionarios", e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @EmpresaRole
    @GetMapping("/count-empresa/{idEmpresa}")
    public ResponseEntity<Integer> countFuncionariosEmpresa(@PathVariable Integer idEmpresa) {
        try {
            var response = services.countFuncionariosEmpresa(idEmpresa);
            log.info("Quantidade de funcionarios encontrados: " + response);
            return ResponseEntity.status(200).body(response);
        } catch (ResponseStatusException e) {
            log.error("Erro ao buscar funcionarios", e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @EmpresaRole
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Integer> getIdByCpf(@PathVariable String cpf) {
        try {
            var funcionario = services.buscarPorCpf(cpf);
            log.info("Funcionario encontrado" + funcionario);
            return ResponseEntity.status(200).body(funcionario);
        } catch (ResponseStatusException e) {
            log.info("Funcionario não encontrado");
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @EmpresaRole
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Integer id) {
        try {
            var funcionario = services.buscarPorId(id);
            log.info("Funcionario encontrado" + funcionario);
            return ResponseEntity.status(200).body(funcionario);
        } catch (ResponseStatusException e) {
            log.info("Funcionario não encontrado");
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @EmpresaRole
    @PostMapping("/")
    public ResponseEntity<Funcionario> cadastrar(@RequestBody @Valid FuncionarioCriacaoDto dados) {
        try {
            Funcionario func = services.cadastrar(dados);
            log.info("Funcionario cadastrado");
            return ResponseEntity.status(201).body(func);
        } catch (ResponseStatusException e) {
            log.error("Erro ao cadastrar funcionario. Exceção:" + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @EmpresaRole
    @GetMapping("/contato/{id}")
    public ResponseEntity<List<ContatoFuncionario>> listarContatoFuncionario(@PathVariable Integer id) {
        try {
            var response = services.listaContatoFuncionario(id);
            if (!response.isEmpty()) {
                log.info("Contatos encontrados" + response);
                return ResponseEntity.status(200).body(response);
            }
            log.info("Nenhum contato encontrado");
            return ResponseEntity.status(204).body(response);
        } catch (ResponseStatusException e) {
            log.error("Erro ao buscar contatos", e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @EmpresaRole
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody @Valid FuncionarioCriacaoDto dados) {
        try {
            var funcionario = services.atualizar(id, dados);
            log.info("Funcionario atualizado" + funcionario);
            return ResponseEntity.status(200).body(funcionario);
        } catch (ResponseStatusException e) {
            log.error("Erro ao atualizar funcionario" + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @EmpresaRole
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            services.deletar(id);
            log.info("Funcionario deletado");
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao deletar funcionario" + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @GetMapping("/empresa/{id}")
    public ResponseEntity<List<Funcionario>> listarPorEmpresa(@PathVariable Integer id) {
        try {
            var response = services.listaFuncionariosPorEmpresa(id);
            if (!response.isEmpty()) {
                log.info("Funcionarios encontrados" + response);
                return ResponseEntity.status(200).body(response);
            }
            log.info("Nenhum funcionario encontrado");
            return ResponseEntity.status(204).body(response);
        } catch (ResponseStatusException e) {
            log.error("Erro ao buscar funcionarios", e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

}
