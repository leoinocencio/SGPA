package br.com.sgpa.bean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
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
import br.com.sgpa.entity.Pessoa;
import br.com.sgpa.entity.Processo;
import br.com.sgpa.enums.TipoProcesso;
import br.com.sgpa.utils.MensagensUtil;
import br.com.sgpa.utils.Utils;
import br.com.sgpa.utils.exception.DaoException;
import br.com.sgpa.utils.exception.ServiceException;

@ManagedBean(name = "cadastraProcessoBean")
@Component
@Scope(value = "session")
public class CadastraProcessoBean extends Base {

	private List<Documento> listaDocumentos;
	private Processo processo;
	private Processo processoFiltro;
	private List<Pessoa> listaAdvogado;
	private List<Pessoa> listaCliente;
	private List<Processo> listaProcessos;
	private Pessoa usuarioSessao = (Pessoa) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
			.get("usuario");
	private boolean advogado;
	private UploadedFile file;
	private boolean alterarArquivo;
	
	@SuppressWarnings("unused")
	private List<TipoProcesso> tipos;

	public String view() {
		processo = new Processo();
		processo.setDocumento(new Documento());
		carregaListas(true);
		setAlterarArquivo(false);
		return "/pages/cadastro/cadastrarProcesso.xhtml?faces-redirect=true";
	}

	public String consulta() {
		processoFiltro = new Processo();
		carregaListas(false);
		listaProcessos = new ArrayList<Processo>();
		try {
			listaProcessos = processoBusiness.listaProcessoPorTipoPessoa(processoFiltro, false);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "/pages/consulta/consultaProcesso.xhtml?faces-redirect=true";
	}

	private void carregaListas(boolean cadastro) {
		listaAdvogado = new ArrayList<Pessoa>();
		listaCliente = new ArrayList<Pessoa>();
		listaDocumentos = new ArrayList<Documento>();
		try {
			if ("ADVOGADO".equals(usuarioSessao.getTipoPessoa().getDescricao())) {
				listaAdvogado.add(pessoaBusiness.pesquisarPorId(usuarioSessao.getId()));
				if (cadastro)
					processo.setAdvogado(new Pessoa());
				else
					processoFiltro.setAdvogado(new Pessoa());
				if (!listaAdvogado.isEmpty()) {
					if (cadastro)
						processo.setAdvogado(listaAdvogado.get(0));
					else
						processoFiltro.setAdvogado(listaAdvogado.get(0));
				}
				setAdvogado(true);
				listaCliente = pessoaBusiness.listaPessoas("cliente");
			} else {
				if ("ADMINISTRADOR".equals(usuarioSessao.getTipoPessoa().getDescricao())) {
					listaCliente = pessoaBusiness.listaPessoas("cliente");
				} else {
					listaCliente.add(usuarioSessao);
				}
				if (cadastro) {
					processo.setCliente(listaCliente.get(0));
				}
				else  {
					processoFiltro.setCliente(usuarioSessao);
					processoFiltro.setCliente(new Pessoa());
					processoFiltro.setCliente(usuarioSessao);
				}
				listaAdvogado = pessoaBusiness.listaPessoas("advogado");
				setAdvogado(false);
			}
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
					// byte[] dados = getFile().getInputstream();
					documento.setArquivoDocumento(dados);
					documento.setNome(getFile().getFileName().toLowerCase());
					documento.setTipoDocumento(documento.getNome().substring(documento.getNome().lastIndexOf("."),
							documento.getNome().length()));
					// if (documento.getNome().toLowerCase().contains(".pdf")) {
					// documento.setTipo(TipoDocumento.PDF);
					// } else if (documento.getNome().toLowerCase().contains(".odt")) {
					// documento.setTipo(TipoDocumento.ODT);
					// } else if (documento.getNome().toLowerCase().contains(".doc")) {
					// documento.setTipo(TipoDocumento.DOC);
					// } else if (documento.getNome().toLowerCase().contains(".docx")) {
					// documento.setTipo(TipoDocumento.DOCX);
					// } else if (documento.getNome().toLowerCase().contains(".zip")) {
					// documento.setTipo(TipoDocumento.ZIP);
					// } else if (documento.getNome().toLowerCase().contains(".rar")) {
					// documento.setTipo(TipoDocumento.RAR);
					// } else if (documento.getNome().toLowerCase().contains(".jpeg")) {
					// documento.setTipo(TipoDocumento.JPEG);
					// } else if (documento.getNome().toLowerCase().contains(".jpg")) {
					// documento.setTipo(TipoDocumento.JPG);
					// }
					if (processo.getDocumento() != null && processo.getDocumento().getId() != null) {
						processo.getDocumento().setArquivoDocumento(documento.getArquivoDocumento());
						processo.getDocumento().setNome(documento.getNome());
						processo.getDocumento().setTipoDocumento(documento.getTipoDocumento());
					} else {
						processo.setDocumento(documento);
					}
					if (processo.getDocumento() != null && processo.getDocumento().getId() == null) {
						documentoBusiness.salvar(processo.getDocumento());
					} else {
						documentoBusiness.atualizar(processo.getDocumento());
					}
				}
				if (processo.getId() == null) {
					processoBusiness.salvar(processo);
					MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG16"));
				} else {
					processoBusiness.atualizar(processo);
					MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG17"));
				}
				sucesso();
			} else {
				MensagensUtil.addMensagemErro("Adicione um arquivo do processo digital");
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

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public void carregarArquivo(FileUploadEvent event) {
		setAlterarArquivo(false);
	}

	public String editar(Processo processo) {
		setProcesso(processo);
		setAlterarArquivo(true);
		return "/pages/cadastro/cadastrarProcesso.xhtml?faces-redirect=true";
	}

	public void editarArquivo() {
		setAlterarArquivo(false);
		processo.setDocumento(null);
	}

	public StreamedContent fazerDownload(Processo processo) {
		InputStream arquivo = new ByteArrayInputStream(processo.getDocumento().getArquivoDocumento());
		StreamedContent file = new DefaultStreamedContent(arquivo, processo.getDocumento().getTipoDocumento(),
				processo.getDocumento().getNome());
		return file;
	}

	public void excluir(Processo processo) {
		try {
			processoBusiness.excluir(processo);
			MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG18"));
			consultar();
		} catch (Exception e) {
			MensagensUtil.addMensagemErro("Houve um erro ao tentar excluir.");
		}
	}

	public String consultar() {
		listaProcessos = new ArrayList<Processo>();
		try {
			listaProcessos = processoBusiness.listaProcessoPorTipoPessoa(processoFiltro, false);
			if (listaProcessos == null || listaProcessos.isEmpty()) {
				MensagensUtil.addMensagemErro(Utils.getPropriedadeMessage("erro.nenhumRegistro"));
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "/pages/consulta/consultaProcesso.xhtml?faces-redirect=true";
	}

	public List<Documento> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<Documento> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public List<Pessoa> getListaAdvogado() {
		return listaAdvogado;
	}

	public void setListaAdvogado(List<Pessoa> listaAdvogado) {
		this.listaAdvogado = listaAdvogado;
	}

	public Pessoa getUsuarioSessao() {
		return usuarioSessao;
	}

	public void setUsuarioSessao(Pessoa usuarioSessao) {
		this.usuarioSessao = usuarioSessao;
	}

	public boolean isAdvogado() {
		return advogado;
	}

	public void setAdvogado(boolean advogado) {
		this.advogado = advogado;
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

	public Processo getProcessoFiltro() {
		return processoFiltro;
	}

	public void setProcessoFiltro(Processo processoFiltro) {
		this.processoFiltro = processoFiltro;
	}

	public List<Processo> getListaProcessos() {
		return listaProcessos;
	}

	public void setListaProcessos(List<Processo> listaProcessos) {
		this.listaProcessos = listaProcessos;
	}

	public boolean isAlterarArquivo() {
		return alterarArquivo;
	}

	public void setAlterarArquivo(boolean alterarArquivo) {
		this.alterarArquivo = alterarArquivo;
	}

	public List<TipoProcesso> getTipos() {
		return TipoProcesso.getListaTipoProcesso();
	}

	public void setTipos(List<TipoProcesso> tipos) {
		this.tipos = tipos;
	}
	
	public void finalizaProcesso() {
		try {
			processo.setStProcesso("JULGADO");
			processo.setDtFim(new Date());
			processoBusiness.atualizar(processo);
			MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG46"));
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void finalizaProcessoPerdido() {
		try {
			processo.setStProcesso("PERDIDO");
			processo.setDtFim(new Date());
			processoBusiness.atualizar(processo);
			MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG46"));
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

}
