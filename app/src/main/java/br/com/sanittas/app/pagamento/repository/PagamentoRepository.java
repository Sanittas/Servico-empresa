package br.com.sanittas.app.pagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.sanittas.app.pagamento.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
