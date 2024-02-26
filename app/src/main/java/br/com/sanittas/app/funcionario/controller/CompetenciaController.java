package br.com.sanittas.app.funcionario.controller;

import br.com.sanittas.app.funcionario.model.Competencia;
import br.com.sanittas.app.funcionario.services.CompetenciaServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/competencias")
public class CompetenciaController {
    @Autowired
    private CompetenciaServices competenciaServices;

    @GetMapping("/")
    public ResponseEntity<List<Competencia>> listar() {
        try{
            var response = competenciaServices.listar();
            if (response.isEmpty()) {
                return ResponseEntity.status(204).body(response);
            }
            return ResponseEntity.status(200).body(response);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Competencia> cadastrar(@RequestBody Competencia competencia) {
        try{
            var response = competenciaServices.cadastrar(competencia);
            return ResponseEntity.status(201).body(response);
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Competencia> atualizar(@PathVariable Integer id, @RequestBody Competencia competencia) {
//        try{
//            var response = competenciaServices.atualizar(id, competencia);
//            return ResponseEntity.status(200).body(response);
//        } catch (ResponseStatusException e) {
//            throw new ResponseStatusException(e.getStatusCode());
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try{
            competenciaServices.deletar(id);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }
}
