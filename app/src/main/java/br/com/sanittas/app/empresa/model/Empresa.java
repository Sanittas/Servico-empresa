package br.com.sanittas.app.empresa.model;

import br.com.sanittas.app.servicos.model.Servico;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
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
    @Email @Column(unique = true)
    private String email;
    @CNPJ @Column(unique = true)
    private String cnpj;
    private String senha;
    @JsonManagedReference
    @OneToMany(orphanRemoval = true)
    @JsonIgnore
    private List<EnderecoEmpresa> enderecos = new ArrayList<>();
    @OneToMany(orphanRemoval = true)
    private List<Servico> servicos = new ArrayList<>();

    public void addEndereco(EnderecoEmpresa endereco) {
        this.enderecos.add(endereco);
    }

}
