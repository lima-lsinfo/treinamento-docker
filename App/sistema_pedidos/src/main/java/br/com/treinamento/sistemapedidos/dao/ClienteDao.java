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
import br.com.treinamento.sistemapedidos.model.Cliente;

public class ClienteDao {

    public void sqlCadastrarCLiente(Cliente cliente) throws SQLException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.abreConexao();

        String sqlInsereCliente = "INSERT INTO cliente (nome_cliente, email_cliente) VALUES (?,?)";

        PreparedStatement pstm = connection.prepareStatement(sqlInsereCliente, Statement.RETURN_GENERATED_KEYS);
        pstm.setString(1, cliente.getNome());
        pstm.setString(2, cliente.getEmail());

        pstm.execute();
        pstm.close();
        connection.close();

    }

    public List<Cliente> sqlListarClientes() throws SQLException {

        List<Cliente> clienteList = new ArrayList<>();

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.abreConexao();

        String sqlListaClientes = "SELECT id_cliente, nome_cliente, email_cliente FROM cliente";

        PreparedStatement pstm = connection.prepareStatement(sqlListaClientes);
        pstm.execute();

        ResultSet rst = pstm.getResultSet();

        while (rst.next()) {
            Cliente cliente = new Cliente();
            cliente.setId(rst.getInt("id_cliente"));
            cliente.setNome(rst.getString("nome_cliente"));
            cliente.setEmail(rst.getString("email_cliente"));
            clienteList.add(cliente);
        }

        rst.close();
        pstm.close();
        connection.close();

        return clienteList;
    }

    public Optional<Cliente> sqlBuscarClientePorId(Integer codigo) throws SQLException {

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.abreConexao();

        String sqlBuscaClientePorId = "SELECT id_cliente, nome_cliente, email_cliente FROM cliente WHERE id_cliente = ?";

        PreparedStatement pstm = connection.prepareStatement(sqlBuscaClientePorId);
        pstm.setInt(1, codigo);
        pstm.execute();

        ResultSet rst = pstm.getResultSet();

        Optional<Cliente> clienteOptional = Optional.empty();

        if (rst.next()) {
            Cliente cliente = new Cliente();
            cliente.setId(rst.getInt("id_cliente"));
            cliente.setNome(rst.getString("nome_cliente"));
            cliente.setEmail(rst.getString("email_cliente"));

            clienteOptional = Optional.of(cliente);
        }

        rst.close();
        pstm.close();
        connection.close();

        return clienteOptional;
    }

    public void sqlExcluirCliente(Integer codigo) throws SQLException {

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.abreConexao();

        String sqlDeletaCliente = "DELETE FROM cliente WHERE id_cliente = ?";

        PreparedStatement pstm = connection.prepareStatement(sqlDeletaCliente);
        pstm.setInt(1, codigo);

        pstm.execute();
        pstm.close();
        connection.close();
    }

    public void sqlAtualizarCliente(Cliente cliente) throws SQLException {

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.abreConexao();

        String sqlAtualizaCliente = "UPDATE cliente SET nome_cliente = ?, email_cliente = ? WHERE id_cliente = ?";

        PreparedStatement pstm = connection.prepareStatement(sqlAtualizaCliente);
        pstm.setString(1, cliente.getNome());
        pstm.setString(2, cliente.getEmail());
        pstm.setInt(3, cliente.getId());

        pstm.execute();
        pstm.close();
        connection.close();

    }
}
