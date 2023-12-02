package br.com.sanittas.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class ServicoEmpresa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "fkEmpresa")
    private Empresa empresa;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "fkServico")
    private Servico servico;
    private Double valorServico;
    private String duracaoEstimada;
    private String equipeResponsavel;
    @JsonManagedReference
    @OneToMany(mappedBy = "servicoEmpresa",orphanRemoval = true)
    private List<AgendamentoServico> agendamentoServico;
}
