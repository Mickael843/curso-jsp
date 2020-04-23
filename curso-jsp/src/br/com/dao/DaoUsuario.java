package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.beans.BeanUsuario;
import br.com.connection.SingleConnection;

public class DaoUsuario {

	private Connection connection;

	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanUsuario usuario) {

		String sql = "INSERT INTO usuario(login, senha, nome, telefone, cep, rua, cidade, estado, bairro, ibge, fotobase64, "
				+ "contenttype, curriculobase64, contenttypecurriculo, fotobase64miniatura, ativo, sexo, perfil) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

		try {

			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, usuario.getLogin());
			insert.setString(2, usuario.getSenha());
			insert.setString(3, usuario.getNome());
			insert.setString(4, usuario.getTelefone());
			insert.setString(5, usuario.getCep());
			insert.setString(6, usuario.getRua());
			insert.setString(7, usuario.getCidade());
			insert.setString(8, usuario.getEstado());
			insert.setString(9, usuario.getBairro());
			insert.setString(10, usuario.getIbge());
			insert.setString(11, usuario.getFotoBase64());
			insert.setString(12, usuario.getContentType());
			insert.setString(13, usuario.getCurriculoBase64());
			insert.setString(14, usuario.getContentTypeCurriculo());
			insert.setString(15, usuario.getFotoBase64Miniatura());
			insert.setBoolean(16, usuario.isAtivo());
			insert.setString(17, usuario.getSexo());
			insert.setString(18, usuario.getPerfil());
			insert.execute();

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao salvar usuario", e);
		}
	}

	public List<BeanUsuario> listar() {
		String sql = "SELECT * FROM usuario WHERE login <> 'admin'";
		return consultarUsuarios(sql);
	}

	public List<BeanUsuario> listar(String descricaoConsulta) {
		String sql = "SELECT * FROM usuario WHERE login <> 'admin' AND nome LIKE '%" + descricaoConsulta + "%'";
		return consultarUsuarios(sql);
	}

	private List<BeanUsuario> consultarUsuarios(String sql) {
		List<BeanUsuario> usuarios = new ArrayList<BeanUsuario>();
		try {
			PreparedStatement select = connection.prepareStatement(sql);
			ResultSet resultSet = select.executeQuery();

			while (resultSet.next()) {
				BeanUsuario usuario = new BeanUsuario();
				usuario.setLogin(resultSet.getString("login"));
				usuario.setSenha(resultSet.getString("senha"));
				usuario.setId(resultSet.getLong("id"));
				usuario.setNome(resultSet.getString("nome"));
				usuario.setTelefone(resultSet.getString("telefone"));
				usuario.setCep(resultSet.getString("cep"));
				usuario.setRua(resultSet.getString("rua"));
				usuario.setCidade(resultSet.getString("cidade"));
				usuario.setEstado(resultSet.getString("estado"));
				usuario.setBairro(resultSet.getString("bairro"));
				usuario.setIbge(resultSet.getString("ibge"));
				usuario.setFotoBase64Miniatura(resultSet.getString("fotobase64miniatura"));
				usuario.setContentType(resultSet.getString("contenttype"));
				usuario.setCurriculoBase64(resultSet.getString("curriculobase64"));
				usuario.setContentTypeCurriculo(resultSet.getString("contenttypecurriculo"));
				usuario.setAtivo(resultSet.getBoolean("ativo"));
				usuario.setSexo(resultSet.getString("sexo"));
				usuario.setPerfil(resultSet.getString("perfil"));
				usuarios.add(usuario);
			}

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao listar usuarios", e);
		}
		return usuarios;
	}

	public void delete(String id) {
		String sql = "DELETE FROM usuario WHERE id = '" + id + "' AND login <> 'admin'";

		try {

			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao deletar usuario", e);
		}
	}

	public BeanUsuario consultar(String id) {

		String sql = "SELECT * FROM usuario WHERE id = '" + id + "' AND login <> 'admin'";

		try {

			PreparedStatement select = connection.prepareStatement(sql);
			ResultSet resultSet = select.executeQuery();

			if (resultSet.next()) {
				BeanUsuario usuario = new BeanUsuario();
				usuario.setLogin(resultSet.getString("login"));
				usuario.setSenha(resultSet.getString("senha"));
				usuario.setId(resultSet.getLong("id"));
				usuario.setNome(resultSet.getString("nome"));
				usuario.setTelefone(resultSet.getString("telefone"));
				usuario.setCep(resultSet.getString("cep"));
				usuario.setRua(resultSet.getString("rua"));
				usuario.setBairro(resultSet.getString("bairro"));
				usuario.setCidade(resultSet.getString("cidade"));
				usuario.setEstado(resultSet.getString("estado"));
				usuario.setIbge(resultSet.getString("ibge"));
				usuario.setFotoBase64(resultSet.getString("fotobase64"));
				usuario.setFotoBase64Miniatura(resultSet.getString("fotobase64miniatura"));
				usuario.setContentType(resultSet.getString("contenttype"));
				usuario.setCurriculoBase64(resultSet.getString("curriculobase64"));
				usuario.setContentTypeCurriculo(resultSet.getString("contenttypecurriculo"));
				usuario.setAtivo(resultSet.getBoolean("ativo"));
				usuario.setSexo(resultSet.getString("sexo"));
				usuario.setPerfil(resultSet.getString("perfil"));
				return usuario;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean validarLogin(String login) {

		String sql = "SELECT COUNT(1) AS qtd FROM usuario WHERE login = '" + login + "'";

		try {

			PreparedStatement select = connection.prepareStatement(sql);
			ResultSet resultSet = select.executeQuery();

			if (resultSet.next()) {
				return resultSet.getInt("qtd") <= 0;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	public boolean validarSenha(String senha) {

		String sql = "SELECT COUNT(1) AS qtd FROM usuario WHERE senha = '" + senha + "'";

		try {

			PreparedStatement select = connection.prepareStatement(sql);
			ResultSet resultSet = select.executeQuery();

			if (resultSet.next()) {
				return resultSet.getInt("qtd") <= 0;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	public void atualizar(BeanUsuario usuario) {

		int fotoBase64Number = 14;
		int fotoContentTypeNumber = 15;
		int curriculoBase64Number = 16;
		int curriculoContentTypeNumber = 17;
		int fotoBase64MiniaturaNumber = 18;

		StringBuilder sql = new StringBuilder();

		sql.append("UPDATE usuario SET login=?, senha=?, nome=?, telefone=?,");
		sql.append(" cep=?, rua=?, cidade=?, estado=?, bairro=?, ibge=?, ativo=?, sexo=?, perfil=?");

		if (usuario.isAtualizarImagem()) {
			sql.append(", fotobase64=?, contenttype=?");
		}

		if (usuario.isAtualizarPdf()) {
			sql.append(", curriculobase64=?, contenttypecurriculo=?");
		}

		if (usuario.isAtualizarImagem()) {
			sql.append(", fotobase64miniatura=?");
		}

		if (usuario.isAtualizarImagem() && !usuario.isAtualizarPdf()) {
			fotoBase64MiniaturaNumber = 16;
		} else if (!usuario.isAtualizarImagem() && usuario.isAtualizarPdf()) {
			curriculoBase64Number = 14;
			curriculoContentTypeNumber = 15;
		}

		sql.append(" WHERE id= " + usuario.getId());

		try {

			PreparedStatement updade = connection.prepareStatement(sql.toString());
			updade.setString(1, usuario.getLogin());
			updade.setString(2, usuario.getSenha());
			updade.setString(3, usuario.getNome());
			updade.setString(4, usuario.getTelefone());
			updade.setString(5, usuario.getCep());
			updade.setString(6, usuario.getRua());
			updade.setString(7, usuario.getCidade());
			updade.setString(8, usuario.getEstado());
			updade.setString(9, usuario.getBairro());
			updade.setString(10, usuario.getIbge());
			updade.setBoolean(11, usuario.isAtivo());
			updade.setString(12, usuario.getSexo());
			updade.setString(13, usuario.getPerfil());

			if (usuario.isAtualizarImagem()) {
				updade.setString(fotoBase64Number, usuario.getFotoBase64());
				updade.setString(fotoContentTypeNumber, usuario.getContentType());
			}

			if (usuario.isAtualizarPdf()) {
				updade.setString(curriculoBase64Number, usuario.getCurriculoBase64());
				updade.setString(curriculoContentTypeNumber, usuario.getContentTypeCurriculo());
			}

			if (usuario.isAtualizarImagem()) {
				updade.setString(fotoBase64MiniaturaNumber, usuario.getFotoBase64Miniatura());
			}
			updade.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar usuario", e);
		}
	}
}
