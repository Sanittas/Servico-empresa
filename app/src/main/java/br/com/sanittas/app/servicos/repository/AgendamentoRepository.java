package br.com.sanittas.app.servicos.repository;

import br.com.sanittas.app.servicos.model.AgendamentoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoServico, Integer> {
}
