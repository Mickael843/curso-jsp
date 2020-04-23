package br.com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.beans.BeanUsuario;
import br.com.dao.DaoUsuario;

@WebServlet("/pesquisar")
public class PesquisaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoUsuario daoUsuario = new DaoUsuario();

	public PesquisaServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String descricaoConsulta = request.getParameter("descricaoConsulta");

		if (descricaoConsulta != null && !descricaoConsulta.trim().isEmpty()) {
			List<BeanUsuario> lista = daoUsuario.listar(descricaoConsulta);

			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuarios.jsp");
			request.setAttribute("usuarios", lista);
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuarios.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		}
	}
}
