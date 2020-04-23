<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Telefones</title>
<link rel="stylesheet" href="resources/css/cadastro.css">

</head>
<body>
	<a href="acessoliberado.jsp"><img alt="voltar" title="voltar"
		src="resources/img/voltar.jfif" width="30px" height="30px"></a>
	<a href="index.jsp"><img alt="sair" title="sair"
		src="resources/img/sair.png" width="30px" height="30px"></a>
	<div align="center">
		<h1>Cadastro de Telefones</h1>
		<h3 style="color: red;">${msg}</h3>
	</div>

	<form action="salvarTelefones" method="post" id="formUser"
		onsubmit="return validarCampos();" style="width: 75%;">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Usuário:</td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							class="field-long" value="${user.id}"></td>
						<td><input type="text" readonly="readonly" id="nome"
							name="nome" class="field-long" value="${user.nome}"></td>
					</tr>
					<tr>
						<td>Número:</td>
						<td><input type="text" id="numero" name="numero"
							class="field-long" placeholder="Informe um número"></td>
						<td><select id="tipo" name="tipo" style="width: 173px;">
								<option>Casa</option>
								<option>Fixo</option>
								<option>Celular</option>
						</select></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar" style="width: 173px;"></td>
						<td><input type="submit" value="Voltar" style="width: 173px;"
							onclick="document.getElementById('formUser').action = 'salvarTelefones?acao=voltar'"></td>
					</tr>
				</table>
			</li>
		</ul>
	</form>
	<div class="cantainer">
		<table class="responsive-table">
			<caption>Usuários cadastrados</caption>
			<tr>
				<th>Id</th>
				<th>Número</th>
				<th>Tipo</th>
				<th>Excluir</th>
			</tr>
			<c:forEach items="${telefones}" var="fone">
				<tr>
					<td><c:out value="${fone.id}"></c:out></td>
					<td><c:out value="${fone.numero}"></c:out></td>
					<td><c:out value="${fone.tipo}"></c:out></td>
					<td><a href="salvarTelefones?acao=delete&fone=${fone.id}"
						onclick="return confirm('Deseja deletar esse Usuario');"><img
							alt="Excluir" title="Excluir" src="resources/img/excluir.jpg"
							width="25px" height="25px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script type="text/javascript">
		function validarCampos() {
			if (document.getElementById("numero").value == '') {
				alert('Informe um número para o cadastro!');
				return false;
			} else if (document.getElementById("numero").value == '') {
				alert('Informe o tipo do número!');
				return false;
			} 
	</script>
</body>
</html>