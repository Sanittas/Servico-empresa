package br.com.sanittas.app.empresa.services;

import br.com.sanittas.app.empresa.model.Empresa;
import br.com.sanittas.app.empresa.model.EnderecoEmpresa;
import br.com.sanittas.app.empresa.repository.EmpresaRepository;
import br.com.sanittas.app.empresa.services.dto.*;
import br.com.sanittas.app.empresa.services.dto.ListaEndereco;
import br.com.sanittas.app.servicos.repository.AvaliacaoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class EmpresaServices {
    private final EmpresaRepository repository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final PasswordEncoder passwordEncoder;

    public List<ListaEmpresaDto> listarEmpresas() {
        log.info("Listando todas as empresas.");
        List<Empresa> empresas = repository.findAll();
        List<ListaEmpresaDto> listaEmpresaDtos = new ArrayList<>();
        for (Empresa empresa : empresas) {
            List<ListaEndereco> listaEnderecos = new ArrayList<>();
            extrairEndereco(empresa, listaEnderecos);
            var empresaDto = new ListaEmpresaDto(
                    empresa.getId(),
                    empresa.getRazaoSocial(),
                    empresa.getCnpj(),
                    calcularAvaliacao(empresa),
                    listaEnderecos
            );
            listaEmpresaDtos.add(empresaDto);
        }
        log.info("Empresas listadas com sucesso.");
        return listaEmpresaDtos;
    }

    private Double calcularAvaliacao(Empresa empresa) {
        return avaliacaoRepository.calcularAvaliacao(empresa.getId());
    }

    public Empresa listarEmpresaPorId(Integer id) {
        log.info("Listando empresa com ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Empresa não encontrada."
                ));
    }

    private static void extrairEndereco(Empresa empresa, List<ListaEndereco> listaEnderecos) {
        for (EnderecoEmpresa endereco : empresa.getEnderecos()) {
            var enderecoDto = new ListaEndereco(
                    endereco.getId(),
                    endereco.getLogradouro(),
                    endereco.getNumero(),
                    endereco.getComplemento(),
                    endereco.getEstado(),
                    endereco.getCidade()
            );
            listaEnderecos.add(enderecoDto);
        }
    }


    public void atualizar(EmpresaCriacaoDto empresa, Integer id) {
        log.info("Atualizando empresa com ID: {}", id);
        var empresaAtualizada = repository.findById(id);
        if (empresaAtualizada.isPresent()) {
            empresaAtualizada.get().setRazaoSocial(empresa.razaoSocial());
            empresaAtualizada.get().setCnpj(empresa.cnpj());
            empresaAtualizada.get().setEmail(empresa.email());
            empresaAtualizada.get().setSenha(passwordEncoder.encode(empresa.senha()));
            repository.save(empresaAtualizada.get());
            log.info("Empresa atualizada com sucesso.");
        } else {
            log.warn("Tentativa de atualizar empresa com ID {}, mas não encontrada.", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada.");
        }
    }

    public void deletar(Integer id) {
        var empresa = repository.findById(id);
        if (empresa.isEmpty()){
            log.error("Tentativa de deletar empresa com ID {}, mas não encontrada.", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada.");
        }
        log.info("Deletando empresa com ID: {}", id);
        repository.deleteById(id);
        log.info("Empresa deletada com sucesso.");
    }

    //Manutenção
//    public String generateToken(String email) {
//        try {
//            log.info("Gerando token para o email: {}", email);
//
////            Empresa empresa = repository.findByEmail(email)
////                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
//
////            KeyBasedPersistenceTokenService tokenService = getInstanceFor(empresa);
////            Token token = tokenService.allocateToken(empresa.getEmail());
//
////            return token.getKey();
//            return "";
//        } catch (Exception e) {
//            log.error("Erro ao gerar token: {}", e.getMessage());
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
//        }
//    }
//
//    public void validarToken(String token) {
//        try {
//            log.info("Validando token");
//
//            PasswordTokenPublicData publicData = readPublicData(token);
//
//            if (isExpired(publicData)) {
//                throw new RuntimeException("Token expirado");
//            }
//        } catch (Exception e) {
//            log.error("Erro ao validar token: {}", e.getMessage());
//            throw e;
//        }
//    }
//
//    @SneakyThrows
//    public void alterarSenha(NovaSenhaDto novaSenhaDto) {
//        try {
//            log.info("Alterando senha com token");
//
//            PasswordTokenPublicData publicData = readPublicData(novaSenhaDto.getToken());
//
//            if (isExpired(publicData)) {
//
//                throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Token expirado");
//            }
//
//            Empresa empresa = repository.findByCnpj(publicData.getCnpj())
//                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//            empresa.setSenha(novaSenhaDto.getNovaSenha());
//            repository.save(empresa);
//        } catch (Exception e) {
//            log.error("Erro ao alterar senha: {}", e.getMessage());
//            throw e;
//        }
//    }
//
//    private boolean isExpired(PasswordTokenPublicData publicData) {
//        Instant createdAt = new Date(publicData.getCreateAtTimestamp()).toInstant();
//        Instant now = new Date().toInstant();
//        return createdAt.plus(Duration.ofMinutes(5)).isBefore(now);
//    }
//
//
//    private PasswordTokenPublicData readPublicData(String rawToken) {
//        String rawTokenDecoded = new String(Base64.getDecoder().decode(rawToken));
//        String[] tokenParts = rawTokenDecoded.split(":");
//        Long timestamp = Long.parseLong(tokenParts[0]);
//        String email = tokenParts[2];
//        return new PasswordTokenPublicData(email, timestamp);
//    }
}
