package edu.ifsp.loja.controllers.cliente.buscar;

import java.io.IOException;
import java.util.List;

import edu.ifsp.loja.controllers.cliente.ClienteDTO;
import edu.ifsp.loja.service.ClienteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cliente/buscar")
public class BuscarClienteController  extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BuscarClienteController.doGet()");
		request.setAttribute("dto", new BuscarClienteDTO("", true));
		request.getRequestDispatcher("/app/cliente/buscarCliente.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String paramAtivo = request.getParameter("ativo");
		boolean ativo = "true".equals(paramAtivo);
		System.out.println("ativo: " + paramAtivo + ", " + ativo);
		
		ClienteService service = new ClienteService();
		BuscarClienteDTO searchDTO = new BuscarClienteDTO(nome, ativo);
		List<ClienteDTO> clientes = service.buscar(searchDTO);
		
		request.setAttribute("clientes", clientes);
		request.setAttribute("dto", searchDTO);
		request.getRequestDispatcher("/app/cliente/buscarCliente.jsp").forward(request, response);
	}
	
}
