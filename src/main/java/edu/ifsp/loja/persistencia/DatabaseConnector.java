package edu.ifsp.loja.persistencia;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseConnector {
	public static Connection getConnection() throws SQLException {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:/comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/lojadb");
			return ds.getConnection();
			
		} catch (NamingException e) {
			throw new IllegalArgumentException(e);
		}
 	}

}
