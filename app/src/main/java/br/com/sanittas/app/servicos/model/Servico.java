package br.com.sanittas.app.servicos.model;

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
    @OneToMany
    private List<AgendamentoServico> agendamentos;
}
