package br.com.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.beans.BeanUsuario;
import br.com.beans.BeanTelefone;
import br.com.dao.DaoTelefone;
import br.com.dao.DaoUsuario;

@WebServlet("/salvarTelefones")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoUsuario daoUsuario = new DaoUsuario();

	private DaoTelefone daoTelefone = new DaoTelefone();

	private BeanUsuario usuario = null;

	public TelefoneServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");
		String user = request.getParameter("user");

		if (user != null) {

			if (acao.equalsIgnoreCase("listarTelefones")) {

				usuario = daoUsuario.consultar(user);

				request.getSession().setAttribute("user", usuario);
				RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
				request.setAttribute("telefones", daoTelefone.listarTelefone(usuario.getId()));
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("delete")) {
				String fone = request.getParameter("fone");

				daoTelefone.deletarTelefone(fone);

				RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
				request.setAttribute("telefones", daoTelefone.listarTelefone(usuario.getId()));
				view.forward(request, response);
			}
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuarios.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BeanTelefone telefone = new BeanTelefone();

		BeanUsuario usuario = (BeanUsuario) request.getSession().getAttribute("user");

		String acao = request.getParameter("acao");
		String numero = request.getParameter("numero");
		String tipo = request.getParameter("tipo");

		telefone.setUsuario(usuario.getId());

		if (acao == null || (acao != null && !acao.equalsIgnoreCase("voltar"))) {
			if (numero != null && !numero.isEmpty()) {

				telefone.setNumero(numero);
				telefone.setTipo(tipo);

				daoTelefone.salvarTelefone(telefone);

				request.setAttribute("msg", "Telefone salvo com sucesso!");
			}

			request.getSession().setAttribute("user", usuario);
			request.setAttribute("user", usuario);

			RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
			request.setAttribute("telefones", daoTelefone.listarTelefone(telefone.getUsuario()));
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuarios.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		}
	}
}
