package br.com.sanittas.app.servicos.services.dto;

import jakarta.validation.constraints.NotNull;

public record AvaliacaoDto(
        @NotNull
        Integer agendamentoServico,
        @NotNull
        Integer avaliacao,
        String comentario
) {
}
