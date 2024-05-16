package br.com.sanittas.app.servicos.services.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record VerificarDisponibilidadeDto(
        @NotNull
        Integer idServico,
        @NotNull
        Integer idFuncionario,
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dataHoraAgendamento
) {
}
