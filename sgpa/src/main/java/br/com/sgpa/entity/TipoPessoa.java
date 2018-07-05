package br.com.sgpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_pessoa", schema = "sgpa")
public class TipoPessoa extends BaseEntity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5988254744013193275L;

	@Id
	@Column(name = "id_tipo_pessoa", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "descricao", nullable = false, length = 255)
	private String descricao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
