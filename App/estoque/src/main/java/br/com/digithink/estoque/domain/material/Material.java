package br.com.digithink.estoque.domain.material;

import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;

import br.com.digithink.estoque.domain.categoria.Categoria;
import br.com.digithink.estoque.domain.fornecedor.Fornecedor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "materiais")
public class Material {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter	@Setter
	@NotNull(message = "* Categoria obrigatória")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="categoria_id", foreignKey = @ForeignKey(name="fk_categoria"))
	private Categoria categoria;

    @Getter @Setter
    @Column(length = 100)
    @NotBlank(message = "* Nome é obrigatório")
    @Size(max = 100, message = "Máximo de 100 caracteres")
    private String nome;

    @Getter @Setter
    @Column(length = 13)
    private String codigoBarras;

    @Getter @Setter
    @Column(length = 30)
    private String fabricante;

    @Getter @Setter
    @Column(length = 20)
    @NotBlank(message = "* UM obrigatória")
    private String unidadeMedida;

    @Getter	@Setter	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fornecedor_id", foreignKey = @ForeignKey(name="fk_fornecedor"))
	private Fornecedor fornecedor;

    @Getter @Setter
	@NotNull(message="* Valor obrigatório")
	@NumberFormat(pattern = "#,###,###,###.##")
	@Column(precision = 11, scale = 2, columnDefinition = "Decimal(11,2)")
	private BigDecimal valor;

    @Getter @Setter
    @Column(length = 30)
    @NotBlank(message = "* Local obrigatório")
    private String localArmazenagem;
    
    @Getter @Setter
    @NotNull(message = "* Qtd obrigatória")
    private Integer estoqueMaximo;

    @Getter @Setter
    @NotNull(message = "* Qtd obrigatória")
    private Integer estoqueMinimo;

    @Getter @Setter
    @NotNull(message = "* Saldo obrigatório")
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer saldo = 0;

    @Getter @Setter
    @Column(length = 10)
    @NotBlank(message = "* Situação obrigatória")
    private String situacao;

}
