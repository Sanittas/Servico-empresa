package br.com.sanittas.app.funcionario.repository;

import br.com.sanittas.app.empresa.model.Empresa;
import br.com.sanittas.app.funcionario.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    List<Funcionario> findAllByfkEmpresa(Empresa idEmpresa);

    Optional<Funcionario> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    Integer countByfkEmpresa(Empresa empresa);

    boolean existsByFuncional(String funcional);
}