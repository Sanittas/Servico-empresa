package br.com.sanittas.app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Servico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "fk_categoria_servico")
    private CategoriaServico categoriaServico;
    @JsonManagedReference
    @OneToMany(mappedBy = "servico",orphanRemoval = true)
    private List<ServicoEmpresa> servicoEmpresa;
}
