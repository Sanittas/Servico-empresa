package br.com.sanittas.app.controller;

import br.com.sanittas.app.model.Servico;
import br.com.sanittas.app.service.ServicosServices;
import br.com.sanittas.app.service.servico.dto.ServicoCriacaoDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/servicos")
@Slf4j
public class ServicosController {
    @Autowired
    private ServicosServices services;

    @GetMapping("/")
    public ResponseEntity<?> listar() {
        try{
            List<Servico> response = services.listar();
            if (!response.isEmpty()){
                log.info("Servicos encontrados" + response);
                return ResponseEntity.status(200).body(response);
            }
            log.info("Nenhum servico encontrado");
            return ResponseEntity.status(204).build();
        }catch (Exception e) {
            log.error("Erro ao buscar servicos", e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid ServicoCriacaoDto dados) {
        try{
            services.cadastrar(dados);
            log.info("Servico cadastrado com sucesso");
            return ResponseEntity.status(201).build();
        }catch (Exception e){
            log.error("Erro ao cadastrar servico", e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

}
