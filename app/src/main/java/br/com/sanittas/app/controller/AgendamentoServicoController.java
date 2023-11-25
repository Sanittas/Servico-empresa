package br.com.sanittas.app.controller;

import br.com.sanittas.app.model.AgendamentoServico;
import br.com.sanittas.app.service.AgendamentoServicoServices;
import br.com.sanittas.app.service.agendamento.dto.AgendamentoCriacaoDto;
import br.com.sanittas.app.service.agendamento.dto.ListaAgendamento;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoServicoController {
    @Autowired
    private AgendamentoServicoServices services;

    @GetMapping("/")
    public ResponseEntity<List<ListaAgendamento>>listar() {
        try {
            List<ListaAgendamento> response = services.listar();
            if (!response.isEmpty()) {
                return ResponseEntity.status(200).body(response);
            }
            return ResponseEntity.status(204).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid AgendamentoCriacaoDto dados) {
        try {
            services.cadastrar(dados);
            return ResponseEntity.status(201).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody @Valid AgendamentoCriacaoDto dados) {
        try {
            services.atualizar(id, dados);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            services.deletar(id);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }
}
