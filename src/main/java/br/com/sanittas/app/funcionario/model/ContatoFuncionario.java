package br.com.sanittas.app.funcionario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Table(name = "contato_funcionario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContatoFuncionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contato_funcionario")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    @JsonBackReference
    private Funcionario funcionario;
    @Column(name = "num_cel")
    private String tel;
    @Email
    private String email;
}
