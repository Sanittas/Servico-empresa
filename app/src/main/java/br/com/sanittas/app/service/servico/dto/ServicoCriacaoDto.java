package br.com.sanittas.app.service.servico.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoCriacaoDto {
    private String descricao;
    private Integer fkCategoriaServico;
}
