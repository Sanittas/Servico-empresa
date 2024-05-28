package br.com.sanittas.app.servicos.services.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoCriacaoDto {
    private String descricao;
    @NotBlank
    private String areaSaude;
    @Positive
    private Double valor;
    @NotNull
    private Integer duracaoEstimada;
    @NotNull
    private Integer empresaId;
}
