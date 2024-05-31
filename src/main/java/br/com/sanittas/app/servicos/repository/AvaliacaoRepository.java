package br.com.sanittas.app.servicos.repository;

import br.com.sanittas.app.servicos.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    @Query("""
    SELECT AVG(a.avaliacao)
    FROM Avaliacao a
    JOIN a.agendamentoServico ag
    JOIN ag.servico s
    JOIN s.empresa e
    WHERE e.id = :id
    """)
    Double calcularAvaliacao(Integer id);
}
