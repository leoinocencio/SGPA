package br.com.sgpa.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.sgpa.entity.Contrato;

@Repository
public class ContratoDao extends GenericDaoMysql<Contrato> {
	
	@SuppressWarnings("unchecked")
	public List<Contrato> listaContratoPorParametros(Contrato contrato) {
		StringBuilder hql = new StringBuilder("SELECT c FROM Contrato c WHERE 1=1 ");
//		if (contrato.getProcesso().getAdvogado() != null && contrato.getProcesso().getAdvogado().getId() != null) {
//			hql.append(" AND c.processo.advogado.id = :advogado");
//		} 
//		if (contrato.getProcesso().getCliente() != null && contrato.getProcesso().getCliente().getId() != null)  {
//			hql.append(" AND c.processo.cliente.id = :cliente");
//		}
		if (contrato.getProcesso() != null && contrato.getProcesso().getId() != null) {
			hql.append(" AND c.processo.id = :processo");
		}
		if (contrato.getId() != null) {
			hql.append(" AND c.id = :id");
		}
		hql.append(" ORDER BY c.id");
		Query query = getSession().createQuery(hql.toString());
//		if (contrato.getProcesso().getAdvogado() != null && contrato.getProcesso().getAdvogado().getId() != null) {
//			query.setParameter("advogado", contrato.getProcesso().getAdvogado().getId());
//		} 
//		if (contrato.getProcesso().getCliente() != null && contrato.getProcesso().getCliente().getId() != null)  {
//			query.setParameter("cliente", contrato.getProcesso().getCliente().getId());
//		}
		if (contrato.getProcesso() != null && contrato.getProcesso().getId() != null) {
			query.setParameter("processo", contrato.getProcesso().getId());
		}
		if (contrato.getId() != null) {
			query.setParameter("id", contrato.getId());
		}
		return query.list();
	}

}
