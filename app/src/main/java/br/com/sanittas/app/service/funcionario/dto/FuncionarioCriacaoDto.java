package br.com.sanittas.app.service.funcionario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class FuncionarioCriacaoDto {
    @NotBlank
    private String funcional;
    @NotBlank
    private String nome;
    @CPF
    private String cpf;
    @Email
    private String email;
    @NotBlank
    private String numeroRegistroAtuacao;
}
