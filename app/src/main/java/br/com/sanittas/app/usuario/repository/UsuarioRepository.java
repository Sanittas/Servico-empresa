package br.com.sanittas.app.usuario.repository;

import br.com.sanittas.app.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
}
