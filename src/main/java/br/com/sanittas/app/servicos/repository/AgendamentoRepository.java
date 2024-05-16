package br.com.sanittas.app.servicos.repository;

import br.com.sanittas.app.funcionario.model.Funcionario;
import br.com.sanittas.app.servicos.model.AgendamentoServico;
import br.com.sanittas.app.servicos.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoServico, Integer> {
    List<AgendamentoServico> findAllByUsuario_Id(Integer id);

    @Query(value = "SELECT a FROM AgendamentoServico a WHERE a.dataHoraAgendamento >= " +
            ":dataInicio " +
            "AND a.dataHoraAgendamento < :dataFim AND a.servico = :servico AND a.funcionario = :funcionario")
    List<AgendamentoServico> findAllByDay(@Param("dataInicio") LocalDateTime dataInicio,
                                          @Param("dataFim") LocalDateTime dataFim,
                                          @Param("servico") Servico servico,
                                          @Param("funcionario") Funcionario funcionario);
}
