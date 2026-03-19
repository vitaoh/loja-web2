package edu.ifsp.loja.service;

import java.util.ArrayList;
import java.util.List;

import edu.ifsp.loja.controllers.produto.ProdutoDTO;
import edu.ifsp.loja.controllers.produto.buscar.BuscarProdutoDTO;
import edu.ifsp.loja.persistencia.ProdutoDAO;

public class ProdutoService {
	private ProdutoDAO dao = new ProdutoDAO();
	
	public List<ProdutoDTO> buscar(BuscarProdutoDTO searchDTO) {
		List<ProdutoDTO> produtos = new ArrayList<>();
		
		if ((searchDTO.descricao() != null && !searchDTO.descricao().isBlank()) || searchDTO.preco() >= 0) {
			produtos = dao.find(searchDTO.descricao(), searchDTO.preco()).stream()
					.map(p -> new ProdutoDTO(p.getId(), p.getDescricao(), p.getPreco()))
					.toList();
		}
		
		return produtos;
	}
}
