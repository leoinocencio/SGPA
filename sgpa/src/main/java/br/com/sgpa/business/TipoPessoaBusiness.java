package br.com.sgpa.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgpa.dao.TipoPessoaDao;
import br.com.sgpa.entity.TipoPessoa;
import br.com.sgpa.utils.exception.ServiceException;

@Service("tipoPessoaBusiness")
public class TipoPessoaBusiness {

	@Autowired
	private TipoPessoaDao tipoPessoaDao;

	@Transactional(value = "transactionManager")
	public List<TipoPessoa> getListaTipoPessoa() throws ServiceException {
		List<TipoPessoa> listaTipoPessoa = new ArrayList<TipoPessoa>();
		listaTipoPessoa = tipoPessoaDao.getAll();
		validaRetorno(listaTipoPessoa);
		return listaTipoPessoa;
	}

	@Transactional(value = "transactionManager")
	public TipoPessoa pesquisarPorId(int idTipoPessoa) throws ServiceException {
		TipoPessoa tipoPessoa = new TipoPessoa();
		tipoPessoa = tipoPessoaDao.pesquisarPorId(idTipoPessoa);
		validaRetorno(tipoPessoa);
		return tipoPessoa;
	}

	public void validaRetorno(Object obj) throws ServiceException {
		if (obj == null) {
			throw new ServiceException("Tipo(s) Pessoa não encontrado(s)");
		}

	}

	public TipoPessoaDao getTipoPessoaDao() {
		return tipoPessoaDao;
	}

	public void setTipoPessoaDao(TipoPessoaDao tipoPessoaDao) {
		this.tipoPessoaDao = tipoPessoaDao;
	}

}
