package br.com.sanittas.app.repository;

import br.com.sanittas.app.model.Empresa;
import br.com.sanittas.app.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    List<Funcionario> findAllByIdEmpresa(Empresa idEmpresa);

    Optional<Funcionario> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
