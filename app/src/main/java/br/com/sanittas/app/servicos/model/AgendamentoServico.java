package br.com.sanittas.app.servicos.model;


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
    @JsonBackReference
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;
    private Boolean status;
    @ManyToOne
    @JsonBackReference
    private Servico servico;
}
