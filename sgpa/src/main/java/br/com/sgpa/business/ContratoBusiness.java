package br.com.sgpa.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgpa.dao.ContratoDao;
import br.com.sgpa.entity.Contrato;
import br.com.sgpa.utils.exception.DaoException;
import br.com.sgpa.utils.exception.ServiceException;

@Service("contratoBusiness")
public class ContratoBusiness {

	@Autowired
	private ContratoDao contratoDao;

	@Transactional(value = "transactionManager")
	public List<Contrato> listaContratoPorParametros(Contrato contrato) throws ServiceException {
		List<Contrato> listaContratos = new ArrayList<Contrato>();
		listaContratos = contratoDao.listaContratoPorParametros(contrato);
		return listaContratos;
	}

	@Transactional(value = "transactionManager")
	public Contrato pesquisarPorId(int idContrato) throws ServiceException {
		Contrato contrato = new Contrato();
		contrato = contratoDao.pesquisarPorId(idContrato);
		return contrato;
	}

	public void validaRetorno(Object obj) throws ServiceException {
		if (obj == null) {
			throw new ServiceException("Contrato(s) não encontrado(s)");
		}

	}

	@Transactional(value = "transactionManager", rollbackFor = { Exception.class, DaoException.class })
	public Contrato salvar(Contrato contrato) throws DaoException, ServiceException {
		// validaContrato(contrato);
		contratoDao.persistirEntidade(contrato);
		return contrato;
	}

	@Transactional(value = "transactionManager")
	public Contrato atualizar(Contrato contrato) throws DaoException, ServiceException {
		if (contrato.getId() == null) {
			throw new ServiceException("Contrato não pode ser alterado!");
		}
		// validaContrato(contrato);
		contratoDao.updateEntidade(contrato);
		return contrato;
	}

	@Transactional(value = "transactionManager")
	public void excluir(Contrato contrato) throws ServiceException, DaoException {
		contratoDao.excluirEntidade(contrato);
	}

	public void validarExclusao(Contrato contrato) throws ServiceException {
		// String msgExclusao = "Contrato " + contrato.getNumeroContrato()+ " não pode
		// ser excluído.";
		// if (contrato.getListaSubAreas() == null){
		// throw new ServiceException(msgExclusao);
		// }
		// if (!(areaEntity.getListaSubAreas().isEmpty())) {
		// throw new ServiceException(msgExclusao);
		// }

	}

	public ContratoDao getContratoDao() {
		return contratoDao;
	}

	public void setContratoDao(ContratoDao contratoDao) {
		this.contratoDao = contratoDao;
	}

}
