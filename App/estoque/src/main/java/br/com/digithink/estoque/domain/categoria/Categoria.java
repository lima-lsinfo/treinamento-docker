package br.com.digithink.estoque.domain.categoria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column(length = 20)
    @NotBlank(message = "* O nome é obrigatório")
    @Size(max = 20, message = "Máximo de 20 caracteres")
    private String nome;

    @Getter @Setter
    @Column(length = 10)
    @NotBlank(message = "* Obrigatório")
    private String situacao;
    
}
