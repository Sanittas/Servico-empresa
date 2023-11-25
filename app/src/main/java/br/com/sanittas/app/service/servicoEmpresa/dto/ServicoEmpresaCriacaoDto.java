package br.com.sanittas.app.service.servicoEmpresa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoEmpresaCriacaoDto {
    @NotNull
    private Integer idEmpresa;
    @NotNull
    private Integer idServico;
    @NotNull @Positive
    private Double valorServico;
    @NotBlank
    private String duracaoEstimada;
    @NotBlank
    private String equipeResponsavel;
}
