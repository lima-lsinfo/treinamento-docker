package br.com.digithink.estoque.domain.permissao;

import org.springframework.security.core.GrantedAuthority;

import br.com.digithink.estoque.enuns.Permissao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(
		name = "TB_PERMISSAO",
		uniqueConstraints={
				@UniqueConstraint(name="authority_unique", columnNames={"authority"})
				}
		)
public class PermissaoModel implements GrantedAuthority{

	private static final long serialVersionUID = 1L;

	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long permissaoId;
	
	@Setter
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Permissao authority;
	
	@Override
	public String getAuthority() {
		return authority.toString();
	}

}
