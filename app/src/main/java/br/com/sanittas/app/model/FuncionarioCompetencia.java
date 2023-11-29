package br.com.sanittas.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FuncionarioCompetencia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String experiencia;
    private String especializacao;
    private String nivel_proficiencia;
    @ManyToOne
    @JoinColumn(name = "fk_competencia")
    private Competencia competencia;
    @ManyToOne
    @JoinColumn(name = "fk_funcionario")
    private Funcionario funcionario;
}
