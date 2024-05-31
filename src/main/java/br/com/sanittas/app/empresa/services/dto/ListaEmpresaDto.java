package br.com.sanittas.app.empresa.services.dto;

import java.util.List;

public record ListaEmpresaDto(
        Integer id,
        String razaoSocial,
        String cnpj,
        Double avaliacao,
        List<ListaEndereco> enderecos
) {
}
