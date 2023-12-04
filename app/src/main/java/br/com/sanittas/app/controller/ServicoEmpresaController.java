package br.com.sanittas.app.controller;

import br.com.sanittas.app.model.ServicoEmpresa;
import br.com.sanittas.app.service.ServicoEmpresaServices;
import br.com.sanittas.app.service.servicoEmpresa.dto.ServicoEmpresaCriacaoDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/servicos-empresas")
@SecurityRequirement(name = "bearer-key") // Requisito de segurança para autenticação JWT
@Slf4j
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
        } catch (ResponseStatusException e) {
            log.error(e.getLocalizedMessage());
            log.error(e.getReason());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ServicoEmpresa>> listarPorEmpresa(@PathVariable Integer id) {
        try{
            List<ServicoEmpresa> response = servicoEmpresaServices.listarPorEmpresa(id);
            if (!response.isEmpty()){
                log.info("Servicos encontrados" + response);
                return ResponseEntity.status(200).body(response);
            }
            log.info("Nenhum servico encontrado");
            return ResponseEntity.status(204).body(response);
        }catch (ResponseStatusException e) {
            log.error("Erro ao buscar servicos: " + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid ServicoEmpresaCriacaoDto dados) {
        try {
            servicoEmpresaServices.cadastrar(dados);
            return ResponseEntity.status(201).build();
        } catch (ResponseStatusException e) {
            log.error(e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody @Valid ServicoEmpresaCriacaoDto dados) {
        try {
            servicoEmpresaServices.atualizar(id, dados);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            log.error(e.getLocalizedMessage());
            log.error(e.getReason());
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
