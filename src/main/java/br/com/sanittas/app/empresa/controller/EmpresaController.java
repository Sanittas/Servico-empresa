package br.com.sanittas.app.empresa.controller;

import br.com.sanittas.app.api.configuration.security.roles.EmpresaRole;
import br.com.sanittas.app.empresa.model.Empresa;
import br.com.sanittas.app.empresa.services.EmpresaServices;
import br.com.sanittas.app.empresa.services.dto.EmpresaCriacaoDto;
import br.com.sanittas.app.empresa.services.dto.ListaEmpresaDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/empresas")
@Slf4j
@AllArgsConstructor
public class EmpresaController {
    private final EmpresaServices services;


    @EmpresaRole
    @GetMapping("/")
    public ResponseEntity<List<ListaEmpresaDto>> listarEmpresas() {
        try {
            log.info("Recebida solicitação para listar empresas");
            List<ListaEmpresaDto> response = services.listarEmpresas();
            if (response.size() > 0) {
                log.info("Empresas listadas com sucesso");
                return ResponseEntity.status(200).body(response);
            }
            log.info("Nenhuma empresa encontrada");
            return ResponseEntity.status(204).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao listar empresas: {}", e.getMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @EmpresaRole
    @GetMapping("/{id}")
    public Empresa buscarEmpresaPorId(@PathVariable Integer id) {
        try {
            log.info("Recebida solicitação para buscar empresa com ID: {}", id);
            Empresa response = services.listarEmpresaPorId(id);
            log.info("Empresa encontrada com sucesso: {}", response.getRazaoSocial());
            return response;
        } catch (ResponseStatusException e) {
            log.error("Erro ao buscar empresa com ID {}: {}", id, e.getMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @EmpresaRole
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarEmpresa(@RequestBody @Valid EmpresaCriacaoDto empresa, @PathVariable Integer id) {
        try {
            log.info("Recebida solicitação para atualizar dados da empresa com ID {}: {}", id, empresa.razaoSocial());
            services.atualizar(empresa, id);
            log.info("Dados da empresa atualizados com sucesso: cnpj:{}\n razaoSocial:{}", empresa.cnpj(),empresa.razaoSocial());
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao atualizar empresa: {}", e.getMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    @EmpresaRole
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Integer id) {
        try {
            log.info("Recebida solicitação para deletar empresa com ID: {}", id);
            services.deletar(id);
            log.info("Empresa deletada com sucesso: ID {}", id);
            return ResponseEntity.status(200).build();
        } catch (ResponseStatusException e) {
            log.error("Erro ao deletar empresa: {}", e.getMessage());
            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
        }
    }

    // Manutenção
//    @PostMapping("/esqueci-senha")
//    public ResponseEntity<?> esqueciASenha(@RequestParam String email) {
//        try {
//            String token = services.generateToken(email);
//            emailServices.enviarEmailComToken(email, token);
//            return ResponseEntity.status(200).build();
//        } catch (ResponseStatusException e) {
//            log.info(e.getLocalizedMessage());
//            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
//        }
//    }
//
//    /**
//     * Valida um token para redefinição de senha.
//     *
//     * @param token O token a ser validado.
//     * @return Uma ResponseEntity indicando o sucesso ou falha da operação.
//     */
//    @GetMapping("/validarToken/{token}")
//    public ResponseEntity<?> validarToken(@PathVariable String token) {
//        try {
//            services.validarToken(token);
//            return ResponseEntity.status(200).build();
//        } catch (ResponseStatusException e) {
//            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
//        }
//    }
//
//    /**
//     * Altera a senha de um usuário.
//     *
//     * @param novaSenhaDto O DTO contendo a nova senha e o token de validação.
//     * @return Uma ResponseEntity indicando o sucesso ou falha da operação.
//     */
//    @PutMapping("/alterar-senha")
//    public ResponseEntity<?> alterarSenha(@RequestBody @Valid NovaSenhaDto novaSenhaDto) {
//        try {
//            services.alterarSenha(novaSenhaDto);
//            return ResponseEntity.status(200).build();
//        } catch (ResponseStatusException e) {
//            throw new ResponseStatusException(e.getStatusCode(), e.getReason());
//        }
//    }
}
