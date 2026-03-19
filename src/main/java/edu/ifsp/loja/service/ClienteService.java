package edu.ifsp.loja.service;

import java.util.ArrayList;
import java.util.List;

import edu.ifsp.loja.controllers.cliente.ClienteDTO;
import edu.ifsp.loja.controllers.cliente.buscar.BuscarClienteDTO;
import edu.ifsp.loja.persistencia.ClienteDAO;

public class ClienteService {
	private ClienteDAO dao = new ClienteDAO();
	
	public List<ClienteDTO> buscar(BuscarClienteDTO searchDTO) {
		List<ClienteDTO> clientes = new ArrayList<>();
		
		if (searchDTO.nome() != null && !searchDTO.nome().isBlank()) {
			clientes = dao.find(searchDTO.nome(), searchDTO.ativo()).stream()
					.map(c -> new ClienteDTO(c.getId(), c.getNome(), c.getEmail(), c.isAtivo()))
					.toList();
		}
		
		return clientes;
	}
}
