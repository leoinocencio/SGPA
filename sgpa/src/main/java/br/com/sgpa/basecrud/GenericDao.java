package br.com.sgpa.basecrud;

import java.io.Serializable;
import java.util.List;

import br.com.sgpa.utils.exception.DaoException;

public interface GenericDao<T> {

	public T pesquisarPorId(Serializable id) throws DaoException;

	public List<T> getAll() throws DaoException;

	public void persistirEntidade(T entity) throws DaoException;

	public void excluirEntidade(T entity) throws DaoException;

	public void updateEntidade(T entity) throws DaoException;

}
