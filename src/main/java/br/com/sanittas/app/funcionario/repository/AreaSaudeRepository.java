package br.com.sanittas.app.funcionario.repository;

import br.com.sanittas.app.funcionario.model.AreaSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaSaudeRepository extends JpaRepository<AreaSaude, Integer> {
}
