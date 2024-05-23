package br.com.sanittas.app.funcionario.controller;

import br.com.sanittas.app.funcionario.model.AreaSaude;
import br.com.sanittas.app.funcionario.services.AreaSaudeServices;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/area-saude")
@AllArgsConstructor
public class AreaSaudeController {
    private final AreaSaudeServices services;

    @GetMapping("/")
    public ResponseEntity<List<AreaSaude>> getAll() {
        try{
            List<AreaSaude> response = services.getAll();
            if (response.isEmpty()) {
                return ResponseEntity.status(204).build();
            } else {
                return ResponseEntity.status(200).body(response);
            }
        }catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }
}
