package br.com.treinamento.sistemapedidos.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class Produto {

	public Produto() {
	}

	public Produto(String nome, BigDecimal preco) {
		this.nome = nome;
		this.preco = preco;
	}

	@Getter
	@Setter
	private Integer id;

	@Getter
	@Setter
	private String nome;

	@Getter
	@Setter
	private BigDecimal preco;

}
