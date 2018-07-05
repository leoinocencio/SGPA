package br.com.sgpa.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.com.sgpa.utils.exception.ServiceException;

public class MensagensUtil {
	
	/**
	 * Metodo que apresenta mensagem de aviso na tela
	 * 
	 * @param String view, String msg, String erro
	 * @author carlos.lopes
	 * */
	public static void addMensagemAviso(String view, String msg, String erro){
		RequestContext.getCurrentInstance().execute("rolarBarraTopo()");
		FacesContext.getCurrentInstance().addMessage(view,new FacesMessage(FacesMessage.SEVERITY_WARN, msg, erro));
	}
	
	/**
	 * Metodo que apresenta mensagem de erro na tela
	 * 
	 * @param String view, String msg, String erro
	 * @author carlos.lopes
	 * */	
	public static void addMensagemErro(String view, String msg, String erro){
		RequestContext.getCurrentInstance().execute("rolarBarraTopo()");
		FacesContext.getCurrentInstance().addMessage(view,new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, erro));
	}
	
	/**
	 * Metodo que apresenta mensagem de sucesso na tela
	 * 
	 * @param String view, String msg, String erro
	 * @author carlos.lopes
	 * */		
	public static void addMensagemSucesso(String view, String msg, String erro){
		RequestContext.getCurrentInstance().execute("rolarBarraTopo()");
		FacesContext.getCurrentInstance().addMessage(view, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, erro));
	}
	
	/**
	 * Metodo que apresenta mensagem de erro na tela caso ocorra algum erro inesperado no sistema
	 * 
	 * @param String view, String msg, String erro
	 * @author carlos.lopes
	 * */	
	public static void addMensagemErroInterno(String view, String msg, String erro){
		RequestContext.getCurrentInstance().execute("rolarBarraTopo()");
		FacesContext.getCurrentInstance().addMessage(view,new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, erro));
	}
	
	/**
	 * Metodo que apresenta mensagem de sucesso na tela default
	 * 
	 * @param msg
	 * @author carlos.lopes
	 * */		
	public static void addMensagemSucesso(String msg){
		addMensagemSucesso(null, msg, "");
	}
	
	/**
	 * Metodo que apresenta mensagem de aviso na tela default
	 * 
	 * @param msg
	 * @author carlos.lopes
	 * */
	public static void addMensagemAviso(String msg){
		addMensagemAviso(null, msg, "");
	}
	
	/**
	 * Metodo que apresenta mensagem de erro na tela default
	 * 
	 * @param msg
	 * @author carlos.lopes
	 * */	
	public static void addMensagemErro(String msg){
		addMensagemErro(null, msg, "");
	}
	
	/**
	 * Metodo que apresenta mensagem de erro exception na tela default
	 * 
	 * @param msg
	 * @author carlos.lopes
	 * */	
	public static void addMensagemErroInterno(String msg){
		addMensagemErroInterno(null, msg, "");
	}

	/**
	 * Metodo que apresenta mensagem de erro service na tela default
	 * 
	 * @param ServiceException e
	 * @author carlos.lopes
	 * */
	public static void addMensagemErroService(ServiceException e) {
		if (e != null) {
			if (e.getErros() != null && e.getErros().size() > 0) {
				for (String erro : e.getErros()) {
					addMensagemErro(erro);
				}
			} else {
				addMensagemErro(e.getMessage());
			}
		}
	}

}
