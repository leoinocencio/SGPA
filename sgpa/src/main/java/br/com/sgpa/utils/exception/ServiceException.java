package br.com.sgpa.utils.exception;

import java.util.List;

public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3241697634400690730L;

	private List<String> erros;

	public ServiceException(String mensagem) {
		super(mensagem);
	}

	public ServiceException() {
	}

	public ServiceException(Throwable throwable) {
		super(throwable);
	}

	public ServiceException(String mensagem, Throwable throwable) {
		super(mensagem, throwable);
	}

	public ServiceException(List<String> erros) {
		this.erros = erros;
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}

}
