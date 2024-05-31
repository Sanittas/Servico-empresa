package br.com.sanittas.app.servicos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "avaliacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Avaliacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao")
    private Integer idAvaliacao;
    @OneToOne
    @JoinColumn(name = "id")
    private AgendamentoServico agendamentoServico;
    private Integer avaliacao;
    private String comentario;
}
