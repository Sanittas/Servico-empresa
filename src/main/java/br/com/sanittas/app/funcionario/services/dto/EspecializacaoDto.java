package br.com.sanittas.app.funcionario.services.dto;

import jakarta.validation.constraints.NotBlank;

public record EspecializacaoDto(
        @NotBlank
        String especializacao
) {
}
