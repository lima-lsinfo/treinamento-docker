package br.com.treinamento.sistemapedidos.service;

import java.util.Optional;

import br.com.treinamento.sistemapedidos.dao.PedidoDao;
import br.com.treinamento.sistemapedidos.dto.ResumoPedidoDTO;
import br.com.treinamento.sistemapedidos.main.SistemaPedidos;
import br.com.treinamento.sistemapedidos.model.Cliente;
import br.com.treinamento.sistemapedidos.model.ItemPedido;
import br.com.treinamento.sistemapedidos.model.Pedido;
import br.com.treinamento.sistemapedidos.model.Produto;

public class PedidoService {

    // Menu de pedidos
    public static void montarMenuPedidos() {
        System.out.println("\n|---------------------------------------|");
        System.out.println("| Menu de Pedidos ----------------------|");
        System.out.println("|---------------------------------------|");
        System.out.println("| 1 - Criar Pedido                      |");
        System.out.println("| 2 - Listar Pedidos                    |");
        System.out.println("| 3 - Buscar um Pedido                  |");
        System.out.println("| 4 - Atualizar um Pedido               |");
        System.out.println("| 5 - Excluir um Pedido                 |");
        System.out.println("| 6 - Retornar ao menu principal        |");
        System.out.println("|---------------------------------------|\n");
        System.out.print("Informe uma opção:");
    }

    // Cria um novo pedido
    public static void criarPedido() {

        ClienteService clienteService = new ClienteService();
        ProdutoService produtoService = new ProdutoService();
        ItemPedidoService itemPedidoService = new ItemPedidoService();

        try {

            System.out.println("--------------------------------------------------------------------");
            System.out.println("Criar Pedido -------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------");

            System.out.print("Digite o código do cliente: ");
            Integer codigoCliente = SistemaPedidos.scanner.nextInt();
            SistemaPedidos.scanner.nextLine();

            Optional<Cliente> clienteOptional = clienteService.buscarClientePorId(codigoCliente);

            if (clienteOptional.isEmpty()) {
                System.out.println("Cliente não encontrado");
            } else {
                Cliente cliente = clienteOptional.get();
                Pedido pedido = new Pedido(cliente);

                Integer pedidoGerado = cadastrarPedido(pedido);

                if (pedidoGerado != null) {
                    pedido.setId(pedidoGerado);
                    System.out.println("Pedido gerado com sucesso para o cliente: " + pedido.getCliente().getNome());
                    System.out.println("Número do pedido: " + pedido.getId());

                    while (true) {

                        System.out.print("Deseja dicionar um produto ao pedido? (S/N)");
                        String opcao = SistemaPedidos.scanner.nextLine();

                        if (opcao.equalsIgnoreCase("S")) {

                            System.out.print("Informe o código do produto: ");
                            Integer codigoProduto = SistemaPedidos.scanner.nextInt();
                            SistemaPedidos.scanner.nextLine();

                            Optional<Produto> produtoOptional = produtoService.buscarProdutoPorId(codigoProduto);

                            if (produtoOptional.isEmpty()) {
                                System.out.println("Produto não encontrado!");
                            }

                            Produto produto = produtoOptional.get();

                            System.out.print("Informe a quantidade: ");
                            Integer quantidade = SistemaPedidos.scanner.nextInt();
                            SistemaPedidos.scanner.nextLine();

                            ItemPedido itemPedido = new ItemPedido(pedido, produto, quantidade);

                            itemPedidoService.adicionarItemPedido(itemPedido);

                        } else if (opcao.equalsIgnoreCase("N")) {

                            ResumoPedidoDTO resumo = itemPedidoService.getTotais(pedido.getId());
                            pedido.setTotalPedido(resumo.getValorTotal());

                            itemPedidoService.atualizarPedido(pedido.getId(), pedido.getTotalPedido());

                            System.out.println("Pedido finalizado com sucesso!");
                            System.out.println("Valor Total do pedido: R$ " + pedido.getTotalPedido());
                            System.out.println("Quantidade de itens: " + resumo.getTotalItens());
                            break;

                        } else {
                            System.out.println("Opção inválida!");
                        }

                    }
                } else {
                    System.out.println("Erro ao gerar o pedido");
                }
            }

            System.out.println("\nPressione Enter para continuar...");
            SistemaPedidos.scanner.nextLine();

        } catch (Exception e) {
            System.out.println("Erro ao criar o pedido: " + e.getMessage());
        }

    }

    private static Integer cadastrarPedido(Pedido pedido) {

        PedidoDao pedidoDao = new PedidoDao();
        Integer numeroPedidoGerado = null;

        try {
            numeroPedidoGerado = pedidoDao.cadastrarPedido(pedido);

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar o pedido: " + e.getMessage());
        }

        return numeroPedidoGerado;

    }

    // Listar Pedidos cadastrados
    // public static void listarPedidos() {

    // System.out.println("\n----------------------------------------------------------------------");
    // System.out.println("Lista de Pedidos
    // -----------------------------------------------------");
    // System.out.println("----------------------------------------------------------------------");
    // System.out.printf("%-10s %-30s %-30s", "ID", "Cliente", "Total em R$");
    // System.out.println("\n----------------------------------------------------------------------");

    // for(Pedido pedido : SistemaPedidos.pedidoList){
    // System.out.printf("%-10d %-30s %-30s\n", pedido.getIdPedido(),
    // pedido.getCliente().getNome(), pedido.getTotalPedido());
    // }

    // System.out.println("----------------------------------------------------------------------");
    // }

    // // Detalhes de um pedido
    // public static void detalharPedido() {

    // Pedido pedido = getPedido();

    // System.out.println("Número do Pedido " + pedido.getIdPedido());
    // System.out.println("----------------------------------------------------------------------");
    // System.out.println("Cliente: " + pedido.getCliente().getNome());
    // System.out.println("Valor Total: R$ " + pedido.getTotalPedido());
    // System.out.println("Lista de itens do Pedido: ");
    // System.out.println("--------------------------------------------------------------------------------------------------");
    // System.out.printf("%-10s %-30s %-20s %-20s %-20s", "ID", "Produto", "Valor
    // Unit", "Qtd", "Valor Total");
    // System.out.println("\n--------------------------------------------------------------------------------------------------");

    // int i = 1;
    // for(ItemPedido item : pedido.getItensPedido()){
    // System.out.printf("%-10d %-30s %-20s %-20s %-20s\n", i,
    // item.getProduto().getNome(), item.getValorUnitario(), item.getQuantidade(),
    // item.getValorTotal());
    // i++;
    // }

    // System.out.println("--------------------------------------------------------------------------------------------------");
    // System.out.println("\nPressione Enter para continuar...");
    // SistemaPedidos.scanner.nextLine();
    // }

    // // Busca um pedido pelo ID
    // public static Pedido getPedido() {

    // Pedido pedidoBusca = null;
    // boolean pedidoValido = false;

    // while (!pedidoValido) {
    // Integer numeroPedido = MercadoService.getID("Informe o ID do pedido: ");

    // for (Pedido pedido : SistemaPedidos.pedidoList) {
    // if (pedido.getIdPedido().equals(numeroPedido)) {
    // pedidoBusca = pedido;
    // break;
    // }
    // }

    // if (pedidoBusca!=null) {
    // pedidoValido = true;
    // }else{
    // System.out.println("Erro: Pedido não econtrado");
    // }
    // }
    // return pedidoBusca;
    // }

}
