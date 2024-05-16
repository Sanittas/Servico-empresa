package br.com.sanittas.app.pagamento.services.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoRequest {
    private String cpf;
    private String nome;
    private String email;
    private Double valor;
    private String nomeProduto;
}
