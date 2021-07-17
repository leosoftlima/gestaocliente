package br.edu.ifpe.gestaocliente.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {
	
	public static Connection getConexaoMySQL() throws SQLException, ClassNotFoundException {
		Connection connection = null; 
		Class.forName("com.mysql.jdbc.Driver");	
		connection = DriverManager.getConnection("jdbc:mysql://localhost/ifpe_bd_cliente","root","");
		return connection;
	}
	
}
