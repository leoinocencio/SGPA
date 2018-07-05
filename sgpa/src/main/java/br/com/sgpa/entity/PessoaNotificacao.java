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
@Table(name = "pessoa_notificacao", schema = "sgpa")
public class PessoaNotificacao extends BaseEntity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7416316359080487218L;

	// @EmbeddedId
	// protected PessoaNotificacaoPK idPK;
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_remetente", nullable = false)
	private Pessoa remetente;

	@ManyToOne
	@JoinColumn(name = "id_receptor", nullable = false)
	private Pessoa receptor;

	@ManyToOne
	@JoinColumn(name = "id_processo", nullable = false)
	private Processo processo;

	@Column(name = "descricao", nullable = false, length = 4000)
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Pessoa getRemetente() {
		return remetente;
	}

	public void setRemetente(Pessoa remetente) {
		this.remetente = remetente;
	}

	public Pessoa getReceptor() {
		return receptor;
	}

	public void setReceptor(Pessoa receptor) {
		this.receptor = receptor;
	}

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

}
