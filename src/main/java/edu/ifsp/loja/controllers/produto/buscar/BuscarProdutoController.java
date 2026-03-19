package edu.ifsp.loja.controllers.produto.buscar;

import java.io.IOException;
import java.util.List;

import edu.ifsp.loja.controllers.produto.ProdutoDTO;
import edu.ifsp.loja.service.ProdutoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/produto/buscar")
public class BuscarProdutoController  extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BuscarProdutoController.doGet()");
		request.setAttribute("dto", new BuscarProdutoDTO("", 0));
		request.getRequestDispatcher("/app/produto/buscarProduto.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String precoSt = request.getParameter("preco");
		String descricao = request.getParameter("descricao");
		double preco = Double.parseDouble(precoSt);
		
		ProdutoService service = new ProdutoService();
		BuscarProdutoDTO searchDTO = new BuscarProdutoDTO(descricao, preco);
		List<ProdutoDTO> produtos = service.buscar(searchDTO);
		
		request.setAttribute("produtos", produtos);
		request.setAttribute("dto", searchDTO);
		request.getRequestDispatcher("/app/produto/buscarProduto.jsp").forward(request, response);
	}
	
}
