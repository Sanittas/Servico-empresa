package br.com.sanittas.app.servicos.services.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoCriacaoDto {
    private String descricao;
    private String areaSaude;
    @Positive
    private Double valor;
    private Integer duracaoEstimada;
}
