package br.com.sgpa.bean;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgpa.business.DocumentoBusiness;
import br.com.sgpa.business.PagamentoBusiness;
import br.com.sgpa.business.PessoaBusiness;
import br.com.sgpa.business.PessoaNotificacaoBusiness;
import br.com.sgpa.business.ContratoBusiness;
import br.com.sgpa.business.ProcessoBusiness;
import br.com.sgpa.business.TipoPessoaBusiness;

public abstract class Base {

	@Autowired
	protected TipoPessoaBusiness tipoPessoaBusiness;
	@Autowired
	protected PessoaBusiness pessoaBusiness;
	@Autowired
	protected ContratoBusiness contratoBusiness;
	@Autowired
	protected PessoaNotificacaoBusiness pessoaNotificacaoBusiness;
	@Autowired
	protected ProcessoBusiness processoBusiness;
	@Autowired
	protected DocumentoBusiness documentoBusiness;
	@Autowired
	protected PagamentoBusiness pagamentoBusiness;
	
	private String tituloRelatorio;

	public String getTituloRelatorio() {
		return tituloRelatorio;
	}

	public void setTituloRelatorio(String tituloRelatorio) {
		this.tituloRelatorio = tituloRelatorio;
	}

}