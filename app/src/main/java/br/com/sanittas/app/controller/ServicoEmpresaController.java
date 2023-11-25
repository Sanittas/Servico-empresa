package br.com.sanittas.app.controller;

import br.com.sanittas.app.model.ServicoEmpresa;
import br.com.sanittas.app.service.ServicoEmpresaServices;
import br.com.sanittas.app.service.servicoEmpresa.dto.ListaServicoEmpresaDto;
import br.com.sanittas.app.service.servicoEmpresa.dto.ServicoEmpresaCriacaoDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/servicos-empresas")
public class ServicoEmpresaController {

    @Autowired
    private ServicoEmpresaServices servicoEmpresaServices;

    @GetMapping("/")
    public ResponseEntity<List<ListaServicoEmpresaDto>> listar() {
        try {
            List<ListaServicoEmpresaDto> response = servicoEmpresaServices.listar();
            if (!response.isEmpty()) {
                return ResponseEntity.status(200).body(response);
            }
            return ResponseEntity.status(204).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid ServicoEmpresaCriacaoDto dados) {
        try {
            servicoEmpresaServices.cadastrar(dados);
            return ResponseEntity.status(201).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody @Valid ServicoEmpresaCriacaoDto dados) {
        try {
            servicoEmpresaServices.atualizar(id, dados);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            servicoEmpresaServices.deletar(id);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }
}
