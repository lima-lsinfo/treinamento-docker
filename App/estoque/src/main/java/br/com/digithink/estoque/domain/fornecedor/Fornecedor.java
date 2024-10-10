package br.com.digithink.estoque.domain.fornecedor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fornecedores", uniqueConstraints = {@UniqueConstraint(columnNames="cnpj")})
public class Fornecedor {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter @Setter
    @Column(length = 14)
    @NotBlank(message = "* O CNPJ é obrigatório")
    private String cnpj;

    @Getter @Setter
    @Column(length = 50)
    @NotBlank(message = "* O nome é obrigatório")
    @Size(max = 50, message = "Máximo de 50 caracteres")
    private String nome;

    @Getter @Setter
    @Column(length = 14)
    private String telefone;

    @Getter @Setter
    @Column(length = 50)
    @NotBlank(message = "* O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @Getter @Setter
    @Column(length = 30)
    @NotBlank(message = "* O contato é obrigatório")
    private String contato;

    @Getter @Setter
    @Column(length = 10)
    @NotBlank(message = "* A situação é obrigatória")
    private String situacao;
    
}
