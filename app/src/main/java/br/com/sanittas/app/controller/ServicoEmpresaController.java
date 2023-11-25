package br.com.sanittas.app.controller;

import br.com.sanittas.app.model.ServicoEmpresa;
import br.com.sanittas.app.service.ServicoEmpresaServices;
import br.com.sanittas.app.service.servicoEmpresa.dto.ServicoEmpresaCriacaoDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servico-empresa")
public class ServicoEmpresaController {

    @Autowired
    private ServicoEmpresaServices servicoEmpresaServices;

    @GetMapping("/")
    public ResponseEntity<List<ServicoEmpresa>> listar() {
        try {
            List<ServicoEmpresa> response = servicoEmpresaServices.listar();
            if (!response.isEmpty()) {
                return ResponseEntity.status(200).body(response);
            }
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid ServicoEmpresaCriacaoDto dados) {
        try {
            servicoEmpresaServices.cadastrar(dados);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
