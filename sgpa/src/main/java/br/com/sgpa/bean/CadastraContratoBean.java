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

import br.com.sgpa.entity.Contrato;
import br.com.sgpa.entity.Documento;
import br.com.sgpa.entity.Pessoa;
import br.com.sgpa.entity.Processo;
import br.com.sgpa.utils.MensagensUtil;
import br.com.sgpa.utils.Utils;
import br.com.sgpa.utils.exception.DaoException;
import br.com.sgpa.utils.exception.ServiceException;

@ManagedBean(name = "cadastraContratoBean")
@Component
@Scope(value = "session")
public class CadastraContratoBean extends Base {

	private List<Documento> listaDocumentos;
	private Contrato contrato;
	private Contrato contratoFiltro;
	private List<Pessoa> listaAdvogado;
	private List<Pessoa> listaCliente;
	private List<Processo> listaProcesso;
	private List<Contrato> listaContratos;
	private Pessoa usuarioSessao = (Pessoa) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
			.get("usuario");
	private boolean advogado;
	private UploadedFile file;
	private boolean alterarArquivo;

	public String view() {
		contrato = new Contrato();
		contrato.setDocumento(new Documento());
		contrato.setProcesso(new Processo());
		carregaListas(true);
		return "/pages/cadastro/cadastrarContrato.xhtml?faces-redirect=true";
	}

	public String consulta() {
		contratoFiltro = new Contrato();
		contratoFiltro.setProcesso(new Processo());
		carregaListas(false);
		listaContratos = new ArrayList<Contrato>();
		try {
			listaContratos = contratoBusiness.listaContratoPorParametros(contratoFiltro);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "/pages/consulta/consultaContrato.xhtml?faces-redirect=true";
	}

	private void carregaListas(boolean cadastro) {
		listaAdvogado = new ArrayList<Pessoa>();
		listaCliente = new ArrayList<Pessoa>();
		listaDocumentos = new ArrayList<Documento>();
		listaProcesso = new ArrayList<Processo>();
		try {
			if ("ADVOGADO".equals(usuarioSessao.getTipoPessoa().getDescricao())) {
				listaAdvogado.add(pessoaBusiness.pesquisarPorId(usuarioSessao.getId()));
				if (cadastro) {
					contrato.getProcesso().setAdvogado(new Pessoa());
					contrato.getProcesso().setAdvogado(listaAdvogado.get(0));
				} else {
					contratoFiltro.getProcesso().setAdvogado(new Pessoa());
					contratoFiltro.getProcesso().setAdvogado(listaAdvogado.get(0));
				}
				Processo processoFiltro = new Processo();
				processoFiltro.setAdvogado(new Pessoa());
				processoFiltro.setAdvogado(listaAdvogado.get(0));
				listaProcesso = processoBusiness.listaProcessoPorTipoPessoa(processoFiltro, false);
				setAdvogado(true);
			} else if ("CLIENTE".equals(usuarioSessao.getTipoPessoa().getDescricao())) {
				listaCliente.add(pessoaBusiness.pesquisarPorId(usuarioSessao.getId()));
				if (cadastro) {
					contrato.getProcesso().setCliente(new Pessoa());
					contrato.getProcesso().setCliente(listaCliente.get(0));
				} else {
					contratoFiltro.getProcesso().setCliente(new Pessoa());
					contratoFiltro.getProcesso().setCliente(listaCliente.get(0));
				}
				Processo processoFiltro = new Processo();
				processoFiltro.setCliente(new Pessoa());
				processoFiltro.setCliente(listaCliente.get(0));
				listaProcesso = processoBusiness.listaProcessoPorTipoPessoa(processoFiltro, false);
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
					documento.setArquivoDocumento(dados);
					documento.setNome(getFile().getFileName().toLowerCase());
					documento.setTipoDocumento(documento.getNome().substring(documento.getNome().lastIndexOf("."),
							documento.getNome().length()));
					if (contrato.getDocumento() != null && contrato.getDocumento().getId() != null) {
						contrato.getDocumento().setArquivoDocumento(documento.getArquivoDocumento());
						contrato.getDocumento().setNome(documento.getNome());
						contrato.getDocumento().setTipoDocumento(documento.getTipoDocumento());
					} else {
						contrato.setDocumento(documento);
					}
					if (contrato.getDocumento() != null && contrato.getDocumento().getId() == null) {
						documentoBusiness.salvar(contrato.getDocumento());
					} else {
						documentoBusiness.atualizar(contrato.getDocumento());
					}
				}
				if (contrato.getId() == null) {
					contratoBusiness.salvar(contrato);
					MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG35"));
				} else {
					contratoBusiness.atualizar(contrato);
					MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG36"));
				}
				sucesso();
			} else {
				MensagensUtil.addMensagemErro("Adicione um contrato");
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

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public void carregarArquivo(FileUploadEvent event) {
		setAlterarArquivo(false);
	}

	public String editar(Contrato contrato) {
		setContrato(contrato);
		setAlterarArquivo(true);
		return "/pages/cadastro/cadastrarContrato.xhtml?faces-redirect=true";
	}

	public void editarArquivo() {
		setAlterarArquivo(false);
	}

	public StreamedContent fazerDownload(Contrato contrato) {
		InputStream arquivo = new ByteArrayInputStream(contrato.getDocumento().getArquivoDocumento());
		StreamedContent file = new DefaultStreamedContent(arquivo, contrato.getDocumento().getTipoDocumento(),
				contrato.getDocumento().getNome());
		return file;
	}

	public void excluir(Contrato contrato) {
		try {
			contratoBusiness.excluir(contrato);
			MensagensUtil.addMensagemSucesso(Utils.getPropriedadeMessage("MSG41"));
			consultar();
		} catch (Exception e) {
			MensagensUtil.addMensagemErro("Houve um erro ao tentar excluir.");
		}
	}

	public String consultar() {
		listaContratos = new ArrayList<Contrato>();
		try {
			listaContratos = contratoBusiness.listaContratoPorParametros(contratoFiltro);
			if (listaContratos == null || listaContratos.isEmpty()) {
				MensagensUtil.addMensagemErro(Utils.getPropriedadeMessage("erro.nenhumRegistro"));
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "/pages/consulta/consultaContrato.xhtml?faces-redirect=true";
	}

	public void carregarCliente() {
		if (contrato != null && contrato.getProcesso() != null && contrato.getProcesso().getId() != null) {
			if (listaCliente.isEmpty() || (listaCliente != null && listaCliente.size() < 1)) {
				listaCliente.add(contrato.getProcesso().getCliente());
			}
			contrato.getProcesso().setCliente(contrato.getProcesso().getCliente());
		}
	}

	public void carregarClienteConsulta() {
		if (contratoFiltro != null && contratoFiltro.getProcesso() != null
				&& contratoFiltro.getProcesso().getId() != null) {
			if (listaCliente.isEmpty() || (listaCliente != null && listaCliente.size() < 1)) {
				listaCliente.add(contratoFiltro.getProcesso().getCliente());
			}
			contratoFiltro.getProcesso().setCliente(contratoFiltro.getProcesso().getCliente());
		}
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

	public Contrato getContratoFiltro() {
		return contratoFiltro;
	}

	public void setContratoFiltro(Contrato contratoFiltro) {
		this.contratoFiltro = contratoFiltro;
	}

	public List<Contrato> getListaContratos() {
		return listaContratos;
	}

	public void setListaContratos(List<Contrato> listaContratos) {
		this.listaContratos = listaContratos;
	}

	public boolean isAlterarArquivo() {
		return alterarArquivo;
	}

	public void setAlterarArquivo(boolean alterarArquivo) {
		this.alterarArquivo = alterarArquivo;
	}

	public List<Processo> getListaProcesso() {
		return listaProcesso;
	}

	public void setListaProcesso(List<Processo> listaProcesso) {
		this.listaProcesso = listaProcesso;
	}

}
