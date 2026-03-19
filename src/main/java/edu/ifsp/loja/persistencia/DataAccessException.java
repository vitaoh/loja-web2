package edu.ifsp.loja.persistencia;

public class DataAccessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataAccessException(Throwable t) {
		super(t);
	}

}
