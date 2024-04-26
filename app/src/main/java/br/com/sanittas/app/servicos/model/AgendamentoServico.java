package br.com.sanittas.app.servicos.model;


import br.com.sanittas.app.funcionario.model.Funcionario;
import br.com.sanittas.app.usuario.model.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendamentoServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agendamento")
    private Integer id;
    private LocalDateTime dataHoraAgendamento;
    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;
    @ManyToOne
    private Funcionario funcionario;
    private Boolean status;
    @ManyToOne
    private Servico servico;
}
