package br.com.sanittas.app.service.empresa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public record EmpresaCriacaoDto(
        @NotBlank
        String razaoSocial,
        @CNPJ
        String cnpj,
        @Email
        String email,
        @Size(min = 8, max = 20)
        String senha
) {
}
