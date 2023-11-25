package br.com.sanittas.app.service.funcionario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ListaFuncionarioAtualizacao {
    private int id;
    private String funcional;
    private String nome;
    private String cpf;
    private String rg;
    private String email;
    private String numeroRegistroAtuacao;

}



