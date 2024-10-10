package br.com.treinamento.sistemapedidos.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import br.com.treinamento.sistemapedidos.main.SistemaPedidos;
import br.com.treinamento.sistemapedidos.model.Produto;
import br.com.treinamento.sistemapedidos.dao.ProdutoDao;

public class ProdutoService {

	// Monta o menu de produtos
	public static void montarMenuProduto() {
		System.out.println("\n|---------------------------------------|");
		System.out.println("| Menu de Produtos ---------------------|");
		System.out.println("|---------------------------------------|");
		System.out.println("| 1 - Cadastrar Produto                 |");
		System.out.println("| 2 - Listar Produtos                   |");
		System.out.println("| 3 - Buscar Produto                    |");
		System.out.println("| 4 - Atualizar Produto                 |");
		System.out.println("| 5 - Excluir Produto                   |");
		System.out.println("| 6 - Retornar ao menu principal        |");
		System.out.println("|---------------------------------------|\n");
		System.out.print("Informe uma opção:");
	}

	// Cadastra um produto
	public void cadastrarProduto() {
		System.out.println("\n----------------------------------------------------------------------");
		System.out.println("- Cadastro de Produto ------------------------------------------------");
		System.out.println("----------------------------------------------------------------------");

		System.out.print("Informe o nome do Produto: ");
		String nome = SistemaPedidos.scanner.nextLine();

		System.out.print("Informe o preço: ");
		BigDecimal preco = SistemaPedidos.scanner.nextBigDecimal();
		SistemaPedidos.scanner.nextLine();

		Produto produto = new Produto(nome, preco);

		try {
			salvarProduto(produto);
			System.out.println("Produto cadastrado com sucesso! \nPressione enter para continuar...");
			SistemaPedidos.scanner.nextLine();

		} catch (SQLException e) {
			System.out.println("Erro ao salvar o produto: " + e.getMessage());
			System.out.println("Pressione enter para voltar ao menu...");
			SistemaPedidos.scanner.nextLine();
		}
	}

	// Salva Produto na base de dados
	public void salvarProduto(Produto produto) throws SQLException {
		ProdutoDao produtoDao = new ProdutoDao();
		produtoDao.sqlCadastrarProduto(produto);
	}

	// Lista de todos os produtos
	public void listarProdutos() throws SQLException {

		try {

			ProdutoDao produtoDao = new ProdutoDao();
			List<Produto> produtos = produtoDao.sqlListarProdutos();

			System.out.println("\n----------------------------------------------------------------------");
			System.out.println("Lista de Produtos ----------------------------------------------------");
			System.out.println("----------------------------------------------------------------------");
			System.out.printf("%-10s %-30s %-30s", "ID", "Nome", "Preço");
			System.out.println("\n----------------------------------------------------------------------");

			for (Produto produto : produtos) {
				System.out.printf("%-10d %-30s %-30s\n", produto.getId(), produto.getNome(), produto.getPreco());
			}

			System.out.println("\nFim da lista. \nPressione enter para voltar para o menu...");
			SistemaPedidos.scanner.nextLine();

			System.out.println("----------------------------------------------------------------------");

		} catch (SQLException e) {
			System.out.println("Erro ao listar os produtos: " + e.getMessage());
			System.out.println("Pressione enter para voltar ao menu...");
			SistemaPedidos.scanner.nextLine();
		}
	}

	// Busca um produto através de seu ID
	public void buscarProduto() throws SQLException {

		try {

			ProdutoDao produtoDao = new ProdutoDao();

			System.out.print("Digite o código do Produto: ");
			Integer codigoProduto = SistemaPedidos.scanner.nextInt();
			SistemaPedidos.scanner.nextLine();

			Optional<Produto> produtoOptional = produtoDao.sqlBuscarProdutoPorId(codigoProduto);

			if (produtoOptional.isEmpty()) {
				System.out.println("\n----------------------------------------------------------------------");
				System.out.println("Produto Não Encontrado -----------------------------------------------");
				System.out.println("----------------------------------------------------------------------");
			} else {
				Produto produto = produtoOptional.get();
				System.out.println("\n----------------------------------------------------------------------");
				System.out.println("Produto Encontrado ---------------------------------------------------");
				System.out.println("----------------------------------------------------------------------");
				System.out.printf("%-10s %-30s %-30s", "ID", "Nome", "Preço");
				System.out.println("\n----------------------------------------------------------------------");
				System.out.printf("%-10d %-30s %-30s\n", produto.getId(), produto.getNome(), produto.getPreco());
				System.out.println("----------------------------------------------------------------------");
			}
			System.out.println("\nPressione enter para voltar para o menu...");
			SistemaPedidos.scanner.nextLine();

		} catch (SQLException e) {
			System.out.println("Erro ao buscar o produto: " + e.getMessage());
			System.out.println("Pressione enter para voltar ao menu...");
			SistemaPedidos.scanner.nextLine();
		}
	}

	// Atualiza produto na base de dados através de seu ID
	public void atualizarProduto() throws SQLException {

		try {
			ProdutoDao produtoDao = new ProdutoDao();

			System.out.println("Atualizar Produto");

			System.out.print("Informe o código do Produto: ");
			Integer codigo = SistemaPedidos.scanner.nextInt();
			SistemaPedidos.scanner.nextLine();

			Optional<Produto> produtoOptional = produtoDao.sqlBuscarProdutoPorId(codigo);

			if (produtoOptional.isEmpty()) {
				System.out.println("Produto não encontrado");
			} else {
				Produto produto = produtoOptional.get();

				System.out.print("Informe o nome do Produto: ");
				String nome = SistemaPedidos.scanner.nextLine();
				produto.setNome(nome);

				System.out.print("Informe o preço: ");
				BigDecimal preco = SistemaPedidos.scanner.nextBigDecimal();
				SistemaPedidos.scanner.nextLine();
				produto.setPreco(preco);

				produtoDao.sqlAtualizarProduto(produto);
				;
				System.out.println("Produto atualizado com sucesso!");
			}

			System.out.println("\nPressione enter para voltar para o menu...");
			SistemaPedidos.scanner.nextLine();

		} catch (SQLException e) {
			System.out.println("Erro ao atualizar o produto: " + e.getMessage());
			System.out.println("Pressione enter para voltar ao menu...");
			SistemaPedidos.scanner.nextLine();
		}

	}

	// Excluí produto da base de dados através de seu ID
	public void excluirProduto() throws SQLException {

		try {
			ProdutoDao produtoDao = new ProdutoDao();

			System.out.println("Excluir um Produto");

			System.out.print("Informe o código do Produto: ");
			Integer codigo = SistemaPedidos.scanner.nextInt();
			SistemaPedidos.scanner.nextLine();

			Optional<Produto> produtoOptional = produtoDao.sqlBuscarProdutoPorId(codigo);

			if (produtoOptional.isEmpty()) {
				System.out.println("Produto não encontrado");
			} else {
				Produto produto = produtoOptional.get();
				System.out.print("Deseja realmente excluir o produto " + produto.getNome() + "? (S/N): ");
				String confirmacao = SistemaPedidos.scanner.nextLine();

				if (confirmacao.equalsIgnoreCase("S")) {
					produtoDao.sqlExcluirProduto(codigo);
					System.out.println("Produto excluído com sucesso!");
				} else {
					System.out.println("Exclusão cancelada");
				}
			}

			System.out.println("\nPressione enter para voltar para o menu...");
			SistemaPedidos.scanner.nextLine();

		} catch (SQLException e) {
			System.out.println("Erro ao excluir o produto: " + e.getMessage());
			System.out.println("Pressione enter para voltar ao menu...");
			SistemaPedidos.scanner.nextLine();
		}

	}

	// Busca um produto por ID e retorna um Optional de Produto
	public Optional<Produto> buscarProdutoPorId(Integer codigo) {
		ProdutoDao produtoDao = new ProdutoDao();
		
		try {
			return produtoDao.sqlBuscarProdutoPorId(codigo);
		} catch (SQLException e) {
			System.out.println("Erro ao buscar o produto: " + e.getMessage());
			return Optional.empty();
		}
	}
}