package br.com.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.dao.DaoLogin;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoLogin daoLogin = new DaoLogin();

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String login = request.getParameter("login");
			String senha = request.getParameter("senha");

			if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {

				if (daoLogin.validarLogin(login, senha)) {
					RequestDispatcher dis = request.getRequestDispatcher("acessoliberado.jsp");
					dis.forward(request, response);
				} else {
					RequestDispatcher dis = request.getRequestDispatcher("acessonegado.jsp");
					dis.forward(request, response);
				}
			} else {
				RequestDispatcher dis = request.getRequestDispatcher("index.jsp");
				dis.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
