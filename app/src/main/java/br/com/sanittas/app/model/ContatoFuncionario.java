package br.com.sanittas.app.model;

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
    @Column(name = "num_cel")
    private String tel;
    @Email
    private String email;
    @ManyToOne
    private Funcionario fkFuncionario;
}
