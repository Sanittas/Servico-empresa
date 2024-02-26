package br.com.sanittas.app.funcionario.repository;

import br.com.sanittas.app.funcionario.model.ContatoFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatoFuncionarioRepository extends JpaRepository<ContatoFuncionario, Integer> {
    boolean existsByEmail(String email);
}
