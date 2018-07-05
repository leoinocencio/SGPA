package br.com.sgpa.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.sgpa.entity.Pessoa;
import br.com.sgpa.entity.PessoaNotificacao;
import br.com.sgpa.entity.Processo;
import br.com.sgpa.utils.MensagensUtil;
import br.com.sgpa.utils.Utils;
import br.com.sgpa.utils.exception.DaoException;
import br.com.sgpa.utils.exception.ServiceException;

@ManagedBean(name = "cadastraNotificacaoBean")
@Component
@Scope(value = "session")
public class CadastraNotificacaoBean extends Base {

	private List<Processo> listaPessoaProcesso;
	private List<Pessoa> listaProcessoPessoa;
	private List<PessoaNotificacao> listaPessoaNotificacao;
	private Pessoa usuarioSessao = (Pessoa) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
			.get("usuario");
	private PessoaNotificacao pessoaNotificacao;
	private PessoaNotificacao pessoaNotificacaoFiltro;
	private boolean bloqueiaCombo = true;
	private int processoSelecionado;
	private boolean enviada = true;

	private void carregaListas(boolean cadastro) {
		listaPessoaProcesso = new ArrayList<Processo>();
		listaProcessoPessoa = new ArrayList<Pessoa>();
		listaPessoaNotificacao = new ArrayList<PessoaNotificacao>();
		Processo processoFiltro = new Processo();
		try {
			if ("ADVOGADO".equals(usuarioSessao.getTipoPessoa().getDescricao())) {
				processoFiltro.setAdvogado(new Pessoa());
				processoFiltro.getAdvogado().setId(usuarioSessao.getId());
			} else {
				processoFiltro.setCliente(new Pessoa());
				processoFiltro.getCliente().setId(usuarioSessao.getId());
			}
			listaPessoaProcesso = processoBusiness.listaProcessoPorTipoPessoa(processoFiltro, false);
			listaPessoaNotificacao = pessoaNotificacaoBusiness.getListaNotificacaoRecebida(usuarioSessao.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void carregarPessoas() throws ServiceException {
		if ("ADVOGADO".equals(usuarioSessao.getTipoPessoa().getDescricao())) {
			if (listaProcessoPessoa.isEmpty()) {
				listaProcessoPessoa.add(pessoaNotificacao.getProcesso().getCliente());
			}
		} else {
			if (listaProcessoPessoa.isEmpty()) {
				listaProcessoPessoa.add(pessoaNotificacao.getProcesso().getAdvogado());
			}
		}
		setBloqueiaCombo(false);
	}
	
	public void carregarPessoasFiltro() throws ServiceException {
		if ("ADVOGADO".equals(usuarioSessao.getTipoPessoa().getDescricao())) {
			if (listaProcessoPessoa.isEmpty()) {
				listaProcessoPessoa.add(pessoaNotificacaoFiltro.getProcesso().getCliente());
			}
		} else {
			if (listaProcessoPessoa.isEmpty()) {
				listaProcessoPessoa.add(pessoaNotificacaoFiltro.getProcesso().getAdvogado());
			}
		}
		setBloqueiaCombo(false);
	}

	/**
	 * Funcao que inicializa a tela
	 * 
	 * @return url da pagina
	 */
	public String view() {
		carregaListas(true);
		pessoaNotificacao = new PessoaNotificacao();
		pessoaNotificacao.setProcesso(new Processo());
		pessoaNotificacao.setRemetente(new Pessoa());
		pessoaNotificacao.setReceptor(new Pessoa());
		return "/pages/cadastro/cadastrarNotificacao.xhtml?faces-redirect=true";
	}
	
	public String consultaEnviadas() {
		pessoaNotificacaoFiltro = new PessoaNotificacao();
		carregaListas(false);
		pessoaNotificacaoFiltro.setRemetente(new Pessoa());
		pessoaNotificacaoFiltro.setRemetente(usuarioSessao);
		try {
			listaPessoaNotificacao = pessoaNotificacaoBusiness.listaNotificacao(pessoaNotificacaoFiltro);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		setEnviada(true);
		return "/pages/consulta/consultaNotificacao.xhtml?faces-redirect=true";
	}
	
	public String consultaRecebidas() {
		pessoaNotificacaoFiltro = new PessoaNotificacao();
		carregaListas(false);
		pessoaNotificacaoFiltro.setReceptor(new Pessoa());
		pessoaNotificacaoFiltro.setReceptor(usuarioSessao);
		try {
			listaPessoaNotificacao = pessoaNotificacaoBusiness.listaNotificacao(pessoaNotificacaoFiltro);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		setEnviada(false);
		return "/pages/consulta/consultaNotificacao.xhtml?faces-redirect=true";
	}

	public void salvar() {
		try {
			pessoaNotificacao.setRemetente(usuarioSessao);
//			if ("ADVOGADO".equals(usuarioSessao.getTipoPessoa().getDescricao())) {
//				pessoaNotificacao.setReceptor(pessoaNotificacao.);
//			} else {
//				pessoaNotificacao.setReceptor();
//			}
			if (pessoaNotificacao.getId() == null) {
				pessoaNotificacaoBusiness.salvar(pessoaNotificacao);
				MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG21"));
			} else {
				pessoaNotificacaoBusiness.atualizar(pessoaNotificacao);
				MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG22"));
			}
			sucesso();
			view();
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
	
	public String consultar() {
		listaPessoaNotificacao = new ArrayList<PessoaNotificacao>();
		if (enviada) {
			pessoaNotificacaoFiltro.setRemetente(new Pessoa());
			pessoaNotificacaoFiltro.setRemetente(usuarioSessao);
		} else {
			pessoaNotificacaoFiltro.setReceptor(new Pessoa());
			pessoaNotificacaoFiltro.setReceptor(usuarioSessao);
		}
		try {
			listaPessoaNotificacao = pessoaNotificacaoBusiness.listaNotificacao(pessoaNotificacaoFiltro);
			if (listaPessoaNotificacao == null || listaPessoaNotificacao.isEmpty()) {
				MensagensUtil.addMensagemErro(Utils.getPropriedadeMessage("erro.nenhumRegistro"));
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "/pages/consulta/consultaNotificacao.xhtml?faces-redirect=true";
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
	
	public String editar(PessoaNotificacao pessoaNotificacao) {
		carregaListas(true);
		try {
			setPessoaNotificacao(pessoaNotificacao);
			carregarPessoas();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/pages/cadastro/cadastrarNotificacao.xhtml?faces-redirect=true";
	}
	
//	public void excluir(PessoaNotificacao pessoaNotificacao) {
//		try {
//			pessoaNotificacaoBusiness.excluir(pessoaNotificacao);
//			MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG31"));
//		} catch (Exception e) {
//			MensagensUtil.addMensagemErro("Houve um erro ao tentar excluir.");
//		}
//	}

	public List<PessoaNotificacao> getListaPessoaNotificacao() {
		return listaPessoaNotificacao;
	}

	public void setListaPessoaNotificacao(List<PessoaNotificacao> listaPessoaNotificacao) {
		this.listaPessoaNotificacao = listaPessoaNotificacao;
	}

	public Pessoa getUsuarioSessao() {
		return usuarioSessao;
	}

	public void setUsuarioSessao(Pessoa usuarioSessao) {
		this.usuarioSessao = usuarioSessao;
	}

	public PessoaNotificacao getPessoaNotificacao() {
		return pessoaNotificacao;
	}

	public void setPessoaNotificacao(PessoaNotificacao pessoaNotificacao) {
		this.pessoaNotificacao = pessoaNotificacao;
	}

	public boolean isBloqueiaCombo() {
		return bloqueiaCombo;
	}

	public void setBloqueiaCombo(boolean bloqueiaCombo) {
		this.bloqueiaCombo = bloqueiaCombo;
	}

	public int getProcessoSelecionado() {
		return processoSelecionado;
	}

	public void setProcessoSelecionado(int processoSelecionado) {
		this.processoSelecionado = processoSelecionado;
	}

	public List<Processo> getListaPessoaProcesso() {
		return listaPessoaProcesso;
	}

	public void setListaPessoaProcesso(List<Processo> listaPessoaProcesso) {
		this.listaPessoaProcesso = listaPessoaProcesso;
	}

	public List<Pessoa> getListaProcessoPessoa() {
		return listaProcessoPessoa;
	}

	public void setListaProcessoPessoa(List<Pessoa> listaProcessoPessoa) {
		this.listaProcessoPessoa = listaProcessoPessoa;
	}

	public PessoaNotificacao getPessoaNotificacaoFiltro() {
		return pessoaNotificacaoFiltro;
	}

	public void setPessoaNotificacaoFiltro(PessoaNotificacao pessoaNotificacaoFiltro) {
		this.pessoaNotificacaoFiltro = pessoaNotificacaoFiltro;
	}

	public boolean isEnviada() {
		return enviada;
	}

	public void setEnviada(boolean enviada) {
		this.enviada = enviada;
	}

}
