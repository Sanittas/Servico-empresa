package br.com.sanittas.app.repository;

import br.com.sanittas.app.model.ServicoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoEmpresaRepository extends JpaRepository<ServicoEmpresa, Integer> {

}
