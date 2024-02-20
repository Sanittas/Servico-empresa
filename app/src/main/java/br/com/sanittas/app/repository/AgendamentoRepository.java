package br.com.sanittas.app.repository;

import br.com.sanittas.app.model.AgendamentoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoServico, Integer> {
}
