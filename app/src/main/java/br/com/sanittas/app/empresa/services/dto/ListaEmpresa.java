package br.com.sanittas.app.empresa.services.dto;

import java.util.List;

public record ListaEmpresa(
        Integer id,
        String razaoSocial,
        String cnpj,
        List<ListaEndereco> enderecos
) {
}
