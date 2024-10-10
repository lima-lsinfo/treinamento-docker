package br.com.treinamento.sistemapedidos.model;

import lombok.Getter;
import lombok.Setter;

public class Cliente {

	public Cliente() {
	}

	public Cliente(String nome, String email) {
		this.nome = nome;
		this.email = email;
	}

	@Getter
	@Setter
	private Integer id;

	@Getter
	@Setter
	private String nome;

	@Getter
	@Setter
	private String email;

}