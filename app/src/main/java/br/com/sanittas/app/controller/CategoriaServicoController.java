package br.com.sanittas.app.controller;

import br.com.sanittas.app.model.CategoriaServico;
import br.com.sanittas.app.service.CategoriaServicoServices;
import br.com.sanittas.app.service.categoria.dto.CategoriaServicoCriacaoDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController("/categoria-servico")
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
}
