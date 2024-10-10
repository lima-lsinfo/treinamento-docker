package br.com.digithink.estoque.domain.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<UserDetails> findByLoginIgnoreCase(String login);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailAndIdIsNot(String email, Long id);

}
