package br.com.sanittas.app.funcionario.model;

import br.com.sanittas.app.empresa.model.Empresa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
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
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Competencia> competencias = new ArrayList<>();
    @OneToMany(mappedBy = "fkFuncionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContatoFuncionario> contatos = new ArrayList<>();

    public void addCompetencia(Competencia competencia) {
        this.competencias.add(competencia);
        competencia.setFuncionario(this);
    }

    public void addContato(ContatoFuncionario contato) {
        this.contatos.add(contato);
        contato.setFkFuncionario(this);
    }
}
