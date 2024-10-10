package br.com.treinamento.sistemapedidos.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Pedido {

	public Pedido(Cliente cliente) {
		this.cliente = cliente;
	}

	@Getter @Setter
	private Integer id;

	@Getter @Setter
	private Cliente cliente;
	
	@Getter @Setter
	private BigDecimal totalPedido = BigDecimal.ZERO;
	
	@Getter	@Setter
	private List<ItemPedido> itensPedido = new ArrayList<ItemPedido>();


	// Método para adicionar itens ao pedido
	public void adicionarItem(ItemPedido itemPedido) {
		this.itensPedido.add(itemPedido);
		calcularTotal();
	}

	// Método para calcular o valor total do pedido
	private void calcularTotal() {
		this.totalPedido = BigDecimal.ZERO;

		for (ItemPedido item : itensPedido) {
			this.totalPedido = this.totalPedido.add(item.getValorTotal());
		}
	}
}
