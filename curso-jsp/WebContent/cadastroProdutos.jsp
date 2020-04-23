<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de produtos</title>
<link rel="stylesheet" href="resources/css/cadastro.css">
<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"
	type="text/javascript"></script>
<script src="javaScript/jquery.maskMoney.js" type="text/javascript"></script>
</head>
<body>
	<a href="acessoliberado.jsp"><img alt="voltar" title="voltar"
		src="resources/img/voltar.jfif" width="30px" height="30px"></a>
	<a href="index.jsp"><img alt="sair" title="sair"
		src="resources/img/sair.png" width="30px" height="30px"></a>
	<div align="center">
		<h1>Cadastro de Produtos</h1>
		<h3 style="color: red;">${msg}</h3>
	</div>
	<form action="salvarProduto" method="post" id="formProduto"
		onsubmit="return validarCampos();">
		<ul class="form-style-1" style="text-align: center;">
			<li>
				<table>
					<tr>
						<td><input type="text" readonly="readonly" id="id" name="id"
							style="width: 185px; text-align: center;"
							value="${valorProduto.id}" class="field-long" placeholder="Id"></td>
					</tr>
					<tr>
						<td><input type="text" id="nome" name="nome"
							style="text-align: center;" value="${valorProduto.nome}"
							class="field-long" maxlength="55" placeholder="Nome do Produto"></td>
					</tr>
					<tr>
						<td><input type="number" id="quantidade" name="quantidade"
							style="text-align: center;" placeholder="Quantidade de Produtos"
							value="${valorProduto.quantidade}" class="field-long"
							maxlength="5"></td>
					</tr>
					<tr>
						<td><input type="text" id="valor" name="valor"
							data-thousands="." data-decimal="," data-precision="1"
							style="text-align: center;" placeholder="Valor do produto"
							value="${valorProduto.valorEmTexto}" class="field-long" maxlength="10"></td>
					</tr>
					<tr>
						<td><select id="categorias" name="categoria_id" style="width: 185px;">
							<c:forEach items="${categorias}" var="categoria">
								<option value="${categoria.id}" id="${categoria.id}" 
									<c:if test="${categoria.id == valorProduto.categoria_id}">
										<c:out value="selected=selected"/>
									</c:if>								
								>
									${categoria.nome}
								</option>
							</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td><input type="submit" value="Salvar" style="width: 90px">
							<input type="submit" value="Cancelar" style="width: 90px"
							onclick="document.getElementById('formProduto').action = 'salvarProduto?acao=reset'">
						</td>
					</tr>
				</table>
			</li>
		</ul>
	</form>
	<div class="cantainer">
		<table class="responsive-table">
			<caption>Lista de Produtos</caption>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Valor</th>
				<th>Quantidade</th>
				<th>Delete</th>
				<th>Editar</th>
			</tr>
			<c:forEach items="${produtos}" var="valorProduto">
				<tr>
					<td><c:out value="${valorProduto.id}"></c:out></td>
					<td><c:out value="${valorProduto.nome}"></c:out></td>
					<td><fmt:formatNumber type="number" maxFractionDigits="2" value="${valorProduto.valor}"/></td>
					<td><c:out value="${valorProduto.quantidade}"></c:out></td>
					<td><a
						href="salvarProduto?acao=delete&valorProduto=${valorProduto.id}" onclick="return confirm('Deseja deletar esse Usuario');"><img
							alt="Excluir" title="Exclui" src="resources/img/excluir.jpg"
							width="25px" height="25px"></a></td>
					<td><a
						href="salvarProduto?acao=editar&valorProduto=${valorProduto.id}"><img
							alt="Editar" title="Editar" src="resources/img/editar.png"
							width="25px" height="25px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<script>
		$(function() {
			$('#valor').maskMoney();
		})
	</script>
</body>
</html>