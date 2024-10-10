package br.com.digithink.estoque.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.digithink.estoque.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class AutenticacaoService implements UserDetailsService{

	@Autowired
	private UsuarioRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		
		Optional<UserDetails> user = userRepository.findByLoginIgnoreCase(login);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return user.get();

		
		/*
		Optional<Usuario> usuario = userRepository.findByLoginIgnoreCase(login);
		return usuario.map(UserDetailsImpl::new).orElse(null);
		*/
	    
	}

}
