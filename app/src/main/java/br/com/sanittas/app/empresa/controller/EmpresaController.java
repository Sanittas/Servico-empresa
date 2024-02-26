package br.com.sanittas.app.empresa.controller;

import br.com.sanittas.app.empresa.model.Empresa;
import br.com.sanittas.app.mail.EmailServices;
import br.com.sanittas.app.empresa.services.EmpresaServices;
import br.com.sanittas.app.empresa.services.dto.EmpresaCriacaoDto;
import br.com.sanittas.app.empresa.services.dto.ListaEmpresa;
import br.com.sanittas.app.empresa.services.dto.NovaSenhaDto;
import br.com.sanittas.app.util.ListaObj;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/empresas")
@Slf4j
public class EmpresaController {

    @Autowired
    private EmpresaServices services;
    @Autowired
    private EmailServices emailServices;

    @GetMapping("/")
    public ResponseEntity<ListaObj<ListaEmpresa>> listarEmpresas() {
        try {
            log.info("Recebida solicitação para listar empresas");
            ListaObj<ListaEmpresa> response = services.listarEmpresas();
            if (response.getNroElem() > 0) {
                log.info("Empresas listadas com sucesso");
                return ResponseEntity.status(200).body(response);
            }
            log.info("Nenhuma empresa encontrada");
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            log.error("Erro ao listar empresas: {}", e.getMessage());
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/{id}")
    public Empresa buscarEmpresaPorId(@PathVariable Integer id) {
        try {
            log.info("Recebida solicitação para buscar empresa com ID: {}", id);
            Empresa response = services.listarEmpresaPorId(id);
            log.info("Empresa encontrada com sucesso: {}", response.getRazaoSocial());
            return response;
        } catch (ResponseStatusException e) {
            log.error("Erro ao buscar empresa com ID {}: {}", id, e.getMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PostMapping("/cadastrar/")
    public ResponseEntity<Void> cadastrarEmpresa(@RequestBody @Valid EmpresaCriacaoDto empresa) {
        try {
            log.info("Recebida solicitação para cadastrar uma nova empresa: {}", empresa.razaoSocial());
            services.cadastrar(empresa);
            log.info("Empresa cadastrada com sucesso: {}", empresa.razaoSocial());
            return ResponseEntity.status(201).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao cadastrar empresa: {}", e.getMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarEmpresa(@RequestBody @Valid EmpresaCriacaoDto empresa, @PathVariable Integer id) {
        try {
            log.info("Recebida solicitação para atualizar dados da empresa com ID {}: {}", id, empresa.razaoSocial());
            services.atualizar(empresa, id);
            log.info("Dados da empresa atualizados com sucesso: cnpj:{}\n razaoSocial:{}", empresa.cnpj(),empresa.razaoSocial());
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao atualizar empresa: {}", e.getMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Integer id) {
        try {
            log.info("Recebida solicitação para deletar empresa com ID: {}", id);
            services.deletar(id);
            log.info("Empresa deletada com sucesso: ID {}", id);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao deletar empresa: {}", e.getMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @GetMapping("/export")
    public ResponseEntity<?> gravaArquivoCsv() {
        try {
            log.info("Recebida solicitação para exportar dados para arquivo CSV");
            services.gravaArquivosCsv(services.listarEmpresas());
            log.info("Exportação de dados para arquivo CSV concluída com sucesso");
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao exportar dados para arquivo CSV: {}", e.getMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PostMapping("/ordenar-razao-social")
    public ResponseEntity<Void> ordenarPorRazaoSocial() {
        try {
            log.info("Recebida solicitação para ordenar empresas por razão social");
            services.ordenarPorRazaoSocial();
            log.info("Empresas ordenadas por razão social com sucesso");
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao ordenar empresas por razão social: {}", e.getMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @GetMapping("/pesquisa-razao-social/{razaoSocial}")
    public ResponseEntity<Integer> pesquisaBinariaRazaoSocial(@PathVariable String razaoSocial) {
        try {
            log.info("Recebida solicitação para pesquisa binária por razão social: {}", razaoSocial);
            Integer response = services.pesquisaBinariaRazaoSocial(razaoSocial);
            log.info("Resultado da pesquisa binária por razão social: {}", response);
            return ResponseEntity.status(200).body(response);
        } catch (ResponseStatusException e) {
            log.error("Erro na pesquisa binária por razão social: {}", e.getMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> esqueciASenha(@RequestParam String email) {
        try {
            String token = services.generateToken(email);
            emailServices.enviarEmailComToken(email, token);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            log.info(e.getLocalizedMessage());
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    /**
     * Valida um token para redefinição de senha.
     *
     * @param token O token a ser validado.
     * @return Uma ResponseEntity indicando o sucesso ou falha da operação.
     */
    @GetMapping("/validarToken/{token}")
    public ResponseEntity<?> validarToken(@PathVariable String token) {
        try {
            services.validarToken(token);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }

    /**
     * Altera a senha de um usuário.
     *
     * @param novaSenhaDto O DTO contendo a nova senha e o token de validação.
     * @return Uma ResponseEntity indicando o sucesso ou falha da operação.
     */
    @PutMapping("/alterar-senha")
    public ResponseEntity<?> alterarSenha(@RequestBody @Valid NovaSenhaDto novaSenhaDto) {
        try {
            services.alterarSenha(novaSenhaDto);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(e.getStatusCode());
        }
    }
}
