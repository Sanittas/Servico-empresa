package br.com.sanittas.app.funcionario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Competencia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    @JsonBackReference
    private Funcionario funcionario;
    @NotBlank
    private String especializacao;
    @NotBlank
    private String registroAtuacao;
}
