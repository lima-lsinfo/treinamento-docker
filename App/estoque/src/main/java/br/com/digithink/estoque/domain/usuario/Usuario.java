package br.com.digithink.estoque.domain.usuario;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.digithink.estoque.domain.grupoAcesso.GrupoAcesso;
import br.com.digithink.estoque.enuns.SituacaoUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Table(
		name = "usuarios",
		indexes = {
				@Index(name = "id_usuario_index", columnList="id_usuario"),
				@Index(name = "login_usuario_index", columnList="login")
				},
		uniqueConstraints={
				@UniqueConstraint(name="login_unique", columnNames={"login"}),
				@UniqueConstraint(name="email_unique", columnNames={"email"})}
		)

public class Usuario implements UserDetails{

	private static final long serialVersionUID = 1L;

	public Usuario() {
	}

	public Usuario(String login) {
		this.login = login;
	}

	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;

	@Getter @Setter
	@Size(min = 3, max = 20)
	@Column(length=20)
	private String login;
	
	@Getter @Setter
	@Size(min = 3, max = 30)
	@Column(length=30)
	private String nome;
	
	@Getter	@Setter
	@NotEmpty(message = "* Informe o e-mail do usuário")
	@Size(max=700, message = "* Limite de 100 caracteres")
	@Column(length=70)
	@Email(message="* E-mail inválido")
	private String email;
	
	@Getter @Setter
	private String senha;

	public boolean isContaResetada() {
        return this.getSituacaoUsuario() == SituacaoUsuario.Resetado;
    }
	
	public boolean isNovaConta() {
        return this.getSituacaoUsuario() == SituacaoUsuario.Novo;
    }	
	
	@Getter @Setter
	@Transient
    private String image64;
	
	@Getter	@Setter
	@NotNull(message = "Selecione uma opção")
	@Column(length=10)
	@Enumerated(EnumType.STRING)
	private SituacaoUsuario situacaoUsuario;

	@Getter @Setter
	private LocalDateTime dataAtualizacao;

		
	@Getter @Setter
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "rel_usuarios_grupos_acessos",
			joinColumns = @JoinColumn(name="usuario_id"),
			inverseJoinColumns = @JoinColumn(name="grupo_acesso_id")
			)
	private List<GrupoAcesso> gruposAcessos;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<GrantedAuthority> authorities = new ArrayList<>();

		this.gruposAcessos.forEach( grupo -> {
			grupo.getPermissoes().forEach( p ->{
				 var role = new SimpleGrantedAuthority(p);
				 if(!authorities.contains(role)) authorities.add(role);
			});
		});
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.getSenha();
	}

	@Override
	public String getUsername() {
		return this.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}