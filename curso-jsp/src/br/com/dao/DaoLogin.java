package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.connection.SingleConnection;

public class DaoLogin {

	private Connection connection;
	
	public DaoLogin() {
		this.connection = SingleConnection.getConnection();
	}
	
	public boolean validarLogin(String login, String senha) throws Exception {
		
		String sql = "SELECT * FROM usuario WHERE login = '" + login + "' AND senha = '" + senha + "'";
			
			PreparedStatement select = connection.prepareStatement(sql);
			ResultSet resultSet = select.executeQuery();
			
			if(resultSet.next()) {
				return true;
			} else {
				return false;
			}	
	}
}
