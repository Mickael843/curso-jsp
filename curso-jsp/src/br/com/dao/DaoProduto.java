package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.beans.BeanCategoria;
import br.com.beans.BeanProduto;
import br.com.connection.SingleConnection;

public class DaoProduto {

	private Connection connection;

	public DaoProduto() {
		connection = SingleConnection.getConnection();
	}

	public void salvarProduto(BeanProduto produto) {

		String sql = "INSERT INTO produto(nome, quantidade, valor, categoria_id) VALUES(?, ?, ?, ?)";

		try {

			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, produto.getNome());
			insert.setDouble(2, produto.getQuantidade());
			insert.setDouble(3, produto.getValor());
			insert.setLong(4, produto.getCategoria_id());
			insert.execute();

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao salvar Produto", e);
		}
	}

	public BeanProduto buscarProduto(String id) {

		String sql = "SELECT * FROM produto WHERE id = '" + id + "'";

		try {

			PreparedStatement select = connection.prepareStatement(sql);
			ResultSet resultSet = select.executeQuery();

			if (resultSet.next()) {
				BeanProduto produto = new BeanProduto();
				produto.setId(resultSet.getLong("id"));
				produto.setNome(resultSet.getString("nome"));
				produto.setQuantidade(resultSet.getDouble("quantidade"));
				produto.setValor(resultSet.getDouble("valor"));
				produto.setCategoria_id(resultSet.getLong("categoria_id"));

				return produto;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro na busca de Produtos", e);
		}
		return null;
	}

	public void atualizarProduto(BeanProduto produto) {

		String sql = "UPDATE produto SET nome = ?, quantidade = ?, valor = ?, categoria_id = ? WHERE id = "
				+ produto.getId();

		try {

			PreparedStatement update = connection.prepareStatement(sql);
			update.setString(1, produto.getNome());
			update.setDouble(2, produto.getQuantidade());
			update.setDouble(3, produto.getValor());
			update.setLong(4, produto.getCategoria_id());
			update.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar produto", e);
		}
	}

	public List<BeanProduto> listarProdutos() {

		List<BeanProduto> produtos = new ArrayList<BeanProduto>();

		String sql = "SELECT * FROM produto";

		try {

			PreparedStatement select = connection.prepareStatement(sql);
			ResultSet resultSet = select.executeQuery();

			while (resultSet.next()) {
				BeanProduto produto = new BeanProduto();
				produto.setId(resultSet.getLong("id"));
				produto.setNome(resultSet.getString("nome"));
				produto.setQuantidade(resultSet.getDouble("quantidade"));
				produto.setValor(resultSet.getDouble("valor"));
				produto.setCategoria_id(resultSet.getLong("categoria_id"));

				produtos.add(produto);
			}
			return produtos;

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao listar produtos", e);
		}
	}

	public void deletarProduto(String id) {

		String sql = "DELETE FROM produto WHERE id = '" + id + "'";

		try {

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.execute();

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao deletar produto", e);
		}
	}

	public boolean validarNome(String nome) {
		String sql = "SELECT COUNT(1) AS qtd FROM produto WHERE nome = '" + nome + "'";

		try {

			PreparedStatement select = connection.prepareStatement(sql);
			ResultSet resultSet = select.executeQuery();

			if (resultSet.next()) {
				return resultSet.getInt("qtd") <= 0;
			}

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao validarNome", e);
		}
		return false;
	}

	public List<BeanCategoria> listarCategorias() {

		List<BeanCategoria> categorias = new ArrayList<BeanCategoria>();

		String sql = "SELECT * FROM categoria";

		try {

			PreparedStatement select = connection.prepareStatement(sql);
			ResultSet resultSet = select.executeQuery();

			while (resultSet.next()) {
				BeanCategoria categoria = new BeanCategoria();
				categoria.setId(resultSet.getLong("id"));
				categoria.setNome(resultSet.getString("nome"));
				categorias.add(categoria);
			}
			return categorias;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
