package br.com.sanittas.app.controller;

import br.com.sanittas.app.model.CategoriaServico;
import br.com.sanittas.app.service.CategoriaServicoServices;
import br.com.sanittas.app.service.categoria.dto.CategoriaServicoCriacaoDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/categorias-servicos")
public class CategoriaServicoController {
    @Autowired
    private CategoriaServicoServices categoriaServicoServices;

    @GetMapping("/")
    public ResponseEntity<List<CategoriaServico>> listar() {
        try {
            List<CategoriaServico> response = categoriaServicoServices.listar();
            if (!response.isEmpty()) {
                return ResponseEntity.status(200).body(response);
            }
            return ResponseEntity.status(204).body(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid CategoriaServicoCriacaoDto categoriaServico) {
        try {
            categoriaServicoServices.cadastrar(categoriaServico);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody @Valid CategoriaServicoCriacaoDto categoriaServico) {
        try {
            categoriaServicoServices.atualizar(id, categoriaServico);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            categoriaServicoServices.deletar(id);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }
}
