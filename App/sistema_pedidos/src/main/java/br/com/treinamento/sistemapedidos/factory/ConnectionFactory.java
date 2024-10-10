package br.com.treinamento.sistemapedidos.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public ConnectionFactory(){

    }

    public Connection abreConexao() throws SQLException{
        return DriverManager.getConnection("jdbc:postgresql://localhost/sistema_pedidos","root","123456");
    }
}