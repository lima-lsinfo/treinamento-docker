package br.com.treinamento.projeto;

import br.com.terinamento.projeto.model.Biblioteca;
import br.com.terinamento.projeto.model.Livro;
import br.com.terinamento.projeto.model.Usuario;

/**
 * Sistema para empréstimo de livros
 */
public class App 
{
    public static void main( String[] args )
    {
    	Usuario usuario = new Usuario("Leandro", 37, "email@gmail.com", "11983420932");
        Livro livro = new Livro("Ted Talks", "Chris Anderson", false);

        Biblioteca biblioteca = new Biblioteca(livro, usuario);

        biblioteca.exibirDetalhesLivro();
        biblioteca.getLivro().realizarEmprestimo();
        biblioteca.exibirDetalhesLivro();
        biblioteca.getLivro().realizarDevolucao();
        biblioteca.exibirDetalhesLivro();
    }
}
