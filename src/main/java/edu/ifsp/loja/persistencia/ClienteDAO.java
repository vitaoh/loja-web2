package edu.ifsp.loja.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.ifsp.loja.modelo.Cliente;

public class ClienteDAO {
	
	public Cliente findById(int id) {
		Cliente cliente = new Cliente();

		try {
			
			Connection conn = DatabaseConnector.getConnection();
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT id, nome, email FROM cliente WHERE id = " + id);
			
			if (rs.next()) {
				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setEmail(rs.getString("email"));
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cliente;
	}
}
