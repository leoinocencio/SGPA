package br.com.sgpa.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class GenericDaoMysql<T> implements GenericDao<T> {

	private Class<T> persistClass;

	@Resource(name = "sessionFactoryMysql")
	private SessionFactory sessionFactoryMysql;

	public GenericDaoMysql(SessionFactory sessionFactory) {
		this.sessionFactoryMysql = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public GenericDaoMysql() {
		this.persistClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected Session getSession() {
		return sessionFactoryMysql.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T pesquisarPorId(Serializable id){
		getSession().clear();
		T a = (T) getSession().getSessionFactory().getCurrentSession().get(this.persistClass, id);
		return a;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll(){
		getSession().clear();
		List<T> a = getSession().getSessionFactory().getCurrentSession().createCriteria(this.persistClass).list();
		return a;
	}

	public void persistirEntidade(T entity){
		getSession().getSessionFactory().getCurrentSession().save(entity);
		getSession().flush();
	}

	public void excluirEntidade(T entity){
		getSession().getSessionFactory().getCurrentSession().delete(entity);
		getSession().flush();
	}

	public void updateEntidade(T entity){
		getSession().getSessionFactory().getCurrentSession().merge(entity);
		getSession().flush();
	}
}
