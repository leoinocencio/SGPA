package br.com.sgpa.bean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.sgpa.entity.Documento;
import br.com.sgpa.entity.Pagamento;
import br.com.sgpa.entity.Pessoa;
import br.com.sgpa.entity.Processo;
import br.com.sgpa.utils.MensagensUtil;
import br.com.sgpa.utils.Utils;
import br.com.sgpa.utils.exception.DaoException;
import br.com.sgpa.utils.exception.ServiceException;

@ManagedBean(name = "cadastraPagamentoBean")
@Component
@Scope(value = "session")
public class CadastraPagamentoBean extends Base {

	private List<Documento> listaDocumentos;
	private Pagamento pagamento;
	private Pagamento pagamentoFiltro;
	private List<Pessoa> listaCliente;
	private List<Pagamento> listaPagamentos;
	private List<Processo> listaProcessos;
	private Pessoa usuarioSessao = (Pessoa) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
			.get("usuario");
	private boolean cliente;
	private UploadedFile file;
	private boolean alterarArquivo;
	private Pessoa clienteFiltro;

	public String view() {
		pagamento = new Pagamento();
		pagamento.setDocumento(new Documento());
		carregaListas(true);
		return "/pages/cadastro/cadastrarPagamento.xhtml?faces-redirect=true";
	}

	public String consulta() {
		pagamentoFiltro = new Pagamento();
		clienteFiltro = new Pessoa();
		carregaListas(false);
		listaPagamentos = new ArrayList<Pagamento>();
		try {
			listaPagamentos = pagamentoBusiness.listaPagamentoPorTipoPessoa(pagamentoFiltro);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "/pages/consulta/consultaPagamento.xhtml?faces-redirect=true";
	}

	private void carregaListas(boolean cadastro) {
		listaCliente = new ArrayList<Pessoa>();
		listaDocumentos = new ArrayList<Documento>();
		listaProcessos = new ArrayList<Processo>();
		Processo processoFiltro = new Processo();
		try {
			if ("CLIENTE".equals(usuarioSessao.getTipoPessoa().getDescricao())) {
				listaCliente.add(usuarioSessao);
				if (!cadastro)
					getPagamentoFiltro().setCliente(listaCliente.get(0));
				processoFiltro.setCliente(new Pessoa());
				processoFiltro.setCliente(usuarioSessao);
				setCliente(true);
			} else {
				listaCliente = pessoaBusiness.listaPessoas("cliente");
			}
			listaProcessos = processoBusiness.listaProcessoPorTipoPessoa(processoFiltro, false);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (isAlterarArquivo() || (getFile() != null && getFile().getSize() > 0)) {
				if (!isAlterarArquivo()) {
					Documento documento = new Documento();
					InputStream stream = getFile().getInputstream();
					byte[] dados = new byte[(int) getFile().getSize()];
					stream.read(dados, 0, (int) getFile().getSize());
					stream.close();
					documento.setArquivoDocumento(dados);
					documento.setNome(getFile().getFileName().toLowerCase());
					documento.setTipoDocumento(documento.getNome().substring(documento.getNome().lastIndexOf("."),
							documento.getNome().length()));
					if (pagamento.getDocumento() != null && pagamento.getDocumento().getId() != null) {
						pagamento.getDocumento().setArquivoDocumento(documento.getArquivoDocumento());
						pagamento.getDocumento().setNome(documento.getNome());
						pagamento.getDocumento().setTipoDocumento(documento.getTipoDocumento());
					} else {
						pagamento.setDocumento(documento);
					}
					if (pagamento.getDocumento() != null && pagamento.getDocumento().getId() == null) {
						documentoBusiness.salvar(pagamento.getDocumento());
					} else {
						documentoBusiness.atualizar(pagamento.getDocumento());
					}
				}
				if (pagamento.getId() == null) {
					pagamentoBusiness.salvar(pagamento);
					MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG11"));
				} else {
					pagamentoBusiness.atualizar(pagamento);
					MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG12"));
				}
				sucesso();
			} else {
				MensagensUtil.addMensagemErro("Adicione um comprovante");
			}
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
			view();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public void carregarArquivo(FileUploadEvent event) {
		setAlterarArquivo(false);
	}

	public String editar(Pagamento pagamento) {
		setPagamento(pagamento);
		setAlterarArquivo(true);
		return "/pages/cadastro/cadastrarPagamento.xhtml?faces-redirect=true";
	}

	public void editarArquivo() {
		setAlterarArquivo(false);
	}

	public StreamedContent fazerDownload(Pagamento pagamento) {
		InputStream arquivo = new ByteArrayInputStream(pagamento.getDocumento().getArquivoDocumento());
		StreamedContent file = new DefaultStreamedContent(arquivo, pagamento.getDocumento().getTipoDocumento(),
				pagamento.getDocumento().getNome());
		return file;
	}

	public void excluir(Pagamento pagamento) {
		try {
			pagamentoBusiness.excluir(pagamento);
			MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG13"));
			consultar();
		} catch (Exception e) {
			MensagensUtil.addMensagemErro("Houve um erro ao tentar excluir.");
		}
	}

	public String consultar() {
		listaPagamentos = new ArrayList<Pagamento>();
		try {
			listaPagamentos = pagamentoBusiness.listaPagamentoPorTipoPessoa(pagamentoFiltro);
			if (listaPagamentos == null || listaPagamentos.isEmpty()) {
				MensagensUtil.addMensagemErro(Utils.getPropriedadeMessage("erro.nenhumRegistro"));
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "/pages/consulta/consultaPagamento.xhtml?faces-redirect=true";
	}

	public List<Documento> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<Documento> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public Pessoa getUsuarioSessao() {
		return usuarioSessao;
	}

	public void setUsuarioSessao(Pessoa usuarioSessao) {
		this.usuarioSessao = usuarioSessao;
	}


	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<Pessoa> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Pessoa> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public Pagamento getPagamentoFiltro() {
		return pagamentoFiltro;
	}

	public void setPagamentoFiltro(Pagamento pagamentoFiltro) {
		this.pagamentoFiltro = pagamentoFiltro;
	}

	public List<Pagamento> getListaPagamentos() {
		return listaPagamentos;
	}

	public void setListaPagamentos(List<Pagamento> listaPagamentos) {
		this.listaPagamentos = listaPagamentos;
	}

	public boolean isAlterarArquivo() {
		return alterarArquivo;
	}

	public void setAlterarArquivo(boolean alterarArquivo) {
		this.alterarArquivo = alterarArquivo;
	}

	public boolean isCliente() {
		return cliente;
	}

	public void setCliente(boolean cliente) {
		this.cliente = cliente;
	}

	public List<Processo> getListaProcessos() {
		return listaProcessos;
	}

	public void setListaProcessos(List<Processo> listaProcessos) {
		this.listaProcessos = listaProcessos;
	}

	public Pessoa getClienteFiltro() {
		return clienteFiltro;
	}

	public void setClienteFiltro(Pessoa clienteFiltro) {
		this.clienteFiltro = clienteFiltro;
	}

}
