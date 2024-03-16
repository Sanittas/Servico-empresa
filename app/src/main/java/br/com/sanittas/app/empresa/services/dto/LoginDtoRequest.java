package br.com.sanittas.app.empresa.services.dto;

public record LoginDtoRequest(
        String cnpj,
        String senha
) {
}
