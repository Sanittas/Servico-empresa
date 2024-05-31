package br.com.sanittas.app.servicos.controller;

import br.com.sanittas.app.api.configuration.security.roles.EmpresaRole;
import br.com.sanittas.app.servicos.model.Servico;
import br.com.sanittas.app.servicos.services.ServicosServices;
import br.com.sanittas.app.servicos.services.dto.AgendaFuncionarioDto;
import br.com.sanittas.app.servicos.services.dto.ServicoCriacaoDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/servicos")
@Slf4j
@AllArgsConstructor
public class ServicosController {
    private final ServicosServices services;

    @GetMapping("/")
    public ResponseEntity<?> listar() {
        try {
            List<Servico> response = services.listar();
            if (!response.isEmpty()) {
                log.info("Servicos encontrados" + response);
                return ResponseEntity.status(200).body(response);
            }
            log.info("Nenhum servico encontrado");
            return ResponseEntity.status(204).body(response);
        } catch (ResponseStatusException e) {
            log.error("Erro ao buscar servicos: " + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @GetMapping("/agenda-funcionario")
    public ResponseEntity<List<String>> listarAgendaFuncionario(@RequestBody @Valid AgendaFuncionarioDto dados) {
        try {
            List<String> response = services.listarAgendaFuncionario(dados);
            if (!response.isEmpty()) {
                log.info("Agenda de funcionarios encontrada" + response);
                return ResponseEntity.status(200).body(response);
            }
            log.info("Nenhuma agenda de funcionario encontrada");
            return ResponseEntity.status(204).body(response);
        } catch (ResponseStatusException e) {
            log.error("Erro ao buscar agenda de funcionarios: " + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @GetMapping("/servicos-empresa/{empresaId}")
    public ResponseEntity<List<Servico>> getServicosPorEmpresa(@PathVariable Integer empresaId) {
        try {
            List<Servico> response = services.getServicosPorEmpresa(empresaId);
            if (!response.isEmpty()) {
                log.info("Servicos encontrados" + response);
                return ResponseEntity.status(200).body(response);
            }
            log.info("Nenhum servico encontrado");
            return ResponseEntity.status(204).body(response);
        } catch (ResponseStatusException e) {
            log.error("Erro ao buscar servicos: " + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Integer id) {
        try {
            Servico response = services.buscarPorId(id);
            log.info("Servico encontrado" + response);
            return ResponseEntity.status(200).body(response);
        } catch (ResponseStatusException e) {
            log.error("Erro ao buscar servico: " + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

//    @GetMapping("/servico-empresa/categoria/")
//    public ResponseEntity<List<Servico>> listarServicoCategoria() {
//        try{
//            List<Servico> response = services.listarServicoCategoria();
//            if (!response.isEmpty()){
//                log.info("Servicos encontrados" + response);
//                return ResponseEntity.status(200).body(response);
//            }
//            log.info("Nenhum servico encontrado");
//            return ResponseEntity.status(204).body(response);
//        }catch (ResponseStatusException e) {
//            log.error("Erro ao buscar servicos: " + e.getLocalizedMessage());
//            throw new ResponseStatusException(e.getStatusCode());
//        }
//    }


    @EmpresaRole
    @PostMapping("/")
    public ResponseEntity<Servico> cadastrar(@RequestBody @Valid ServicoCriacaoDto dados) {
        try {
            Servico response = services.cadastrar(dados);
            log.info("Servico cadastrado com sucesso");
            return ResponseEntity.status(201).body(response);
        } catch (ResponseStatusException e) {
            log.error("Erro ao cadastrar servico" + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @EmpresaRole
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody @Valid ServicoCriacaoDto dados) {
        try {
            services.atualizar(id, dados);
            log.info("Servico atualizado com sucesso");
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao atualizar servico" + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @EmpresaRole
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            services.deletar(id);
            log.info("Servico deletado com sucesso");
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao deletar servico" + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }
}
