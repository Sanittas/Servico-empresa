package br.com.sanittas.app.servicos.controller;

import br.com.sanittas.app.servicos.model.AgendamentoServico;
import br.com.sanittas.app.servicos.services.AgendamentoServicoServices;
import br.com.sanittas.app.servicos.services.dto.AgendamentoCriacaoDto;
import br.com.sanittas.app.servicos.services.dto.VerificarDisponibilidadeDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@Slf4j
@AllArgsConstructor
public class AgendamentoServicoController {

    private final AgendamentoServicoServices services;

    @GetMapping("/")
    public ResponseEntity<List<AgendamentoServico>> listar() {
        try {
            List<AgendamentoServico> response = services.listar();
            if (!response.isEmpty()) {
                return ResponseEntity.status(200).body(response);
            }
            return ResponseEntity.status(204).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid AgendamentoCriacaoDto dados) {
        try {
            services.cadastrar(dados);
            return ResponseEntity.status(201).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao cadastrar agendamento: " + e.getMessage());
            log.error(e.getReason());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<AgendamentoServico>> listarAgendamentosPorUsuario(@PathVariable Integer id) {
        try {
            List<AgendamentoServico> response = services.listarAgendamentosPorUsuario(id);
            if (!response.isEmpty()) {
                return ResponseEntity.status(200).body(response);
            }
            return ResponseEntity.status(204).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @GetMapping("/verificar-disponibilidade")
    public ResponseEntity<String> verificarDisponibilidadeAgendamento(@Valid @RequestBody VerificarDisponibilidadeDto dados) {
        try {
            return ResponseEntity.status(200).body(services.verificarDisponibilidade(dados));
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody @Valid AgendamentoCriacaoDto dados) {
        try {
            services.atualizar(id, dados);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            services.deletar(id);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }
}
