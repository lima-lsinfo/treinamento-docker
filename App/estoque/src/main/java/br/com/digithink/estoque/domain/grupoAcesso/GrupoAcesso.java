package br.com.digithink.estoque.domain.grupoAcesso;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "grupos_acessos")
@ToString
public class GrupoAcesso implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Getter @Setter
	@NotEmpty(message = "* Informe o nome do Grupo")
	@Column(nullable = false)
	private String grupo;
	
	@Getter	@Setter
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable (name ="permissao_grupo",joinColumns = @JoinColumn (name="id_grupo"), foreignKey = @ForeignKey(name="fk_id_grupo"))
	@Column(name ="permissoes")
	private List<String> permissoes;
	
	@Getter	@Setter
	private String descricao;
	
	@Getter	@Setter
	@NotEmpty(message = "* Selecione uma opção")
	@Column(name="situacao", length=10)
	private String situacao;
	
}