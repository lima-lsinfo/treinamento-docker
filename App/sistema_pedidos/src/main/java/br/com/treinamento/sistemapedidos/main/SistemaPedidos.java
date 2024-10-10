package br.com.treinamento.sistemapedidos.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.treinamento.sistemapedidos.model.Pedido;
import br.com.treinamento.sistemapedidos.service.ClienteService;
import br.com.treinamento.sistemapedidos.service.MercadoService;
import br.com.treinamento.sistemapedidos.service.PedidoService;
import br.com.treinamento.sistemapedidos.service.ProdutoService;

public class SistemaPedidos {

	public static Scanner scanner = new Scanner(System.in);
	public static List<Pedido> pedidoList = new ArrayList<Pedido>();

	public static void main(String[] args) throws SQLException{

		ProdutoService produtoService = new ProdutoService();
		ClienteService clienteService = new ClienteService();


		while (true) {
			MercadoService.montarMenu();
			Integer opcao = scanner.nextInt();
			scanner.nextLine();

			switch (opcao) {
			case 1: {
				Integer opcaoCliente;
				do {
					ClienteService.montarMenuCliente();
					opcaoCliente = scanner.nextInt();
					scanner.nextLine();

					switch (opcaoCliente) {
					case 1: {
						clienteService.cadastrarCliente();
						break;
					}
					case 2: {
						clienteService.listarClientes();
						break;
					}
					case 3: {
						clienteService.buscarCliente();
						break;
					}
					case 4: {
						clienteService.atualizarCliente();
						break;
					}
					case 5: {
						clienteService.excluirCliente();
						break;
					}
					case 6: {
						System.out.println("Saindo do Menu Cliente...");
						break;
					}
					default:
						System.out.println("Opção inválida, tente novamente");
					}
				} while (opcaoCliente != 6);
				break;
			}
			case 2: {
				Integer opcaoProduto;
				do {
					ProdutoService.montarMenuProduto();
					opcaoProduto = scanner.nextInt();
					scanner.nextLine();

					switch (opcaoProduto) {
					case 1: {
						produtoService.cadastrarProduto();
						break;
					}
					case 2: {
						produtoService.listarProdutos();
						break;
					}
					case 3: {
						produtoService.buscarProduto();
						break;
					}
					case 4: {
						produtoService.atualizarProduto();
						break;
					}
					case 5: {
						produtoService.excluirProduto();
						break;
					}
					case 6: {
						System.out.println("Saindo do Menu Produto...");
						break;
					}
					default:
						System.out.println("Opção inválida, tente novamente");
					}
				} while (opcaoProduto != 6);
				break;
			}
			case 3: {
				Integer opcaoPedido;
				do {
					PedidoService.montarMenuPedidos();
					opcaoPedido = scanner.nextInt();
					scanner.nextLine();

					switch (opcaoPedido) {
					case 1: {
						// PedidoService.criarPedido();
						break;
					}
					case 2: {
						// PedidoService.listarPedidos();
						break;
					}
					case 3: {
						// PedidoService.detalharPedido();
						break;
					}
					case 4: {
						System.out.println("Saindo do Menu Pedidos...");
						break;
					}
					default:
						System.out.println("Opção inválida, tente novamente");
					}
				} while (opcaoPedido != 3);
				break;
			}
			case 4: {
				scanner.close();
				System.out.println("Saindo...");
				System.exit(0);
			}
			default:
				System.out.println("Opção inválida, tente novamente");
			}

		}
	}
}
