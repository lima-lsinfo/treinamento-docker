package br.com.treinamento.sistemapedidos.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.treinamento.sistemapedidos.dto.ResumoPedidoDTO;
import br.com.treinamento.sistemapedidos.factory.ConnectionFactory;
import br.com.treinamento.sistemapedidos.model.ItemPedido;

public class ItemPedidoDao {

    public void criarItemPedido(ItemPedido itemPedido) throws SQLException {

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.abreConexao();

        String sqlInsere = "INSERT INTO tb_itens_pedido(id_pedido, id_produto, quantidade, valor_unitario, valor_total) values (?,?,?,?,?)";

        PreparedStatement pstm = connection.prepareStatement(sqlInsere);
        pstm.setInt(1, itemPedido.getPedido().getId());
        pstm.setInt(2, itemPedido.getProduto().getId());
        pstm.setInt(3, itemPedido.getQuantidade());
        pstm.setBigDecimal(4, itemPedido.getValorUnitario());
        pstm.setBigDecimal(5, itemPedido.getValorTotal());

        pstm.execute();
        pstm.close();
        connection.close();

    }

    public ResumoPedidoDTO buscaResumoPedido(Integer idPedido) throws SQLException {

        ResumoPedidoDTO resumoPedidoDTO = new ResumoPedidoDTO();
        resumoPedidoDTO.setTotalItens(0);
        resumoPedidoDTO.setValorTotal(BigDecimal.ZERO);

        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.abreConexao();

        String sqlResumoPorId = "SELECT COUNT(id) as totalItens, SUM(valor_total) as valorTotal FROM tb_itens_pedido WHERE id_pedido = ?";

        PreparedStatement pstm = connection.prepareStatement(sqlResumoPorId);
        pstm.setInt(1, idPedido);
        pstm.execute();

        ResultSet rst = pstm.getResultSet();

        if (rst.next()) {
            resumoPedidoDTO.setValorTotal(rst.getBigDecimal("valor_total"));
            resumoPedidoDTO.setTotalItens(rst.getInt("qtd_itens"));
        }

        rst.close();
        pstm.close();
        connection.close();

        return resumoPedidoDTO;

    }
}
