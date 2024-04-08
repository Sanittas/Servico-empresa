package br.com.sanittas.app.servicos.controller;

import br.com.sanittas.app.servicos.model.Servico;
import br.com.sanittas.app.servicos.services.ServicosServices;
import br.com.sanittas.app.servicos.services.dto.ServicoCriacaoDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/servicos")
@Slf4j
@CrossOrigin(origins = "*")
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
            return ResponseEntity.status(204).body(response);
        }catch (ResponseStatusException e) {
            log.error("Erro ao buscar servicos: " + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Integer id) {
        try{
            Servico response = services.buscarPorId(id);
            log.info("Servico encontrado" + response);
            return ResponseEntity.status(200).body(response);
        }catch (ResponseStatusException e) {
            log.error("Erro ao buscar servico: " + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

//    @GetMapping("/servico-empresa/categoria/")
//    public ResponseEntity<List<Servico>> listarServicoCategoria() {
//        try{
//            List<Servico> response = services.listarServicoCategoria();
//            if (!response.isEmpty()){
//                log.info("Servicos encontrados" + response);
//                return ResponseEntity.status(200).body(response);
//            }
//            log.info("Nenhum servico encontrado");
//            return ResponseEntity.status(204).body(response);
//        }catch (ResponseStatusException e) {
//            log.error("Erro ao buscar servicos: " + e.getLocalizedMessage());
//            throw new ResponseStatusException(e.getStatusCode());
//        }
//    }


    @PostMapping("/")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid ServicoCriacaoDto dados) {
        try{
            services.cadastrar(dados);
            log.info("Servico cadastrado com sucesso");
            return ResponseEntity.status(201).build();
        }catch (ResponseStatusException e){
            log.error("Erro ao cadastrar servico" + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody @Valid ServicoCriacaoDto dados) {
        try{
            services.atualizar(id, dados);
            log.info("Servico atualizado com sucesso");
            return ResponseEntity.status(200).build();
        }catch (ResponseStatusException e){
            log.error("Erro ao atualizar servico" + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try{
            services.deletar(id);
            log.info("Servico deletado com sucesso");
            return ResponseEntity.status(200).build();
        }catch (ResponseStatusException e){
            log.error("Erro ao deletar servico" + e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

}
