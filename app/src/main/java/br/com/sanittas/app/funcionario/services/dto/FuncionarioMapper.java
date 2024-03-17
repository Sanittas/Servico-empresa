package br.com.sanittas.app.funcionario.services.dto;

import br.com.sanittas.app.funcionario.model.Funcionario;

public class FuncionarioMapper {

    public static Funcionario of(FuncionarioCriacaoDto funcionarioCriacaoDto) {
        Funcionario funcionario = new Funcionario();

        funcionario.setFuncional(funcionarioCriacaoDto.getFuncional());
        funcionario.setNome(funcionarioCriacaoDto.getNome());
        funcionario.setCpf(funcionarioCriacaoDto.getCpf());


        return funcionario;
    }
}

