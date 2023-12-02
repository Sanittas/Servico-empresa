package br.com.sanittas.app.repository;

import br.com.sanittas.app.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {

}
