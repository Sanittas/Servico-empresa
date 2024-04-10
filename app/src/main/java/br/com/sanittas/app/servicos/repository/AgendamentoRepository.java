package br.com.sanittas.app.servicos.repository;

import br.com.sanittas.app.servicos.model.AgendamentoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoServico, Integer> {
    List<AgendamentoServico> findAllByUsuario_Id(Integer id);
}
