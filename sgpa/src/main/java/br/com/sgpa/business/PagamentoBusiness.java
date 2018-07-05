package br.com.sgpa.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgpa.dao.PagamentoDao;
import br.com.sgpa.entity.Pagamento;
import br.com.sgpa.utils.exception.DaoException;
import br.com.sgpa.utils.exception.ServiceException;

@Service("pagamentoBusiness")
public class PagamentoBusiness {

	@Autowired
	private PagamentoDao pagamentoDao;

	@Transactional(value = "transactionManager")
	public List<Pagamento> listaPagamentoPorTipoPessoa(Pagamento pagamento) throws ServiceException {
		List<Pagamento> listaPagamentos = new ArrayList<Pagamento>();
		listaPagamentos = pagamentoDao.listaPagamentoPorPessoa(pagamento);
		return listaPagamentos;
	}

	@Transactional(value = "transactionManager")
	public Pagamento pesquisarPorId(int idPagamento) throws ServiceException {
		Pagamento pagamento = new Pagamento();
		pagamento = pagamentoDao.pesquisarPorId(idPagamento);
		// validaRetorno(tipoPagamento);
		return pagamento;
	}

	public void validaRetorno(Object obj) throws ServiceException {
		if (obj == null) {
			throw new ServiceException("Pagamento(s) não encontrado(s)");
		}

	}

	@Transactional(value = "transactionManager", rollbackFor = { Exception.class, DaoException.class })
	public Pagamento salvar(Pagamento pagamento) throws DaoException, ServiceException {
		// validaPagamento(pagamento);
		pagamentoDao.persistirEntidade(pagamento);
		return pagamento;
	}

	@Transactional(value = "transactionManager")
	public Pagamento atualizar(Pagamento pagamento) throws DaoException, ServiceException {
		if (pagamento.getId() == null) {
			throw new ServiceException("Pagamento não pode ser alterado!");
		}
		// validaPagamento(pagamento);
		pagamentoDao.updateEntidade(pagamento);
		return pagamento;
	}
	
	@Transactional(value = "transactionManager")
	public void excluir(Pagamento pagamento) throws ServiceException, DaoException {
		pagamentoDao.excluirEntidade(pagamento);
	}
	
	public void validarExclusao(Pagamento pagamento) throws ServiceException{
//		String msgExclusao =  "Pagamento "  + pagamento.getNumeroPagamento()+ " não pode ser excluído.";
//		if (pagamento.getListaSubAreas() == null){
//			throw new ServiceException(msgExclusao);
//		}
//		if (!(areaEntity.getListaSubAreas().isEmpty())) {
//			throw new ServiceException(msgExclusao);
//		}
				
	}
	
	@Transactional(value = "transactionManager")
	public List<Pagamento> getListaPagamentos(boolean advogados) throws ServiceException {
		List<Pagamento> listaPagamento = new ArrayList<Pagamento>();
		listaPagamento = pagamentoDao.listaPagamentos(advogados);
		return listaPagamento;
	}

	public PagamentoDao getPagamentoDao() {
		return pagamentoDao;
	}

	public void setPagamentoDao(PagamentoDao pagamentoDao) {
		this.pagamentoDao = pagamentoDao;
	}

}
