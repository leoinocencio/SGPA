package br.com.sgpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contrato", schema = "sgpa")
public class Contrato extends BaseEntity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 669305456609974446L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

//	@ManyToOne
//	@JoinColumn(name = "id_cliente", nullable = false)
//	private Pessoa cliente;
//
//	@ManyToOne
//	@JoinColumn(name = "id_advogado", nullable = false)
//	private Pessoa advogado;

	@ManyToOne
	@JoinColumn(name = "id_processo", nullable = false)
	private Processo processo;

	@ManyToOne
	@JoinColumn(name = "id_documento", nullable = false)
	private Documento documento;

//	public Pessoa getCliente() {
//		return cliente;
//	}
//
//	public void setCliente(Pessoa cliente) {
//		this.cliente = cliente;
//	}
//
//	public Pessoa getAdvogado() {
//		return advogado;
//	}
//
//	public void setAdvogado(Pessoa advogado) {
//		this.advogado = advogado;
//	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

}
