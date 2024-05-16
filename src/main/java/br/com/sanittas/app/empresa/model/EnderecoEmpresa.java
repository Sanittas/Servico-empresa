package br.com.sanittas.app.empresa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "endereco_empresa")
public class EnderecoEmpresa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_end_empresa")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_empresa")
    @JsonBackReference
    private Empresa empresa;
    private String cep;
    @NotBlank
    private String logradouro;
    @NotBlank
    private String numero;
    private String bairro;
    private String complemento;
    @NotBlank
    private String estado;
    @NotBlank
    private String cidade;

}
