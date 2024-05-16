package br.com.sanittas.app.empresa.services.dto;

public record LoginDtoResponse(
        Integer id,
        String razaoSocial,
        String token
) {
}
