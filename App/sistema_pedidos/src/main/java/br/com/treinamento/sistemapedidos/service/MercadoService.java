package br.com.treinamento.sistemapedidos.service;

import br.com.treinamento.sistemapedidos.main.SistemaPedidos;

public class MercadoService {
	
	public static void montarMenu() {
		System.out.println("\n|---------------------------------------|");
		System.out.println("| Sistema De Compras -------------------|");
		System.out.println("|---------------------------------------|");
		System.out.println("| 1 - Clientes                          |");
		System.out.println("| 2 - Produtos                          |");
		System.out.println("| 3 - Pedidos                           |");
		System.out.println("| 4 - Sair                              |");
		System.out.println("|---------------------------------------|\n");
		System.out.print("Informe uma opção:");
	}
		
	public static Integer getID(String textoEntrada) {
		Integer codigo = null;
		while (codigo == null) {
			try {
				System.out.print(textoEntrada);
				codigo = SistemaPedidos.scanner.nextInt();
				SistemaPedidos.scanner.nextLine(); 
			} catch (Exception e) {
				System.out.println("Erro: O código deve ser informado utilizando um número do tipo inteiro");
				SistemaPedidos.scanner.nextLine();
			}
		}
		return codigo;
	}
	
	
	public static Integer getNumeroInt(String textoEntrada) {
		Integer numero = null;
		while (numero == null) {
			try {
				System.out.print(textoEntrada);
				numero = SistemaPedidos.scanner.nextInt();
				SistemaPedidos.scanner.nextLine(); 
			} catch (Exception e) {
				System.out.println("Erro: Por favor informar um número inteiro.");
				SistemaPedidos.scanner.nextLine();
			}
		}
		return numero;
	}
}
