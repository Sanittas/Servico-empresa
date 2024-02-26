package br.com.sanittas.app.servicos.services.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AgendamentoCriacaoDto {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataAgendamento;
    @NotNull
    private Integer idServico;
    @NotNull
    private Integer idUsuario;
}
