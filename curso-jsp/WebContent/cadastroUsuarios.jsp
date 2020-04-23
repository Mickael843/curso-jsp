<%@page import="br.com.beans.BeanUsuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de usuário</title>
<link rel="stylesheet" href="resources/css/cadastro.css">

<!-- Adicionando JQuery -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
	crossorigin="anonymous" type="text/javascript"></script>

</head>
<body>
	<a href="acessoliberado.jsp"><img alt="voltar" title="voltar"
		src="resources/img/voltar.jfif" width="30px" height="30px"></a>
	<a href="index.jsp"><img alt="sair" title="sair"
		src="resources/img/sair.png" width="30px" height="30px"></a>
	<div align="center">
		<h1>Cadastro de Usuários</h1>
		<h3 style="color: red;">${msg}</h3>
	</div>

	<form action="salvarUsuario" method="post" id="formUser"
		enctype="multipart/form-data" onsubmit="return validarCampos();" style="width: 85%">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td><input type="text" readonly="readonly" id="id" name="id"
							value="${user.id}" class="field-long" style="width: 173px;"
							placeholder="Id"></td>
						<td><input type="text" id="cep" name="cep"
							onblur="consultaCep();" value="${user.cep}"
							placeholder="Informe um cep" maxlength="9"></td>
					</tr>
					<tr>
						<td><input type="text" id="login" name="login"
							value="${user.login}" placeholder="Login" maxlength="12"></td>
						<td><input type="text" id="estado" name="estado"
							value="${user.estado}" placeholder="Estado"></td>
					</tr>
					<tr>
						<td><input type="password" id="senha" name="senha"
							value="${user.senha}" placeholder="Informe a senha"
							maxlength="12"></td>
						<td><input type="text" id="rua" name="rua"
							value="${user.rua}" placeholder="Rua"></td>
					</tr>
					<tr>
						<td><input type="text" id="nome" name="nome"
							value="${user.nome}" placeholder="Nome"></td>
						<td><input type="text" id="bairro" name="bairro"
							value="${user.bairro}" placeholder="Bairro"></td>
					</tr>
					<tr>
						<td><input type="text" id="telefone" name="telefone"
							value="${user.telefone}" placeholder="Telefone"></td>
						<td><input type="text" id="cidade" name="cidade"
							value="${user.cidade}" placeholder="Cidade"></td>
					</tr>
					<tr>
						<td><input type="text" id="ibge" name="ibge"
							value="${user.ibge}" placeholder="IBGE"></td>
						<td><input type="file" id="foto" name="foto"
							placeholder="Foto"></td>
					</tr>
					<tr>
					<td>
						<select id="perfil" name="perfil" style="width: 173px;">
							<option value="nao_informado">[----SELECTION----]</option>
							<option value="administrador">Administrador</option>
							<option value="secretario">Secretario(a)</option>
							<option value="gerente">Gerente</option>
							<option value="funcionario">Funcionario</option>
						</select>
					</td>
						<td><input type="file" id="curriculo" name="curriculo"
							placeholder="Curriculo"></td>
					</tr>
					<tr>
						<td>Ativo:<input type="checkbox" id="ativo" name="ativo"
							<%
								if(request.getAttribute("user") != null) {
									BeanUsuario usuario = (BeanUsuario) request.getAttribute("user");
									if(usuario.isAtivo()) {
										out.print(" ");
										out.print("checked=\"checked\"");
										out.print(" ");
									}
								}
							%>
						></td>
						<td>Sexo:
							<input type="radio" name="sexo"
								<%
									if(request.getAttribute("user") != null) {
										BeanUsuario usuario = (BeanUsuario) request.getAttribute("user");
										if(usuario.getSexo().equalsIgnoreCase("masculino")) {
											out.print(" ");
											out.print("checked=\"checked\"");
											out.print(" ");
										}
									} 
								%>								
							 value="masculino">Masculino</input>
							<input type="radio" name="sexo" 
								<%
									if(request.getAttribute("user") != null) {
										BeanUsuario usuario = (BeanUsuario) request.getAttribute("user");
										if(usuario.getSexo().equalsIgnoreCase("feminino")) {
											out.print(" ");
											out.print("checked=\"checked\"");
											out.print(" ");
										}
									}
								%>
							value="feminino">Feminino</input></td>
					</tr>
					<tr>
						<td><input type="submit" value="Salvar"> <input
							type="submit" value="Cancelar"
							onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'">
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>
	
	<form action="pesquisar" method="post" style="width: 85%">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td><input type="text" id="descricaoConsulta" name="descricaoConsulta" style="width: 250px" placeholder="Pesquisar"></td>
						<td><input type="submit" value="Pesquisar"></td>
					</tr>
				</table>	
			</li>
		</ul>
	</form>
	
	<div class="cantainer">
		<table class="responsive-table">
			<caption>Usuários cadastrados</caption>
			<tr>
				<th>Foto</th>
				<th>Id</th>
				<th>Nome</th>
				<th>telefone</th>
				<th>Curriculo</th>
				<th>Delete</th>
				<th>Editar</th>
				<th>Telefones</th>
			</tr>
			<c:forEach items="${usuarios}" var="user">
				<tr>
					<c:if
						test="${!user.fotoBase64Miniatura.isEmpty() && user.fotoBase64Miniatura != null}">
						<td><a
							href="salvarUsuario?acao=baixar&tipoArquivo=imagem&user=${user.id}"><img
								src='<c:out value="${user.fotoBase64Miniatura}"/>'
								alt="Imagem User" title="Imagem User" width="32px" height="32px"></a></td>
					</c:if>
					<c:if
						test="${user.fotoBase64Miniatura.isEmpty() || user.fotoBase64Miniatura == null}">
						<td><img src="resources/img/perfil.jfif" alt="Imagem"
							title="Imagem" width="32px" height="32px"
							onclick="alert('Não possui imagem')"></td>
					</c:if>
					<td><c:out value="${user.id}"></c:out></td>
					<td><c:out value="${user.nome}"></c:out></td>
					<td><c:out value="${user.telefone}"></c:out></td>
					<c:if
						test="${!user.curriculoBase64.isEmpty() && user.curriculoBase64 != null}">
						<td><a
							href="salvarUsuario?acao=baixar&tipoArquivo=curriculo&user=${user.id}"><img
								alt="curriculo" title="curriculo" src="resources/img/cv.png"
								width="25px" height="25px"></a></td>
					</c:if>
					<c:if
						test="${user.curriculoBase64.isEmpty() || user.curriculoBase64 == null}">
						<td><img alt="curriculo" title="curriculo"
							src="resources/img/cv.png" width="25px" height="25px"
							onclick="alert('Não possui curriculo')"></td>
					</c:if>
					<td><a href="salvarUsuario?acao=delete&user=${user.id}"
						onclick="return confirm('Deseja deletar esse Usuario');"><img
							alt="Excluir" title="Exclui" src="resources/img/excluir.jpg"
							width="25px" height="25px"></a></td>
					<td><a href="salvarUsuario?acao=editar&user=${user.id}"><img
							alt="Editar" title="Editar" src="resources/img/editar.png"
							width="25px" height="25px"></a></td>
					<td><a
						href="salvarTelefones?acao=listarTelefones&user=${user.id}"><img
							alt="Telefones" title="telefones"
							src="resources/img/telefone1.png" width="25px" height="25px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("login").value == '') {
				alert('Informe o Login');
				return false;
			} else if (document.getElementById("senha").value == '') {
				alert('Informe o senha');
				return false;
			} else if (document.getElementById("telefone").value == '') {
				alert('Informe o telefone');
				return false;
			} else if (document.getElementById("nome").value == '') {
				alert('Informe o nome');
				return false;
			}
			return true;
		}

		function consultaCep() {
			var cep = $("#cep").val();

			//Consulta o webservice viacep.com.br/
			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {

						if (!("erro" in dados)) {

							$("#rua").val(dados.logradouro);
							$("#bairro").val(dados.bairro);
							$("#cidade").val(dados.localidade);
							$("#estado").val(dados.uf);
							$("#ibge").val(dados.ibge);

						} else {

							$("#cep").val('');
							$("#rua").val('');
							$("#bairro").val('');
							$("#cidade").val('');
							$("#estado").val('');
							$("#ibge").val('');
							alert("CEP não encontrado.");

						}
					});
		}
	</script>
</body>
</html>