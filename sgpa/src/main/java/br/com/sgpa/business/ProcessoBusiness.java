package br.com.sgpa.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgpa.dao.ProcessoDao;
import br.com.sgpa.entity.Processo;
import br.com.sgpa.utils.exception.DaoException;
import br.com.sgpa.utils.exception.ServiceException;

@Service("processoBusiness")
public class ProcessoBusiness {

	@Autowired
	private ProcessoDao processoDao;

	@Transactional(value = "transactionManager")
	public List<Processo> listaProcessoPorTipoPessoa(Processo processo, boolean relatorio) throws ServiceException {
		List<Processo> listaProcessos = new ArrayList<Processo>();
		listaProcessos = processoDao.listaProcessoPorPessoa(processo, relatorio);
		return listaProcessos;
	}

	@Transactional(value = "transactionManager")
	public Processo pesquisarPorId(int idProcesso) throws ServiceException {
		Processo processo = new Processo();
		processo = processoDao.pesquisarPorId(idProcesso);
		// validaRetorno(tipoProcesso);
		return processo;
	}

	public void validaRetorno(Object obj) throws ServiceException {
		if (obj == null) {
			throw new ServiceException("Processo(s) não encontrado(s)");
		}

	}

	@Transactional(value = "transactionManager", rollbackFor = { Exception.class, DaoException.class })
	public Processo salvar(Processo processo) throws DaoException, ServiceException {
		// validaProcesso(processo);
		if (processo.getDtInicio() == null) {
			processo.setDtInicio(new Date());
		}
		processoDao.persistirEntidade(processo);
		return processo;
	}

	@Transactional(value = "transactionManager")
	public Processo atualizar(Processo processo) throws DaoException, ServiceException {
		if (processo.getId() == null) {
			throw new ServiceException("Processo não pode ser alterado!");
		}
		// validaProcesso(processo);
		processoDao.updateEntidade(processo);
		return processo;
	}
	
	@Transactional(value = "transactionManager")
	public void excluir(Processo processo) throws ServiceException, DaoException {
		processoDao.excluirEntidade(processo);
	}
	
	public void validarExclusao(Processo processo) throws ServiceException{
//		String msgExclusao =  "Processo "  + processo.getNumeroProcesso()+ " não pode ser excluído.";
//		if (processo.getListaSubAreas() == null){
//			throw new ServiceException(msgExclusao);
//		}
//		if (!(areaEntity.getListaSubAreas().isEmpty())) {
//			throw new ServiceException(msgExclusao);
//		}
				
	}
	
	@Transactional(value = "transactionManager")
	public List<Processo> getListaProcessos() throws ServiceException {
		List<Processo> listaProcesso = new ArrayList<Processo>();
		listaProcesso = processoDao.getAll();
		return listaProcesso;
	}

	public ProcessoDao getProcessoDao() {
		return processoDao;
	}

	public void setProcessoDao(ProcessoDao processoDao) {
		this.processoDao = processoDao;
	}

}
