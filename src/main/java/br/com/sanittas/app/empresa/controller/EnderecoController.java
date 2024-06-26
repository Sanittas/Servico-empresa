package br.com.sanittas.app.empresa.controller;

import br.com.sanittas.app.api.configuration.security.roles.EmpresaRole;
import br.com.sanittas.app.empresa.model.EnderecoEmpresa;
import br.com.sanittas.app.empresa.services.EnderecoServices;
import br.com.sanittas.app.empresa.services.dto.EnderecoCriacaoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
@Slf4j
public class EnderecoController {

    @Autowired
    private EnderecoServices enderecoServices;

    /**
     * Lista os endereços de uma empresa pelo ID.
     *
     * @param id_empresa ID da empresa.
     * @return ResponseEntity<List<ListaEndereco>> - Lista de endereços.
     */
    @EmpresaRole
    @GetMapping("/{id_empresa}")
    public ResponseEntity<List<EnderecoEmpresa>> listarEnderecosPorEmpresa(@PathVariable Integer id_empresa) {
        try {
            log.info("Listando endereços da empresa com ID: {}", id_empresa);
            var response = enderecoServices.listarEnderecosPorEmpresa(id_empresa);
            if (!response.isEmpty()) {
                return ResponseEntity.status(200).body(response);
            }
            log.info("A empresa com ID {} não possui endereços cadastrados.", id_empresa);
            return ResponseEntity.status(204).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao listar endereços da empresa com ID: {}", id_empresa, e);
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    /**
     * Cadastra um novo endereço para uma empresa.
     *
     * @param endereco     DTO de criação do endereço.
     * @param empresa_id   ID da empresa.
     * @return ResponseEntity<Void> - Resposta HTTP.
     */
    @EmpresaRole
    @PostMapping("/{empresa_id}")
    public ResponseEntity<Void> cadastrarEnderecoEmpresa(@RequestBody EnderecoCriacaoDto endereco, @PathVariable Integer empresa_id) {
        try {
            log.info("Cadastrando endereço para a empresa com ID: {}", empresa_id);
            enderecoServices.cadastrarEnderecoEmpresa(endereco, empresa_id);
            return ResponseEntity.status(201).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao cadastrar endereço para a empresa com ID: {}", empresa_id, e);
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    /**
     * Atualiza um endereço de uma empresa.
     *
     * @param enderecoCriacaoDto DTO de atualização do endereço.
     * @param id                ID do endereço.
     * @return ResponseEntity<ListaEndereco> - Endereço atualizado.
     */
    @EmpresaRole
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoEmpresa> atualizarEnderecoEmpresa(@RequestBody EnderecoCriacaoDto enderecoCriacaoDto, @PathVariable Integer id) {
        try {
            log.info("Atualizando endereço com ID: {}", id);
            var endereco = enderecoServices.atualizar(enderecoCriacaoDto, id);
            return ResponseEntity.status(200).body(endereco);
        } catch (ResponseStatusException e) {
            log.error("Erro ao atualizar endereço com ID: {}", id, e);
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    /**
     * Deleta um endereço de uma empresa.
     *
     * @param id ID do endereço.
     * @return ResponseEntity<Void> - Resposta HTTP.
     */
    @EmpresaRole
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEnderecoEmpresa(@PathVariable Integer id) {
        try {
            log.info("Deletando endereço com ID: {}", id);
            enderecoServices.deletarEndereco(id);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao deletar endereço com ID: {}", id, e);
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }
}
