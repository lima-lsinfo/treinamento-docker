package br.com.treinamento.sistemapedidos.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.treinamento.sistemapedidos.factory.ConnectionFactory;
import br.com.treinamento.sistemapedidos.model.Pedido;

public class PedidoDao {

    public Integer cadastrarPedido(Pedido pedido) throws SQLException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.abreConexao();

        String sqlInsere = "INSERT INTO tb_pedido(id_cliente, total_pedido) values (?,?)";

        PreparedStatement pstm = connection.prepareStatement(sqlInsere, Statement.RETURN_GENERATED_KEYS);
        pstm.setInt(1, pedido.getCliente().getId());
        pstm.setBigDecimal(2, pedido.getTotalPedido());
        pstm.execute();

        Integer numeroPedidoGerado = null;
        ResultSet rs = pstm.getGeneratedKeys();

        if (rs.next()) {
            numeroPedidoGerado = rs.getInt(1);
        }

        pstm.close();
        connection.close();

        return numeroPedidoGerado;
    }
    
    
    public void atualizarPedido(Integer idPedido, BigDecimal totalPedido) throws SQLException {
        
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.abreConexao();

        String sqlAtualiza = "UPDATE tb_pedido set total_pedido = ? WHERE id_pedido = ?";

        PreparedStatement pstm = connection.prepareStatement(sqlAtualiza);
        pstm.setBigDecimal(1, totalPedido);
        pstm.setInt(2, idPedido);

        pstm.execute();
        pstm.close();
        connection.close();
        
    }

    
}
