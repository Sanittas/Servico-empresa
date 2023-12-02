package br.com.sanittas.app.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AgendamentoServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dataHoraAgendamento;
    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;
    @JsonBackReference
    @ManyToOne
    private ServicoEmpresa servicoEmpresa;
}
