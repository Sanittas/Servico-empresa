package br.com.sanittas.app.pagamento.services.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;

public record SalvarPagamentoDto(
        @NotNull
        Integer idAgendamento,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate dataPagamento,
        @NotNull
        Double valor
) {
}
