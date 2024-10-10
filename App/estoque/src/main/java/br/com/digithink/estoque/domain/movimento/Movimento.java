package br.com.digithink.estoque.domain.movimento;

import java.time.LocalDate;

import br.com.digithink.estoque.domain.fornecedor.Fornecedor;
import br.com.digithink.estoque.domain.material.Material;
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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movimentos")
public class Movimento {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @NotNull(message = "* Data é obrigatória")
    @Column(nullable = false)
    private LocalDate data = LocalDate.now();

    @Getter @Setter
    @NotNull(message = "* Material é obrigatório")
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="material_id", foreignKey = @ForeignKey(name="fk_material"))
    private Material material;

    @Getter @Setter
    @NotNull(message = "* Quantidade é obrigatória")
    @Column(nullable = false)
    private Integer quantidade;

    @Getter @Setter
    @NotBlank(message = "* Tipo é obrigatório")
    @Column(length = 20)
    private String tipo; // vamos infomar  se é entrada ou saída

    @Getter @Setter
    @Column(length = 50)
    private String notaFiscal;

    @Getter @Setter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fornecedor_id", foreignKey = @ForeignKey(name="fk_fornecedor"))
    private Fornecedor fornecedor;

    @Getter @Setter
    @NotBlank(message = "* Responsável é obrigatório")
    @Column(length = 50)
    private String responsavel;
}