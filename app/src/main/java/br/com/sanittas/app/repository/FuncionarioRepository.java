package br.com.sanittas.app.repository;

import br.com.sanittas.app.model.Empresa;
import br.com.sanittas.app.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    List<Funcionario> findAllByIdEmpresa(Empresa idEmpresa);
}
