package br.com.sanittas.app.usuario.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Usuario")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String nome;
    @Pattern(regexp = "^\\(\\d{2}\\)9\\d{4}-\\d{4}$", message = "O número de telefone deve estar no formato 11999999999")
    private String telefone;
    @Email
    private String email;
    @CPF
    private String cpf;
    @NotBlank
    @JsonIgnore
    private String senha;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<EnderecoUsuario> enderecos = new ArrayList<>(); // Lista de endereços associados ao usuário
}

