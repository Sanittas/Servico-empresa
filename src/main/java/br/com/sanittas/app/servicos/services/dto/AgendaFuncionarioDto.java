package br.com.sanittas.app.servicos.services.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AgendaFuncionarioDto(
        @NotNull
        Integer idFuncionario,
        @NotBlank
        String servico,
        @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate data
) {
}
