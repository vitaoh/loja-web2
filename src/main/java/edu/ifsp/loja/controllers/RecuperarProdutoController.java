package edu.ifsp.loja.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import edu.ifsp.loja.modelo.Produto;
import edu.ifsp.loja.persistencia.ProdutoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/produto")
public class RecuperarProdutoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		String paramId = request.getParameter("id");
		if ((paramId != null) && !paramId.isBlank()) {
			int id = Integer.parseInt(paramId);
			ProdutoDAO dao = new ProdutoDAO();
			Produto produto = dao.findById(id);
			
			out.println("Descricao: " + produto.getDescricao());
			out.println("Preço: " + produto.getPreco());
		} else {
			out.println("Informe o id do produto na URL.");
			out.println("Exemplo:");
			out.println("http://localhost:8080/loja-web2/app/produto?id=1");
		}
		
	}
}
