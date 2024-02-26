package br.com.sanittas.app.usuario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "endereco_usuario")
public class EnderecoUsuario {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_end_usuario")
    private Integer id;
    @NotBlank
    private String cep;
    @NotBlank
    private String logradouro;
    @NotBlank
    private String numero;
    private String complemento;
    @NotBlank
    private String bairro;
    @NotBlank
    private String cidade;
    @NotBlank
    private String estado;
}
