package br.com.sanittas.app.service.servicoEmpresa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoEmpresaCriacaoDto {
    @NotNull
    private Integer idEmpresa;
    @NotNull
    private Integer idServico;
    @NotBlank
    private String valorServico;
    @NotBlank
    private String duracaoEstimada;
    @NotBlank
    private String equipeResponsavel;
}
