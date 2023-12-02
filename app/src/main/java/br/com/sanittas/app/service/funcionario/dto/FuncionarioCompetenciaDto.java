package br.com.sanittas.app.service.funcionario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FuncionarioCompetenciaDto {
    private String experiencia;
    private String especializacao;
    private String nivel_proficiencia;
    private Integer fk_competencia;
    private Integer fk_funcionario;
}
