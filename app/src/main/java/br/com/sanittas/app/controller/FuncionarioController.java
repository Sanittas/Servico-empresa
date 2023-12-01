package br.com.sanittas.app.controller;

import br.com.sanittas.app.model.Funcionario;
import br.com.sanittas.app.service.FuncionarioServices;
import br.com.sanittas.app.service.funcionario.dto.FuncionarioCriacaoDto;
import br.com.sanittas.app.service.funcionario.dto.ListaFuncionario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/funcionarios")
@Slf4j
public class FuncionarioController {
    @Autowired
    private FuncionarioServices services;
    @GetMapping("/")
    public ResponseEntity<List<ListaFuncionario>> listar() {
        try{
            var response = services.listaFuncionarios();
            if (!response.isEmpty()){
                log.info("Funcionarios encontrados" + response);
                return ResponseEntity.status(200).body(response);
            }
            log.info("Nenhum funcionario encontrado");
            return ResponseEntity.status(204).body(response);
        }catch (ResponseStatusException e) {
            log.error("Erro ao buscar funcionarios", e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Integer id) {
        try{
            var funcionario = services.buscarPorId(id);
            log.info("Funcionario encontrado" + funcionario);
            return ResponseEntity.status(200).body(funcionario);
        }catch (ResponseStatusException e){
            log.info("Funcionario não encontrado");
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid FuncionarioCriacaoDto dados, HttpServletRequest request) {
        try{
            String requestTokenHeader = request.getHeader("Authorization");
            String jwtToken = "";
            if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
            }
            services.cadastrar(dados,jwtToken);
            log.info("Funcionario cadastrado");
            return ResponseEntity.status(201).build();
        }catch (ResponseStatusException e){
            log.error("Erro ao cadastrar funcionario. Exceção:" + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody @Valid Funcionario dados) {
        try{
            var funcionario = services.atualizar(id,dados);
            log.info("Funcionario atualizado" + funcionario);
            return ResponseEntity.status(200).body(funcionario);
        }catch (ResponseStatusException e){
            log.error("Erro ao atualizar funcionario" + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try{
            services.deletar(id);
            log.info("Funcionario deletado");
            return ResponseEntity.status(200).build();
        }catch (ResponseStatusException e){
            log.error("Erro ao deletar funcionario" + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @GetMapping("/empresa/{id}")
    public ResponseEntity<List<ListaFuncionario>> listarPorEmpresa(@PathVariable Integer id) {
        try{
            var response = services.listaFuncionariosPorEmpresa(id);
            if (!response.isEmpty()){
                log.info("Funcionarios encontrados" + response);
                return ResponseEntity.status(200).body(response);
            }
            log.info("Nenhum funcionario encontrado");
            return ResponseEntity.status(204).body(response);
        }catch (ResponseStatusException e) {
            log.error("Erro ao buscar funcionarios", e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

}