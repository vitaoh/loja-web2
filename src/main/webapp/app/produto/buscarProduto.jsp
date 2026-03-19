<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="edu.ifsp.loja.controllers.produto.buscar.BuscarProdutoDTO" %>
<%@ page import="edu.ifsp.loja.controllers.produto.ProdutoDTO" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Loja Web2</title>
</head>
<body>
	<%
	BuscarProdutoDTO dto = (BuscarProdutoDTO)request.getAttribute("dto");	
	%>

	<h1>Buscar produtos</h1>
	<form method="post" action="/loja-web2/produto/buscar">
		<label for="descricao">Descricao: </label>
		<input type="text" name="descricao" id="descricao" value="<%= dto.descricao() %>">
		<br>
		
		<label for="preco">Preco > </label>
		<input type="number" name="preco" id="preco" value="<%= dto.preco() %>" min="0">
		
		<button type="submit">Buscar</button>
	</form>
	
	<%
	if (request.getAttribute("produtos") != null) {
		List<ProdutoDTO> produtos = (List<ProdutoDTO>)request.getAttribute("produtos");
	%>
	<br>
	<table border="1">
		<tr>
			<th>ID</th>
			<th>Descricao</th>
			<th>Preco</th>
		</tr>
		
		<% for (ProdutoDTO p : produtos) { %>
		<tr>
			<td><%= p.id() %></td>
			<td><%= p.descricao() %></td>
			<td><%= p.preco() %></td>
		</tr>
		<% } %>
		
	</table>
	<%
	} else {
	%>
	<p>Nenhum produto encontrado.</p>
	<%
	}
	%>
	
</body>
</html>