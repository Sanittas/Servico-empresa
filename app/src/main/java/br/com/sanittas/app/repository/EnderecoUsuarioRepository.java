package br.com.sanittas.app.repository;

import br.com.sanittas.app.model.EnderecoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoUsuarioRepository extends JpaRepository<EnderecoUsuario, Integer> {
}
