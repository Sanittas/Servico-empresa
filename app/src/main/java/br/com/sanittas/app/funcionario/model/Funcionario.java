package br.com.sanittas.app.funcionario.model;

import br.com.sanittas.app.empresa.model.Empresa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "Funcionario")
@Table(name = "funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank @Column(unique = true)
    private String funcional;
    @NotBlank
    private String nome;
    @CPF @Column(unique = true)
    private String cpf;
    @ManyToOne
    @JoinColumn(name = "fk_empresa")
    private Empresa fkEmpresa;
    private Boolean inativo;
    @NotBlank
    private String numeroRegistroAtuacao;
    @OneToMany(orphanRemoval = true)
    private List<Competencia> competencias = new ArrayList<>();
    @OneToMany(orphanRemoval = true)
    private List<ContatoFuncionario> contatos = new ArrayList<>();

    public void addCompetencia(Competencia competencia) {
        this.competencias.add(competencia);
    }

    public void addContato(ContatoFuncionario contato) {
        this.contatos.add(contato);
    }
}
