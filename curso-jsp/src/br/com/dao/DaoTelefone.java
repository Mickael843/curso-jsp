package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.beans.BeanTelefone;
import br.com.connection.SingleConnection;

public class DaoTelefone {

	private Connection connection;

	public DaoTelefone() {
		connection = SingleConnection.getConnection();
	}

	public void salvarTelefone(BeanTelefone telefone) {

		String sql = "INSERT INTO telefone(numero, tipo, usuario) VALUES(?, ?, ?)";

		try {

			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUsuario());

			insert.execute();

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao salvar Telefone", e);
		}
	}

	public List<BeanTelefone> listarTelefone(Long user) {

		List<BeanTelefone> telefones = new ArrayList<BeanTelefone>();

		String sql = "SELECT * FROM telefone WHERE usuario = " + user;

		try {

			PreparedStatement select = connection.prepareStatement(sql);
			ResultSet resultSet = select.executeQuery();

			while (resultSet.next()) {
				BeanTelefone telefone = new BeanTelefone();
				telefone.setId(resultSet.getLong("id"));
				telefone.setNumero(resultSet.getString("numero"));
				telefone.setTipo(resultSet.getString("tipo"));
				telefone.setUsuario(resultSet.getLong("usuario"));
				telefones.add(telefone);
			}
			return telefones;

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao listar Telefone", e);
		}
	}

	public void deletarTelefone(String id) {

		String sql = "DELETE FROM telefone WHERE id = '" + id + "'";

		try {

			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao deletar Telefone", e);
		}
	}
}
