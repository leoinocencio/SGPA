package br.com.sgpa.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgpa.dao.PessoaNotificacaoDao;
import br.com.sgpa.entity.PessoaNotificacao;
import br.com.sgpa.utils.exception.DaoException;
import br.com.sgpa.utils.exception.ServiceException;

@Service("pessoaNotificacaoBusiness")
public class PessoaNotificacaoBusiness {

	@Autowired
	private PessoaNotificacaoDao pessoaNotificacaoDao;

	@Transactional(value = "transactionManager")
	public List<PessoaNotificacao> listaNotificacao(PessoaNotificacao pessoaNotificacao) throws ServiceException {
		List<PessoaNotificacao> listaPessoaNotificacao = new ArrayList<PessoaNotificacao>();
		listaPessoaNotificacao = pessoaNotificacaoDao.listaNotificacao(pessoaNotificacao);
		return listaPessoaNotificacao;
	}
	
	@Transactional(value = "transactionManager")
	public List<PessoaNotificacao> getListaNotificacaoEnviada(int idPessoaNotificacao) throws ServiceException {
		List<PessoaNotificacao> listaPessoaNotificacao = new ArrayList<PessoaNotificacao>();
		listaPessoaNotificacao = pessoaNotificacaoDao.listaNotificacaoEnviada(idPessoaNotificacao);
		return listaPessoaNotificacao;
	}

	@Transactional(value = "transactionManager")
	public List<PessoaNotificacao> getListaNotificacaoRecebida(int idPessoaNotificacao) throws ServiceException {
		List<PessoaNotificacao> listaPessoaNotificacao = new ArrayList<PessoaNotificacao>();
		listaPessoaNotificacao = pessoaNotificacaoDao.listaNotificacaoRecebida(idPessoaNotificacao);
		return listaPessoaNotificacao;
	}

	@Transactional(value = "transactionManager")
	public PessoaNotificacao pesquisarPorId(int id) throws ServiceException {
		PessoaNotificacao pessoaNotificacao = new PessoaNotificacao();
		pessoaNotificacao = pessoaNotificacaoDao.pesquisarPorId(id);
		return pessoaNotificacao;
	}

	public void validaRetorno(Object obj) throws ServiceException {
		if (obj == null) {
			throw new ServiceException("Notificação(ões) não encontrada(s)");
		}

	}

	@Transactional(value = "transactionManager", rollbackFor = { Exception.class, DaoException.class })
	public PessoaNotificacao salvar(PessoaNotificacao pessoaNotificacao) throws DaoException, ServiceException {
		pessoaNotificacaoDao.persistirEntidade(pessoaNotificacao);
		return pessoaNotificacao;
	}

	@Transactional(value = "transactionManager")
	public PessoaNotificacao atualizar(PessoaNotificacao pessoaNotificacao) throws DaoException, ServiceException {
		if (pessoaNotificacao.getId() == null) {
			throw new ServiceException("Notificação não pode ser alterada!");
		}
		pessoaNotificacaoDao.updateEntidade(pessoaNotificacao);
		return pessoaNotificacao;
	}
	
	@Transactional(value = "transactionManager")
	public void excluir(PessoaNotificacao pessoaNotificacao) throws ServiceException, DaoException {
		pessoaNotificacaoDao.excluirEntidade(pessoaNotificacao);
	}

	public PessoaNotificacaoDao getPessoaNotificacaoDao() {
		return pessoaNotificacaoDao;
	}

	public void setPessoaNotificacaoDao(PessoaNotificacaoDao pessoaNotificacaoDao) {
		this.pessoaNotificacaoDao = pessoaNotificacaoDao;
	}

}
