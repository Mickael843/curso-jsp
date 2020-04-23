<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Menu</title>
</head>
<body>
	<a href="index.jsp"><img alt="sair" title="sair"
		src="resources/img/sair.png" width="40px" height="40px"></a>
	<div align="center" style="padding-top: 10%">
		<h2>Bem vindo ao sistema!</h2>
		<br />
		<table>
			<tr>
				<td style="padding: 0px 20px 0px 15px"><a
					href="salvarUsuario?acao=listartodos"><img
						alt="Cadastro de Usuarios" title="Cadastro de Usuarios"
						src="resources/img/usuario.png" width="100px" height="100px"></a></td>
				<td style="padding: 0px 20px 0px 15px"><a
					href="salvarProduto?acao=listartodos"><img
						alt="Cadastro de Produtos" title="Cadastro de Produtos"
						src="resources/img/produto.jpg" width="100px" height="100px"></a></td>
			</tr>
			<tr>
				<td style="text-align: center">Cad. Usuario</td>
				<td style="text-align: center">Cad. Produto</td>
			</tr>
		</table>

	</div>
</body>
</html>