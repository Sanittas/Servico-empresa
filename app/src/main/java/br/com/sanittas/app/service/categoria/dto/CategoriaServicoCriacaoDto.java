package br.com.sanittas.app.service.categoria.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaServicoCriacaoDto {
    @NotBlank
    private String areaSaude;
}
