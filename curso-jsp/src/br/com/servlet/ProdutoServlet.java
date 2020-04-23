package br.com.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.beans.BeanProduto;
import br.com.dao.DaoProduto;

@WebServlet("/salvarProduto")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoProduto daoProduto = new DaoProduto();

	public ProdutoServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao") != null ? request.getParameter("acao") : "listartodos";
		String valorProduto = request.getParameter("valorProduto");
		RequestDispatcher view = request.getRequestDispatcher("/cadastroProdutos.jsp");

		if (acao.equalsIgnoreCase("listartodos")) {
			request.setAttribute("produtos", daoProduto.listarProdutos());
		} else if (acao.equalsIgnoreCase("delete")) {
			daoProduto.deletarProduto(valorProduto);
			request.setAttribute("produtos", daoProduto.listarProdutos());
		} else if (acao.equalsIgnoreCase("editar")) {
			BeanProduto beanProduto = daoProduto.buscarProduto(valorProduto);
			request.setAttribute("valorProduto", beanProduto);
			request.setAttribute("produtos", daoProduto.listarProdutos());
		}

		request.setAttribute("categorias", daoProduto.listarCategorias());
		view.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {

			RequestDispatcher view = request.getRequestDispatcher("/cadastroProdutos.jsp");
			request.setAttribute("produtos", daoProduto.listarProdutos());
			view.forward(request, response);

		} else {

			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String quantidade = request.getParameter("quantidade");
			String valor = request.getParameter("valor");
			String valorParce = valor.replaceAll("\\.", "");
			String categoria = request.getParameter("categoria_id");

			BeanProduto produto = new BeanProduto();
			produto.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			produto.setNome(nome);
			produto.setQuantidade(Double.valueOf(quantidade));
			produto.setValor(Double.valueOf(valorParce.replaceAll("\\,", ".")));
			produto.setCategoria_id(Long.parseLong(categoria));

			boolean podeInserir = false;

			if (nome != null && !nome.isEmpty()) {
				podeInserir = validarProduto(request, daoProduto, id, nome);
			}

			if (id == null || id.isEmpty() && podeInserir) {
				daoProduto.salvarProduto(produto);
			} else if (id != null && !id.isEmpty() && podeInserir) {
				daoProduto.atualizarProduto(produto);
			}

			if (!podeInserir) {
				request.setAttribute("valorProduto", produto);
			}

			RequestDispatcher view = request.getRequestDispatcher("/cadastroProdutos.jsp");
			request.setAttribute("produtos", daoProduto.listarProdutos());
			request.setAttribute("categorias", daoProduto.listarCategorias());
			view.forward(request, response);
		}
	}

	private static boolean validarProduto(HttpServletRequest request, DaoProduto daoProduto, String id, String nome) {

		if (id == null || id.isEmpty()) {
			if (!daoProduto.validarNome(nome)) {
				request.setAttribute("msg", "Nome do Produto inválido!");
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
}
