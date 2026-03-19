package edu.ifsp.loja.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.ifsp.loja.modelo.Produto;

public class ProdutoDAO {
	public Produto findById(int id) {
		Produto produto = new Produto();
		
		try {
			
			Connection conn = DatabaseConnector.getConnection();
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT id, descricao, preco FROM produto WHERE id = " + id);
			
			if (rs.next()) {
				produto.setId(rs.getInt("id"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setPreco(rs.getDouble("preco"));
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return produto;
	}
	
	public List<Produto> find(String descricao, double preco) {
		List<Produto> resultado = new ArrayList<>();

		try {
			
			Connection conn = DatabaseConnector.getConnection();
			
			PreparedStatement ps = conn.prepareStatement("SELECT id, descricao, preco FROM produto WHERE descricao LIKE ? AND preco > ?");	
			ps.setString(1, "%" + descricao + "%");
			ps.setDouble(2, preco);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setPreco(rs.getDouble("preco"));
				resultado.add(produto);
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

		
		return resultado;
	}
}
