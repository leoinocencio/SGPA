package br.com.sgpa.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sgpa.entity.PessoaNotificacao;

@Repository
public class PessoaNotificacaoDao extends GenericDaoMysql<PessoaNotificacao> {

	@SuppressWarnings("unchecked")
	public List<PessoaNotificacao> listaNotificacao(PessoaNotificacao pessoaNotificacao) {
		StringBuilder hql = new StringBuilder("SELECT pn FROM PessoaNotificacao pn WHERE 1=1 ");
		if (pessoaNotificacao.getRemetente() != null) {
			hql.append(" AND pn.remetente.id = :idPessoa");
		}
		else if (pessoaNotificacao.getReceptor() != null) {
			hql.append(" AND pn.receptor.id = :idPessoa");
		}
		Query query = getSession().createQuery(hql.toString());
		if (pessoaNotificacao.getRemetente() != null) {
			query.setParameter("idPessoa", pessoaNotificacao.getRemetente().getId());
		}
		else if (pessoaNotificacao.getReceptor() != null) {
			query.setParameter("idPessoa", pessoaNotificacao.getReceptor().getId());
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PessoaNotificacao> listaNotificacaoEnviada(int idPessoa) {
		StringBuilder hql = new StringBuilder("SELECT pn FROM PessoaNotificacao pn WHERE 1=1 ");
		hql.append(" AND pn.remetente.id = :idPessoa");
		Query query = getSession().createQuery(hql.toString());
		query.setParameter("idPessoa", idPessoa);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PessoaNotificacao> listaNotificacaoRecebida(int idPessoa) {
		StringBuilder hql = new StringBuilder("SELECT pn FROM PessoaNotificacao pn WHERE 1=1 ");
		hql.append(" AND pn.receptor.id = :idPessoa");
		Query query = getSession().createQuery(hql.toString());
		query.setParameter("idPessoa", idPessoa);
		return query.list();
	}

}
