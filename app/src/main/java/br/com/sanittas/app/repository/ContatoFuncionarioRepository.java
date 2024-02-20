package br.com.sanittas.app.repository;

import br.com.sanittas.app.model.ContatoFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatoFuncionarioRepository extends JpaRepository<ContatoFuncionario, Integer> {
}
