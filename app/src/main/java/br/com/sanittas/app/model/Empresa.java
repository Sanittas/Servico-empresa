package br.com.sanittas.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name="Empresa")
@Table(name = "empresa")
public class Empresa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "razao_social")
    private String razaoSocial;
    @CNPJ @Column(unique = true)
    private String cnpj;
    private String senha;
    @OneToMany(mappedBy = "empresa", orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();

}
