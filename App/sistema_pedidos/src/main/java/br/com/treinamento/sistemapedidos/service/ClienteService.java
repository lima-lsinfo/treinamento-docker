package br.com.treinamento.sistemapedidos.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import br.com.treinamento.sistemapedidos.dao.ClienteDao;
import br.com.treinamento.sistemapedidos.main.SistemaPedidos;
import br.com.treinamento.sistemapedidos.model.Cliente;

public class ClienteService {

	// Monta o menu de cliente
	public static void montarMenuCliente() {
		System.out.println("\n|---------------------------------------|");
		System.out.println("| Menu de Clientes ---------------------|");
		System.out.println("|---------------------------------------|");
		System.out.println("| 1 - Cadastrar Cliente                 |");
		System.out.println("| 2 - Listar Clientes                   |");
		System.out.println("| 3 - Buscar Cliente                    |");
		System.out.println("| 4 - Atualizar Cliente                 |");
		System.out.println("| 5 - Excluir Cliente                   |");
		System.out.println("| 6 - Retornar ao menu principal        |");
		System.out.println("|---------------------------------------|\n");
		System.out.print("Informe uma opção:");
	}

	// Cadastra um cliente
	public void cadastrarCliente() {
		System.out.println("\n----------------------------------------------------------------------");
		System.out.println("Cadastro de Cliente --------------------------------------------------");
		System.out.println("----------------------------------------------------------------------");

		System.out.print("Nome: ");
		String nome = SistemaPedidos.scanner.nextLine();

		System.out.print("E-mail: ");
		String email = SistemaPedidos.scanner.nextLine();

		Cliente cliente = new Cliente(nome, email);

		try {
			salvarCliente(cliente);
			System.out.println("Cliente cadastrado com sucesso! \nPressione enter para continuar...");
			SistemaPedidos.scanner.nextLine();

		} catch (SQLException e) {
			System.out.println("Erro ao salvar o cliente: " + e.getMessage());
			System.out.println("Pressione enter para voltar ao menu...");
			SistemaPedidos.scanner.nextLine();
		}
	}

	// Salva Cliente na base de dados
	public void salvarCliente(Cliente cliente) throws SQLException {
		ClienteDao clienteDao = new ClienteDao();
		clienteDao.sqlCadastrarCLiente(cliente);
	}

	// Lista de todos os clientes
	public void listarClientes() throws SQLException {

		try {
			ClienteDao clienteDao = new ClienteDao();
			List<Cliente> clientes = clienteDao.sqlListarClientes();

			System.out.println("\n----------------------------------------------------------------------");
			System.out.println("Lista de Clientes ----------------------------------------------------");
			System.out.println("----------------------------------------------------------------------");
			System.out.printf("%-10s %-30s %-30s", "ID", "Nome", "E-mail");
			System.out.println("\n----------------------------------------------------------------------");

			for (Cliente cliente : clientes) {
				System.out.printf("%-10d %-30s %-30s\n", cliente.getId(), cliente.getNome(), cliente.getEmail());
			}

			System.out.println("\nFim da lista. \nPressione enter para voltar para o menu...");
			SistemaPedidos.scanner.nextLine();

			System.out.println("----------------------------------------------------------------------");
		} catch (SQLException e) {
			System.out.println("Erro ao listar os clientes: " + e.getMessage());
			System.out.println("Pressione enter para voltar ao menu...");
			SistemaPedidos.scanner.nextLine();
		}
	}

	// Busca um cliente através de seu ID
	public void buscarCliente() throws SQLException {

		try {
			ClienteDao clienteDao = new ClienteDao();

			System.out.print("Digite o código do cliente: ");
			Integer codigoCliente = SistemaPedidos.scanner.nextInt();
			SistemaPedidos.scanner.nextLine();

			Optional<Cliente> clienteOptional = clienteDao.sqlBuscarClientePorId(codigoCliente);

			if (clienteOptional.isEmpty()) {
				System.out.println("\n----------------------------------------------------------------------");
				System.out.println("Cliente Não Encontrado -----------------------------------------------");
				System.out.println("----------------------------------------------------------------------");
			} else {
				Cliente cliente = clienteOptional.get();
				System.out.println("\n----------------------------------------------------------------------");
				System.out.println("Cliente Encontrado ---------------------------------------------------");
				System.out.println("----------------------------------------------------------------------");
				System.out.printf("%-10s %-30s %-30s", "ID", "Nome", "E-mail");
				System.out.println("\n----------------------------------------------------------------------");
				System.out.printf("%-10d %-30s %-30s\n", cliente.getId(), cliente.getNome(), cliente.getEmail());
				System.out.println("----------------------------------------------------------------------");
			}

			System.out.println("\nPressione enter para voltar para o menu...");
			SistemaPedidos.scanner.nextLine();

		} catch (SQLException e) {
			System.out.println("Erro ao buscar o cliente: " + e.getMessage());
			System.out.println("Pressione enter para voltar ao menu...");
			SistemaPedidos.scanner.nextLine();
		}
	}

	// Atualiza um cliente
	public void atualizarCliente() throws SQLException {

		try {
			ClienteDao clienteDao = new ClienteDao();

			System.out.println("Atualizar Cliente");

			System.out.print("Informe o código do Cliente: ");
			Integer codigo = SistemaPedidos.scanner.nextInt();
			SistemaPedidos.scanner.nextLine();

			Optional<Cliente> clienteOptional = clienteDao.sqlBuscarClientePorId(codigo);

			if (clienteOptional.isEmpty()) {
				System.out.println("Cliente não encontrado");
			} else {
				Cliente cliente = clienteOptional.get();

				System.out.print("Informe o nome do Cliente: ");
				String nome = SistemaPedidos.scanner.nextLine();
				cliente.setNome(nome);

				System.out.print("Informe o email: ");
				String email = SistemaPedidos.scanner.nextLine();
				SistemaPedidos.scanner.nextLine();
				cliente.setEmail(email);

				clienteDao.sqlAtualizarCliente(cliente);
				;
				System.out.println("Cliente atualizado com sucesso!");
			}

			System.out.println("\nPressione enter para voltar para o menu...");
			SistemaPedidos.scanner.nextLine();

		} catch (SQLException e) {
			System.out.println("Erro ao atualizar o cliente: " + e.getMessage());
			System.out.println("Pressione enter para voltar ao menu...");
			SistemaPedidos.scanner.nextLine();
		}

	}

	// Excluir cliente
	public void excluirCliente() throws SQLException {

		try {
			ClienteDao clienteDao = new ClienteDao();

			System.out.println("Excluir um Cliente");

			System.out.print("Informe o código do Cliente: ");
			Integer codigo = SistemaPedidos.scanner.nextInt();
			SistemaPedidos.scanner.nextLine();

			Optional<Cliente> clienteOptional = clienteDao.sqlBuscarClientePorId(codigo);

			if (clienteOptional.isEmpty()) {
				System.out.println("Cliente não encontrado");
			} else {
				Cliente cliente = clienteOptional.get();
				System.out.print("Deseja realmente excluir o cliente " + cliente.getNome() + "? (S/N): ");
				String confirmacao = SistemaPedidos.scanner.nextLine();

				if (confirmacao.equalsIgnoreCase("S")) {
					clienteDao.sqlExcluirCliente(codigo);
					System.out.println("Cliente excluído com sucesso!");
				} else {
					System.out.println("Exclusão cancelada");
				}
			}

			System.out.println("\nPressione enter para voltar para o menu...");
			SistemaPedidos.scanner.nextLine();

		} catch (SQLException e) {
			System.out.println("Erro ao excluir o cliente: " + e.getMessage());
			System.out.println("Pressione enter para voltar ao menu...");
			SistemaPedidos.scanner.nextLine();
		}

	}

	// Busca um cliente por ID e retorna um Optional de Cliente
	public Optional<Cliente> buscarClientePorId(Integer codigo) throws SQLException {
		ClienteDao clienteDao = new ClienteDao();
		return clienteDao.sqlBuscarClientePorId(codigo);
	}

}
