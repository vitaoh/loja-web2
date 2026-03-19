package edu.ifsp.loja.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import edu.ifsp.loja.modelo.Cliente;
import edu.ifsp.loja.persistencia.ClienteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cliente")
public class RecuperarClienteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		String paramId = request.getParameter("id");
		if ((paramId != null) && !paramId.isBlank()) {
			int id = Integer.parseInt(paramId);
			ClienteDAO dao = new ClienteDAO();
			Cliente cliente = dao.findById(id);
			
			out.println("Cliente: " + cliente.getNome());
			out.println("E-mail: " + cliente.getEmail());
		} else {
			out.println("Informe o id do cliente na URL.");
			out.println("Exemplo:");
			out.println("http://localhost:8080/loja-web2/app/cliente?id=1");
		}
		
	}
}
