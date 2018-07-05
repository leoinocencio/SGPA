package br.com.sgpa.login;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.WordUtils;
import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.sgpa.bean.Base;
import br.com.sgpa.entity.Pessoa;
import br.com.sgpa.entity.TipoPessoa;
import br.com.sgpa.utils.MensagensUtil;
import br.com.sgpa.utils.Utils;
import br.com.sgpa.utils.exception.DaoException;
import br.com.sgpa.utils.exception.ServiceException;

@Controller("usuarioLoginBean")
@Scope(value = "session")
public class UsuarioLoginBean extends Base {

	private String username;

	private String password;

	private HttpSession session;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private void init() {
		carregaListas();
		cpf = null;
		pessoa = new Pessoa();
		pessoa.setTipoPessoa(new TipoPessoa());
	}

	public String login() {
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = null;
		boolean loggedIn = false;
		if (username != null && password != null) {
			try {
				pessoa = pessoaBusiness.pesquisarLogin(username.toUpperCase(), criptografa(password));
				if (pessoa != null) {
					loggedIn = true;
				} else {
					loggedIn = false;
					message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário e/ou senha inválido(s).", "");
					FacesContext.getCurrentInstance().addMessage(null, message);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		} else {
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário e/ou senha inválido(s).", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		context.addCallbackParam("loggedIn", loggedIn);
		if (loggedIn) {
			session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("acesso", true);
			session.setAttribute("pessoaLogada", pessoa);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", pessoa);
			return "/pages/principal.xhtml?faces-redirect=true";
		}
		return "";
	}

	public String cadastrar() {
		init();
		return "/public/cadastro/cadastrarPessoa.xhtml?faces-redirect=true";
	}

	public String sair() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			session = (HttpSession) facesContext.getExternalContext().getSession(false);
			session.invalidate();
		} catch (Exception e) {
			System.out.println("Erro ao fazer logout");
			e.printStackTrace();
		}
		return "/pages/login.xhtml?faces-redirect=true";
	}

	public String getNomeSessao() {
		String nomeUsuario = "";
		if (username.equals("leonardo")) {
			nomeUsuario = "Leonardo Vascaíno";
		} else {
			nomeUsuario = username;
		}
		return WordUtils.capitalizeFully(nomeUsuario);
	}

	private Pessoa pessoa;
	private String cpf;
	private boolean flagAdvogado;

	private List<TipoPessoa> listaTipoPessoa;

	/**
	 * Função que prepara as variáveis antes de enviar para o business
	 */
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
				MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG01"));
			} else {
				pessoaBusiness.atualizar(pessoa);
				MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG02"));
			}
			sucesso();
			init();
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

	public void salvar() {
		System.out.println(pessoa);
	}
}