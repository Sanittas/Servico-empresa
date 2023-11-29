package br.com.sanittas.app.controller;

import br.com.sanittas.app.model.FuncionarioCompetencia;
import br.com.sanittas.app.service.FuncionarioCompetenciaServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/funcionarios-competencias")
public class FuncionarioCompetenciaController {
    @Autowired
    private FuncionarioCompetenciaServices funcionarioCompetenciaServices;

    @GetMapping("/")
    public ResponseEntity<List<FuncionarioCompetencia>>listar() {
        try {
            var response = funcionarioCompetenciaServices.listar();
            if (response.isEmpty()) {
                return ResponseEntity.status(204).body(response);
            }
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<FuncionarioCompetencia> cadastrar(@RequestBody @Valid FuncionarioCompetencia funcionarioCompetencia) {
        try {
            var funcionarioNovo = funcionarioCompetenciaServices.cadastrar(funcionarioCompetencia);
            return ResponseEntity.status(201).body(funcionarioNovo);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioCompetencia> atualizar(@PathVariable Integer id, @RequestBody @Valid FuncionarioCompetencia funcionarioCompetencia) {
        try {
            var funcionarioAtualizado = funcionarioCompetenciaServices.atualizar(id, funcionarioCompetencia);
            return ResponseEntity.status(200).body(funcionarioAtualizado);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            funcionarioCompetenciaServices.deletar(id);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

}
