package br.com.sanittas.app.funcionario.repository;

import br.com.sanittas.app.funcionario.model.ContatoFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatoFuncionarioRepository extends JpaRepository<ContatoFuncionario, Integer> {
    boolean existsByEmail(String email);

    List<ContatoFuncionario> findAllByFkFuncionario_Id(Integer id);
}
