package br.com.sgpa.basecrud.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.sgpa.dao.GenericDao;
import br.com.sgpa.utils.exception.DaoException;

public class GenericDaoImpl<T> implements GenericDao<T> {

	private Class<T> persistClass;

	@Autowired
	private SessionFactory sessionFactory;
	private Session session;

	public GenericDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
		this.persistClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	protected Session getSession() {
		if (session == null || !session.isOpen()) {
			session = this.sessionFactory.openSession();
		}
		return session;
	}

	@SuppressWarnings("unchecked")
	public T pesquisarPorId(Serializable id) throws DaoException {
		T a = (T) getSession().get(this.persistClass, id);
		getSession().close();
		return a;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() throws DaoException {
		List<T> a = getSession().createCriteria(this.persistClass).list();
		getSession().close();
		return a;
	}

	public void persistirEntidade(T entity) throws DaoException {
		getSession().save(entity);
		getSession().close();

	}

	public void excluirEntidade(T entity) throws DaoException {
		getSession().getSessionFactory().getCurrentSession().delete(entity);
		getSession().close();
	}

	public void updateEntidade(T entity) throws DaoException {
		getSession().getSessionFactory().getCurrentSession().merge(entity);
		getSession().close();
	}

}
