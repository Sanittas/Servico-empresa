package br.com.sanittas.app.empresa.model;

import br.com.sanittas.app.servicos.model.Servico;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "empresa")
public class Empresa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    private Integer id;
    @Column(name = "razao_social")
    private String razaoSocial;
    @CNPJ
    private String cnpj;
    @NotBlank @Size(min = 8) @JsonIgnore
    private String senha;
    @Email @Column(unique = true)
    private String email;
    @JsonManagedReference
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<EnderecoEmpresa> enderecos = new ArrayList<>();
    @JsonManagedReference
    @JoinColumn(name = "id_servico")
    @OneToMany(orphanRemoval = true)
    private List<Servico> servicos = new ArrayList<>();

    public void addEndereco(EnderecoEmpresa endereco) {
        this.enderecos.add(endereco);
    }

}
