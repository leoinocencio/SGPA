package br.com.sgpa.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgpa.dao.DocumentoDao;
import br.com.sgpa.entity.Documento;
import br.com.sgpa.utils.exception.DaoException;
import br.com.sgpa.utils.exception.ServiceException;

@Service("documentoProcessoBusiness")
public class DocumentoBusiness {

	@Autowired
	private DocumentoDao documentoProcessoDao;

	@Transactional(value = "transactionManager")
	public List<Documento> getListaDocumentoProcesso() throws ServiceException {
		List<Documento> listaDocumentoProcesso = new ArrayList<Documento>();
		listaDocumentoProcesso = documentoProcessoDao.getAll();
		validaRetorno(listaDocumentoProcesso);
		return listaDocumentoProcesso;
	}

	@Transactional(value = "transactionManager")
	public Documento pesquisarPorId(int idDocumentoProcesso) throws ServiceException {
		Documento documentoProcesso = new Documento();
		documentoProcesso = documentoProcessoDao.pesquisarPorId(idDocumentoProcesso);
		validaRetorno(documentoProcesso);
		return documentoProcesso;
	}

	public void validaRetorno(Object obj) throws ServiceException {
		if (obj == null) {
			throw new ServiceException("Documento não encontrado");
		}

	}

	@Transactional(value = "transactionManager", rollbackFor = { Exception.class, DaoException.class })
	public Documento salvar(Documento documentoProcesso) throws DaoException, ServiceException {
		documentoProcessoDao.persistirEntidade(documentoProcesso);
		return documentoProcesso;
	}

	@Transactional(value = "transactionManager")
	public Documento atualizar(Documento documentoProcesso) throws DaoException, ServiceException {
		if (documentoProcesso.getId() == null) {
			throw new ServiceException("Documento não pode ser alterado!");
		}
		documentoProcessoDao.updateEntidade(documentoProcesso);
		return documentoProcesso;
	}

	public DocumentoDao getDocumentoProcessoDao() {
		return documentoProcessoDao;
	}

	public void setDocumentoProcessoDao(DocumentoDao documentoProcessoDao) {
		this.documentoProcessoDao = documentoProcessoDao;
	}

}
