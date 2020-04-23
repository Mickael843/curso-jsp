package br.com.servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import br.com.beans.BeanUsuario;
import br.com.dao.DaoUsuario;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoUsuario daoUsuario = new DaoUsuario();

	public UsuarioServlet() {
		super();
	}

	@SuppressWarnings("static-access")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");
		String user = request.getParameter("user");

		if (acao != null && acao.equalsIgnoreCase("delete")) {
			daoUsuario.delete(user);
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuarios.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		} else if (acao != null && acao.equalsIgnoreCase("editar")) {
			BeanUsuario beanCursoJsp = daoUsuario.consultar(user);
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuarios.jsp");
			request.setAttribute("user", beanCursoJsp);
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		} else if (acao != null && acao.equalsIgnoreCase("listartodos")) {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuarios.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		} else if (acao != null && acao.equalsIgnoreCase("baixar")) {
			BeanUsuario usuario = daoUsuario.consultar(user);
			if (usuario != null) {
				String tipoDoArquivo = request.getParameter("tipoArquivo");

				String contentType = "";
				byte[] fileBytes = null;

				if (tipoDoArquivo.equalsIgnoreCase("imagem")) {
					contentType = usuario.getContentType();
					fileBytes = new Base64().decodeBase64(usuario.getFotoBase64());
				} else if (tipoDoArquivo.equalsIgnoreCase("curriculo")) {
					contentType = usuario.getContentTypeCurriculo();
					fileBytes = new Base64().decodeBase64(usuario.getCurriculoBase64());
				}

				response.setHeader("Content-Disposition", "attachment;filename=arquivo." + contentType.split("\\/")[1]);

				// Converte a base64 da imagem do banco para byte[]
				// Coloca os bytes em um objeto de entrada para processar
				InputStream inputStream = new ByteArrayInputStream(fileBytes);

				// Inicio da resposta para o navegador
				int read = 0;
				byte[] bytes = new byte[1024];
				OutputStream outputStream = response.getOutputStream();

				while ((read = inputStream.read()) != -1) {
					outputStream.write(bytes, 0, read);
				}
				outputStream.flush();
				outputStream.close();
				inputStream.close();
			}
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuarios.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		}
	}

	@SuppressWarnings("static-access")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {

			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuarios.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);

		} else {

			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");
			String telefone = request.getParameter("telefone");
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("estado");
			String bairro = request.getParameter("bairro");
			String ibge = request.getParameter("ibge");
			String sexo = request.getParameter("sexo");
			String perfil = request.getParameter("perfil");

			BeanUsuario usuario = new BeanUsuario();

			usuario.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNome(nome);
			usuario.setTelefone(telefone);

			usuario.setCep(cep);
			usuario.setRua(rua);
			usuario.setCidade(cidade);
			usuario.setEstado(estado);
			usuario.setBairro(bairro);
			usuario.setIbge(ibge);
			usuario.setSexo(sexo);
			usuario.setPerfil(perfil);

			String ativo = request.getParameter("ativo");

			if (ativo != null && ativo.equalsIgnoreCase("on")) {
				usuario.setAtivo(true);
			} else {
				usuario.setAtivo(false);
			}

			try {

				if (ServletFileUpload.isMultipartContent(request)) {

					Part imagemFoto = request.getPart("foto");

					if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {

						byte[] bytesImagem = converteStremParaByte(imagemFoto.getInputStream());

						String fotoBase64 = new Base64().encodeBase64String(bytesImagem);

						usuario.setFotoBase64(fotoBase64);
						usuario.setContentType(imagemFoto.getContentType());

						// Compactando a imagem em uma miniatura
						// Transformar em um BufferedImage
//						byte[] imagemByteDecode = new Base64().decodeBase64(fotoBase64);
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytesImagem));

						// Pega o tipo da Imagem
						int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

						// Cria imagem em miniatura
						BufferedImage resizedImage = new BufferedImage(100, 100, type);
						Graphics2D g = resizedImage.createGraphics();
						g.drawImage(bufferedImage, 0, 0, 100, 100, null);
						g.dispose();

						// Escrever a imagem novamente
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resizedImage, "png", baos);

						String miniaturaBase64 = "data:image/png;base64,"
								+ DatatypeConverter.printBase64Binary(baos.toByteArray());

						usuario.setFotoBase64Miniatura(miniaturaBase64);

					} else {
						usuario.setAtualizarImagem(false);
					}

					Part curriculoPdf = request.getPart("curriculo");

					if (curriculoPdf != null && curriculoPdf.getInputStream().available() > 0) {
						String curriculoBase64 = new Base64()
								.encodeBase64String(converteStremParaByte(curriculoPdf.getInputStream()));

						usuario.setCurriculoBase64(curriculoBase64);
						usuario.setContentTypeCurriculo(curriculoPdf.getContentType());
					} else {
						usuario.setAtualizarPdf(false);
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			boolean podeInserir = false;

			if (login != null && !login.isEmpty()) {
				podeInserir = validacao(request, daoUsuario, id, login, senha);
			}

			if (id == null || id.isEmpty() && podeInserir) {
				daoUsuario.salvar(usuario);
			} else if (id != null && !id.isEmpty() && podeInserir) {
				daoUsuario.atualizar(usuario);
			}

			if (!podeInserir) {
				request.setAttribute("user", usuario);
			}

			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuarios.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		}
	}

	/**
	 * Faz a validação de Login e Senha Retorna false se o login ou a senha for
	 * igual a algum do banco de dados e retorna false se ele passar direto
	 * 
	 * @param request
	 * @param daoUsuario
	 * @param id
	 * @param login
	 * @param senha
	 * @return {@link Boolean}
	 */
	private static boolean validacao(HttpServletRequest request, DaoUsuario daoUsuario, String id, String login,
			String senha) {

		if (id == null || id.isEmpty() && !daoUsuario.validarLogin(login) && daoUsuario.validarSenha(senha)) {
			request.setAttribute("msg", "Usuário Inválido!");
			return false;
		} else if (id == null || id.isEmpty() && daoUsuario.validarLogin(login) && !daoUsuario.validarSenha(senha)) {
			request.setAttribute("msg", "Senha Inválida!");
			return false;
		} else if (id == null || id.isEmpty() && !daoUsuario.validarLogin(login) && !daoUsuario.validarSenha(senha)) {
			request.setAttribute("msg", "Usuário e senha Inválidos!");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * converte a entrada de fluxo de dados da imagem para um array de bytes
	 * 
	 * @param imagem
	 * @return {@link Array}
	 * @throws IOException
	 */
	private byte[] converteStremParaByte(InputStream imagem) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();
		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}
		return baos.toByteArray();
	}
}
