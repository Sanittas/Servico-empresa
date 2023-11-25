package br.com.sanittas.app.service.agendamento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AgendamentoCriacaoDto {
    @NotNull
    private LocalDateTime dataAgendamento;
    @NotNull
    private Integer idServicoEmpresa;
    @NotNull
    private Integer idUsuario;
}
