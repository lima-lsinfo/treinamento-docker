package br.com.treinamento.sistemapedidos.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import br.com.treinamento.sistemapedidos.dao.ItemPedidoDao;
import br.com.treinamento.sistemapedidos.dao.PedidoDao;
import br.com.treinamento.sistemapedidos.dto.ResumoPedidoDTO;
import br.com.treinamento.sistemapedidos.model.ItemPedido;

public class ItemPedidoService {

    public void adicionarItemPedido(ItemPedido itemPedido) {

        ItemPedidoDao itemPedidoDao = new ItemPedidoDao();

        try {
            itemPedidoDao.criarItemPedido(itemPedido);
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar item ao pedido: " + e.getMessage());
        }
    }

    public ResumoPedidoDTO getTotais(Integer idPedido) throws SQLException {

        ItemPedidoDao itemPedidoDao = new ItemPedidoDao();

        return itemPedidoDao.buscaResumoPedido(idPedido);
    }

    public void atualizarPedido(Integer idPedido, BigDecimal totalPedido) throws SQLException {

        PedidoDao pedidoDao = new PedidoDao();

        try {
            pedidoDao.atualizarPedido(idPedido, totalPedido);
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pedido: " + e.getMessage());
        }

    }
}
