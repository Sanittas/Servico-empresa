package br.com.sanittas.app.servicos.model;

import br.com.sanittas.app.empresa.model.Empresa;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "servico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Servico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servico")
    private Integer id;
    private String descricao;
    private String areaSaude;
    private Double valor;
    private Integer duracaoEstimada;
    @ManyToOne
    @JsonBackReference
    private Empresa empresa;
//    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<AgendamentoServico> agendamentos;
}
