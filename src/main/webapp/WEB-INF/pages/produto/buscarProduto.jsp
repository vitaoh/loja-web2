<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="edu.ifsp.loja.controllers.produto.BuscarProdutoForm" %>
<%@ page import="edu.ifsp.loja.controllers.produto.ProdutoDTO" %>
<%@ page import="edu.ifsp.loja.util.StringUtil" %>
<%@ page import="java.util.List" %>

<%
BuscarProdutoForm form = (BuscarProdutoForm) request.getAttribute("form");
int currentPage  = form.getPage();
int pageSize     = form.getPageSize();
int totalRecords = request.getAttribute("totalRecords") != null
        ? (Integer) request.getAttribute("totalRecords") : 0;
int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

List<ProdutoDTO> produtos = (List<ProdutoDTO>) request.getAttribute("produtos");

String sort  = request.getParameter("sort") != null ? request.getParameter("sort") : "";
String order = request.getParameter("order") != null ? request.getParameter("order") : "asc";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Buscar Produtos</title>
<style>
.pagination { display: flex; justify-content: center; gap: 5px; margin-top: 10px; }
.pagination a { padding: 4px 8px; border: 1px solid #ccc; border-radius: 4px; text-decoration: none; }
.pagination a.current { font-weight: bold; background: #eee; }
.pagination a.disabled { pointer-events: none; opacity: 0.4; }
th.sortable { cursor: pointer; user-select: none; }
th.sortable:hover { background: #f0f0f0; }
</style>
<script>
function gotoPage(page) {
    const form = document.querySelector('#search-form');
    form.querySelector('input[name="page"]').value = page;
    form.submit();
}

function movePage(offset) {
    const current = <%= currentPage %>;
    const total   = <%= totalPages %>;
    const next    = current + offset;
    if (next < 1 || next > total) return;
    gotoPage(next);
}

let priceSortAsc = <%= "asc".equals(order) ? "true" : "false" %>;

function sortByPrice() {
    const tbody = document.querySelector('#results-table tbody');
    const rows = Array.from(tbody.querySelectorAll('tr'));

    rows.sort((a, b) => {
        const priceA = parseFloat(a.cells[2].textContent.replace(',', '.')) || 0;
        const priceB = parseFloat(b.cells[2].textContent.replace(',', '.')) || 0;
        return priceSortAsc ? priceA - priceB : priceB - priceA;
    });

    rows.forEach(row => tbody.appendChild(row));

    const th = document.getElementById('th-preco');
    th.textContent = 'Preço ' + (priceSortAsc ? '▲' : '▼');

    priceSortAsc = !priceSortAsc;

    const form = document.querySelector('#search-form');
    form.querySelector('input[name="sort"]').value = "preco";
    form.querySelector('input[name="order"]').value = priceSortAsc ? "asc" : "desc";
}
</script>
</head>
<body>

<h1>Buscar produtos</h1>

<form id="search-form" method="get" action="<%= request.getContextPath() %>/produto/buscar"
      onsubmit="document.querySelector('input[name=page]').value=1;">
    <label>Descrição:</label>
    <input type="text" name="descricao" value="<%= StringUtil.emptyIfNull(form.getDescricao()) %>"><br>

    <label>Preço Mínimo:</label>
    <input type="number" name="precoMinimo" min="0" value="<%= form.getPrecoMinimo() %>"><br>

    <label>Preço Máximo:</label>
    <input type="number" name="precoMaximo" value="<%= form.getPrecoMaximo() %>"><br>

    <label>Registros por página:</label>
    <select name="pageSize" onchange="document.querySelector('input[name=page]').value=1; this.form.submit();">
        <% for (int opt : new int[]{5,10,20,50}) { %>
            <option value="<%= opt %>" <%= opt == pageSize ? "selected" : "" %>><%= opt %></option>
        <% } %>
    </select><br>

    <input type="hidden" name="page" value="<%= currentPage %>">
    <input type="hidden" name="sort" value="<%= sort %>">
    <input type="hidden" name="order" value="<%= order %>">

    <button type="submit">Buscar</button>
</form>

<% if (produtos != null && !produtos.isEmpty()) { %>

<p> Página <strong><%= currentPage %></strong> de <strong><%= totalPages %></strong> —
    <strong><%= totalRecords %></strong> registros encontrados </p>

<table id="results-table" border="1" width="100%">
<thead>
<tr>
    <th>ID</th>
    <th>Descrição</th>
    <th id="th-preco" class="sortable" onclick="sortByPrice()">Preço ▲▼</th>
</tr>
</thead>
<tbody>
<% for (ProdutoDTO p : produtos) { %>
<tr>
    <td><%= p.id() %></td>
    <td><%= p.descricao() %></td>
    <td><%= p.preco() %></td>
</tr>
<% } %>
</tbody>
</table>

<nav class="pagination">
    <a href="#" class="<%= currentPage==1 ? "disabled" : "" %>" onclick="gotoPage(1)">⏮ Primeira</a>
    <a href="#" class="<%= currentPage==1 ? "disabled" : "" %>" onclick="movePage(-1)">◀ Anterior</a>

    <% for (int i = 1; i <= totalPages; i++) { %>
        <a href="#" class="<%= i==currentPage ? "current" : "" %>" onclick="gotoPage(<%= i %>)"><%= i %></a>
    <% } %>

    <a href="#" class="<%= currentPage==totalPages ? "disabled" : "" %>" onclick="movePage(1)">Próxima ▶</a>
    <a href="#" class="<%= currentPage==totalPages ? "disabled" : "" %>" onclick="gotoPage(<%= totalPages %>)">Última ⏭</a>
</nav>

<% } else { %>
<p>Nenhum resultado encontrado para os critérios informados.</p>
<% } %>

</body>
</html>