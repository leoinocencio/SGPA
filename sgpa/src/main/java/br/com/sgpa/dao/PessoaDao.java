package br.com.sgpa.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sgpa.entity.Pessoa;

@Repository
public class PessoaDao extends GenericDaoMysql<Pessoa> {
	
	public Pessoa pesquisarLogin(String login, String senha) {
		StringBuilder hql = new StringBuilder("SELECT p FROM Pessoa p WHERE 1=1 ");
		hql.append(" AND p.login = :login and p.senha = :senha");
		Query query = getSession().createQuery(hql.toString());
		query.setParameter("login", login.toUpperCase());
		query.setParameter("senha", senha);
		return (Pessoa) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Pessoa> listaPessoa(String tipo) {
		StringBuilder hql = new StringBuilder("SELECT p FROM Pessoa p WHERE 1=1 ");
		hql.append(" AND p.tipoPessoa.descricao = :tipo ORDER BY p.nome");
		Query query = getSession().createQuery(hql.toString());
		query.setParameter("tipo", tipo.toUpperCase());
		return query.list();
	}
	
}
