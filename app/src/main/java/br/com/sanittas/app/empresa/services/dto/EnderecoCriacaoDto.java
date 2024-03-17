package br.com.sanittas.app.empresa.services.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoCriacaoDto {
    private String cep;
    @NotBlank
    private String logradouro;
    private String numero;
    private String complemento;
    @NotBlank
    private String cidade;
    private String bairro;
    @NotBlank
    private String estado;
}
