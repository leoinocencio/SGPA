package br.com.sgpa.bean;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import br.com.sgpa.entity.Pessoa;
import br.com.sgpa.entity.TipoPessoa;
import br.com.sgpa.utils.MensagensUtil;
import br.com.sgpa.utils.Utils;
import br.com.sgpa.utils.exception.DaoException;
import br.com.sgpa.utils.exception.ServiceException;

@Controller("cadastraPessoaBean")
@Component
@Scope(value = "session")
public class CadastraPessoaBean extends Base {

	private Pessoa pessoa;
	private String cpf;
	private boolean flagAdvogado;
	private boolean proprioRegistro = true;
	private Pessoa usuarioSessao = (Pessoa) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
			.get("usuario");
	private List<TipoPessoa> listaTipoPessoa;
	
	public void prepararCadastro() {
		// CPF;
		if (cpf != null && cpf.length() == 14) {
			cpf = cpf.replaceAll("[^0-9]", "");
		}
		// Nome Pessoa
		if (pessoa.getNome().startsWith(" ") || pessoa.getNome().endsWith(" ")) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Por Favor verifique o excesso de espaços em branco no campo Nome", ""));
			return;
		}
		pessoa.setCpf(cpf);
		try {
			pessoa.setSenha(criptografa(pessoa.getSenha()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String cadastrar() {
		cpf = null;
		pessoa = new Pessoa();
		pessoa.setTipoPessoa(new TipoPessoa());
		carregaListas();
		return "/pages/cadastro/cadastrarPessoa.xhtml?faces-redirect=true";
	}

	public void sucesso() {
		try {
			// mensagem
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Flash flash = facesContext.getExternalContext().getFlash();
			flash.setKeepMessages(true);
			flash.setRedirect(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String editar(Pessoa pessoa) {
		if (usuarioSessao.getId() != pessoa.getId()) {
			setProprioRegistro(false);
		}
		this.pessoa = new Pessoa();
		setPessoa(pessoa);
		cpf = pessoa.getCpf();
		if ("ADVOGADO".equals(getPessoa().getTipoPessoa().getDescricao())) {
			setFlagAdvogado(true);
		}
		carregaListas();
		return "/pages/cadastro/cadastrarPessoa.xhtml?faces-redirect=true";
	}
	
	private void carregaListas() {
		listaTipoPessoa = new ArrayList<TipoPessoa>();
		try {
			listaTipoPessoa = tipoPessoaBusiness.getListaTipoPessoa();
			TipoPessoa administrador = new TipoPessoa();
			for (TipoPessoa tipoPessoa : listaTipoPessoa) {
				if ("ADMINISTRADOR".equals(tipoPessoa.getDescricao().toUpperCase())) {
					administrador = tipoPessoa;
					break;
				}
			}
			if (administrador != null && administrador.getId() != null) {
				listaTipoPessoa.remove(administrador);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public void mostraCampos() {
		if (pessoa.getTipoPessoa() != null && "ADVOGADO".equals(pessoa.getTipoPessoa().getDescricao().toUpperCase())) {
			setFlagAdvogado(true);
		} else {
			setFlagAdvogado(false);
		}
	}

	public void salvarPessoa() {
		prepararCadastro();
		try {
			if (pessoa.getId() == null) {
				pessoa.setSenha(criptografa(pessoa.getSenha()));
				pessoaBusiness.salvar(pessoa);
				MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG43"));
			} else {
				pessoaBusiness.atualizar(pessoa);
				MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG44"));
			}
			sucesso();
			cadastrar();
		} catch (DaoException e) {
			e.printStackTrace();
			MensagensUtil.addMensagemErro(Utils.getPropriedadeMessage("erro.salvar"));
		} catch (ServiceException e) {
			MensagensUtil.addMensagemErroService(e);
		} catch (Exception e) {
			e.printStackTrace();
			MensagensUtil.addMensagemErroInterno(Utils.getPropriedadeMessage("erro.informa.administrador"));
		}
	}

	public String criptografa(String senha) throws UnsupportedEncodingException {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			String senhahex = hexString.toString();
			return senhahex;
		} catch (NoSuchAlgorithmException ns) {
			ns.printStackTrace();
		}
		return senha;
	}

	/*
	 ** Getters and Setters
	 */

	public String getCpf() {
		return cpf;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<TipoPessoa> getListaTipoPessoa() {
		return listaTipoPessoa;
	}

	public void setListaTipoPessoa(List<TipoPessoa> listaTipoPessoa) {
		this.listaTipoPessoa = listaTipoPessoa;
	}

	public boolean isFlagAdvogado() {
		return flagAdvogado;
	}

	public void setFlagAdvogado(boolean flagAdvogado) {
		this.flagAdvogado = flagAdvogado;
	}

	public boolean isProprioRegistro() {
		return proprioRegistro;
	}

	public void setProprioRegistro(boolean proprioRegistro) {
		this.proprioRegistro = proprioRegistro;
	}

	public Pessoa getUsuarioSessao() {
		return usuarioSessao;
	}

	public void setUsuarioSessao(Pessoa usuarioSessao) {
		this.usuarioSessao = usuarioSessao;
	}

}
