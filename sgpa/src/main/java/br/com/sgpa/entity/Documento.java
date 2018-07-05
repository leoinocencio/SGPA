package br.com.sgpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "documento", schema = "sgpa")
public class Documento extends BaseEntity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7237750027912206114L;

	@Id
	@Column(name = "id_documento", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Lob
	@Column(name = "arquivo_virtual", columnDefinition = "Blob")
	private byte[] arquivoDocumento;

	@Column(name = "dsc_documento")
	private String nome;

	@Column(name = "tipo_documento", nullable = false, length = 6)
	private String tipoDocumento;

	public Documento() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getArquivoDocumento() {
		return arquivoDocumento;
	}

	public void setArquivoDocumento(byte[] arquivoDocumento) {
		this.arquivoDocumento = arquivoDocumento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

}