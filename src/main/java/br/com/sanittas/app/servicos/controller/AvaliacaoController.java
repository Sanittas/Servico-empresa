package br.com.sanittas.app.servicos.controller;

import br.com.sanittas.app.servicos.model.Avaliacao;
import br.com.sanittas.app.servicos.services.AvaliacaoServices;
import br.com.sanittas.app.servicos.services.dto.AvaliacaoDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
public class AvaliacaoController {
    private final AvaliacaoServices services;

    @PostMapping("/avaliar")
    public ResponseEntity<Avaliacao> avaliar(@RequestBody @Valid AvaliacaoDto avaliacaoDto) {
        try{
            Avaliacao avaliacao = services.salvar(avaliacaoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(avaliacao);
        }catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

}
