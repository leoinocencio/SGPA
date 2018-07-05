package br.com.sgpa.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sgpa.entity.Processo;

@Repository
public class ProcessoDao extends GenericDaoMysql<Processo> {

	@SuppressWarnings("unchecked")
	public List<Processo> listaProcessoPorPessoa(Processo processo, boolean relatorio) {
		StringBuilder hql = new StringBuilder("SELECT p FROM Processo p WHERE 1=1 ");
		if (processo.getAdvogado() != null && processo.getAdvogado().getId() != null) {
			hql.append(" AND p.advogado.id = :advogado");
		} 
		if (processo.getCliente() != null && processo.getCliente().getId() != null)  {
			hql.append(" AND p.cliente.id = :cliente");
		}
		if (processo.getNumeroProcesso() != null && !processo.getNumeroProcesso().isEmpty()) {
			hql.append(" AND p.numeroProcesso = :numeroProcesso");
		}
		if (processo.getStProcesso() != null && !processo.getStProcesso().isEmpty()) {
			hql.append(" AND p.stProcesso = :stProcesso");
		}
		if (processo.getTpProcesso() != null) {
			hql.append(" AND p.tpProcesso = :tpProcesso");
		}
		if (processo.getDtInicio() != null && processo.getDtFim() == null) {
			hql.append(" AND DATE_FORMAT(p.dtInicio, '%Y-%m-%d') between STR_TO_DATE(:dtInicio, '%Y-%m-%d') and now()");
		}
		else if (processo.getDtInicio() != null && processo.getDtFim() != null) {
			hql.append(" AND DATE_FORMAT(p.dtFim, '%Y-%m-%d') between STR_TO_DATE(:dtInicio, '%Y-%m-%d') and STR_TO_DATE(:dtFim, '%d/%m/%Y')");
		}
		if (!relatorio) {
			hql.append(" ORDER BY p.numeroProcesso");
		} else {
			hql.append(" AND p.stProcesso IN ('PERDIDO','JULGADO')");
			hql.append(" ORDER BY p.advogado.nome, p.stProcesso");
		}
		Query query = getSession().createQuery(hql.toString());
		if (processo.getAdvogado() != null && processo.getAdvogado().getId() != null) {
			query.setParameter("advogado", processo.getAdvogado().getId());
		} 
		if (processo.getCliente() != null && processo.getCliente().getId() != null)  {
			query.setParameter("cliente", processo.getCliente().getId());
		}
		if (processo.getNumeroProcesso() != null && !processo.getNumeroProcesso().isEmpty()) {
			query.setParameter("numeroProcesso", processo.getNumeroProcesso());
		}
		if (processo.getStProcesso() != null && !processo.getStProcesso().isEmpty()) {
			query.setParameter("stProcesso", processo.getStProcesso());
		}
		if (processo.getTpProcesso() != null) {
			query.setParameter("tpProcesso", processo.getTpProcesso());
		}
		if (processo.getDtInicio() != null) {
			query.setParameter("dtInicio", new SimpleDateFormat("yyyy-MM-dd").format(processo.getDtInicio()));
		}
		if (processo.getDtFim() != null) {
			query.setParameter("dtFim", new SimpleDateFormat("yyyy-MM-dd").format(processo.getDtFim()));
		}
		return query.list();
	}
}
