package br.com.sanittas.app.service.endereco.dto;

import br.com.sanittas.app.model.EnderecoEmpresa;

public class EnderecoMapper {

    public static EnderecoEmpresa of(EnderecoCriacaoDto enderecoCriacaoDto) {
        EnderecoEmpresa endereco = new EnderecoEmpresa();

        endereco.setLogradouro(enderecoCriacaoDto.getLogradouro());
        endereco.setNumero(enderecoCriacaoDto.getNumero());
        endereco.setComplemento(enderecoCriacaoDto.getComplemento());
        endereco.setCidade(enderecoCriacaoDto.getCidade());
        endereco.setEstado(enderecoCriacaoDto.getEstado());

        return endereco;
    }
}
