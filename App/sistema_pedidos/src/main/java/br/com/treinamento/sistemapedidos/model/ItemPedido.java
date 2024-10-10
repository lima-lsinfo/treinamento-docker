package br.com.treinamento.sistemapedidos.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

public class ItemPedido {

	public ItemPedido(Pedido pedido, Produto produto, Integer quantidade) {
		this.pedido = pedido;
		this.produto = produto;
		this.quantidade = quantidade;
		this.valorUnitario = produto.getPreco();
		this.valorTotal = produto.getPreco().multiply(new BigDecimal(quantidade));
	}

	@Getter @Setter
	private Long id;

	@Getter @Setter
	private Pedido pedido;

	@Getter  @Setter
	private Produto produto;
	
	@Getter @Setter
	private Integer quantidade;

	@Getter @Setter
	private BigDecimal valorUnitario;

	@Getter @Setter
	private BigDecimal valorTotal;


}
