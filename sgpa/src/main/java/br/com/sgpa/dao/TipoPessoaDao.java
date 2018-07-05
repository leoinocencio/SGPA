package br.com.sgpa.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sgpa.entity.TipoPessoa;

@Repository
public class TipoPessoaDao extends GenericDaoMysql<TipoPessoa> {

	@SuppressWarnings("unchecked")
	public List<TipoPessoa> listaTodos() {
		StringBuilder hql = new StringBuilder("SELECT tp FROM TipoPessoa tp ");
		hql.append(" ORDER BY 1 DESC");
		Query query = getSession().createQuery(hql.toString());
		return query.list();
	}

}
