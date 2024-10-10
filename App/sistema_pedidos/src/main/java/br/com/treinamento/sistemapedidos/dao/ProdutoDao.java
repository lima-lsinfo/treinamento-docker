package br.com.treinamento.sistemapedidos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.treinamento.sistemapedidos.factory.ConnectionFactory;
import br.com.treinamento.sistemapedidos.model.Produto;

public class ProdutoDao {

	public void sqlCadastrarProduto(Produto produto) throws SQLException {

		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.abreConexao();

		String sqlInsereProduto = "INSERT INTO produto (nome_produto, valor_produto) VALUES (?,?)";

		PreparedStatement pstm = connection.prepareStatement(sqlInsereProduto, Statement.RETURN_GENERATED_KEYS);
		pstm.setString(1, produto.getNome());
		pstm.setBigDecimal(2, produto.getPreco());

		pstm.execute();
		pstm.close();
		connection.close();
	}

	public List<Produto> sqlListarProdutos() throws SQLException{
		
		List<Produto> produtoList = new ArrayList<>();
		
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.abreConexao();

		String sqlListaProdutos = "SELECT id_produto, nome_produto, valor_produto FROM produto";
		
		PreparedStatement pstm = connection.prepareStatement(sqlListaProdutos);
		pstm.execute();
		
		ResultSet rst = pstm.getResultSet();
		
		while (rst.next()) {
			Produto produto = new Produto();
			produto.setId(rst.getInt("id_produto"));
			produto.setNome(rst.getString("nome_produto"));
			produto.setPreco(rst.getBigDecimal("valor_produto"));
			produtoList.add(produto);
		}
		
		rst.close();
		pstm.close();
		connection.close();
		
		return produtoList;
	}

	public Optional<Produto> sqlBuscarProdutoPorId(Integer codigo) throws SQLException {
		
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.abreConexao();
		
		String sqlBuscaProdutoPorId = "SELECT id_produto, nome_produto, valor_produto FROM produto WHERE id_produto = ?";
		
		PreparedStatement pstm = connection.prepareStatement(sqlBuscaProdutoPorId);
		pstm.setInt(1, codigo);
		pstm.execute();
		
		ResultSet rst = pstm.getResultSet();

		Optional<Produto> produtoOptional = Optional.empty();
		
		if (rst.next()) {
			Produto produto = new Produto();
			produto.setId(rst.getInt("id_produto"));
			produto.setNome(rst.getString("nome_produto"));
			produto.setPreco(rst.getBigDecimal("valor_produto"));
			
			produtoOptional = Optional.of(produto);
		}
		
		rst.close();
		pstm.close();
		connection.close();
		
		return produtoOptional;
	}

	public void sqlExcluirProduto(Integer codigo) throws SQLException {
		
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.abreConexao();

		String sqlDeletaProduto = "DELETE FROM produto WHERE id_produto = ?";

		PreparedStatement pstm = connection.prepareStatement(sqlDeletaProduto);
		pstm.setInt(1, codigo);

		pstm.execute();
		pstm.close();
		connection.close();
	}

	public void sqlAtualizarProduto(Produto produto) throws SQLException {
		
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.abreConexao();

		String sqlAtualizaProduto = "UPDATE produto SET nome_produto = ?, valor_produto = ? WHERE id_produto = ?";

		PreparedStatement pstm = connection.prepareStatement(sqlAtualizaProduto);
		pstm.setString(1, produto.getNome());
		pstm.setBigDecimal(2, produto.getPreco());
		pstm.setInt(3, produto.getId());

		pstm.execute();
		pstm.close();
		connection.close();
		
	}

}
