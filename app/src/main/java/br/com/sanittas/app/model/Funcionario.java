package br.com.sanittas.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "Funcionario")
@Table(name = "funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank @Column(unique = true)
    private String funcional;
    @NotBlank
    private String nome;
    @CPF @Column(unique = true)
    private String cpf;
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}-[\\d\\w]",
            message = "O RG deve estar no formato XX.XXX.XXX-X ou XX.XXX.XXX-x, onde X é um dígito ou letra.")
    @Column(unique = true)
    private String rg;
    @Email @Column(unique = true)
    private String email;
    @NotBlank
    private String numeroRegistroAtuacao;
    @ManyToOne
    @JoinColumn(name = "fk_empresa")
    private Empresa idEmpresa;

}
