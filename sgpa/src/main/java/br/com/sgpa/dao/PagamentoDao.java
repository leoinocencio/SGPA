package br.com.sgpa.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sgpa.entity.Pagamento;

@Repository
public class PagamentoDao extends GenericDaoMysql<Pagamento> {

	@SuppressWarnings("unchecked")
	public List<Pagamento> listaPagamentoPorPessoa(Pagamento pagamento) {
		StringBuilder hql = new StringBuilder("SELECT p FROM Pagamento p WHERE 1=1 ");
		if (pagamento.getProcesso() != null && pagamento.getProcesso().getId() != null) {
			hql.append(" AND p.processo.id = :processo");
		} 
		if (pagamento.getCliente() != null && pagamento.getCliente().getId() != null)  {
			hql.append(" AND p.processo.cliente.id = :cliente");
		}
		if (pagamento.getNumeroPagamento() != null && !pagamento.getNumeroPagamento().isEmpty()) {
			hql.append(" AND p.numeroPagamento = :numeroPagamento");
		}
		if (pagamento.getDataPagamento() != null) {
			hql.append(" AND DATE_FORMAT(p.dataPagamento, '%Y-%m-%d') = :dataPagamento");
		}
		hql.append(" ORDER BY p.numeroPagamento");
		Query query = getSession().createQuery(hql.toString());
		if (pagamento.getProcesso() != null && pagamento.getProcesso().getId() != null) {
			query.setParameter("processo", pagamento.getProcesso().getId());
		} 
		if (pagamento.getCliente() != null && pagamento.getCliente().getId() != null)  {
			query.setParameter("cliente", pagamento.getCliente().getId());
		}
		if (pagamento.getNumeroPagamento() != null && !pagamento.getNumeroPagamento().isEmpty()) {
			query.setParameter("numeroPagamento", pagamento.getNumeroPagamento());
		}
		if (pagamento.getDataPagamento() != null) {
			query.setParameter("dataPagamento", new SimpleDateFormat("yyyy-MM-dd").format(pagamento.getDataPagamento()));
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Pagamento> listaPagamentos(boolean advogados) {
		StringBuilder hql = new StringBuilder("SELECT p FROM Pagamento p ");
		if (advogados)
			hql.append(" ORDER BY p.processo.advogado.nome ASC");
		else 
			hql.append(" ORDER BY p.processo.tpProcesso ASC");
		Query query = getSession().createQuery(hql.toString());
		return query.list();
	}

}
