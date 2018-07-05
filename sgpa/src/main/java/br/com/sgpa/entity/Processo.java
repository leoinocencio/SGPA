package br.com.sgpa.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.sgpa.enums.TipoProcesso;

@Entity
@Table(name = "processo", schema = "sgpa")
public class Processo extends BaseEntity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4218965186937744280L;

	@Id
	@Column(name = "id_processo", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "numero_processo", length = 255)
	private String numeroProcesso;

	@Column(name = "valor_indenizacao")
	private BigDecimal valorIndenizacao;

	@ManyToOne
	@JoinColumn(name = "id_cliente", nullable = false)
	private Pessoa cliente;

	@ManyToOne
	@JoinColumn(name = "id_advogado", nullable = false)
	private Pessoa advogado;

	@Column(name = "tp_processo", nullable = false, length = 255)
	@Enumerated(EnumType.STRING)
	private TipoProcesso tpProcesso;

	@Column(name = "st_processo", length = 255)
	private String stProcesso;

	@ManyToOne
	@JoinColumn(name = "id_documento", nullable = false)
	private Documento documento;

	@Column(name = "dt_inicio")
	@Temporal(TemporalType.DATE)
	private Date dtInicio;

	@Column(name = "dt_fim")
	@Temporal(TemporalType.DATE)
	private Date dtFim;

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public BigDecimal getValorIndenizacao() {
		return valorIndenizacao;
	}

	public void setValorIndenizacao(BigDecimal valorIndenizacao) {
		this.valorIndenizacao = valorIndenizacao;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pessoa getCliente() {
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}

	public Pessoa getAdvogado() {
		return advogado;
	}

	public void setAdvogado(Pessoa advogado) {
		this.advogado = advogado;
	}

	public String getStProcesso() {
		return stProcesso;
	}

	public void setStProcesso(String stProcesso) {
		this.stProcesso = stProcesso;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Integer getId() {
		return id;
	}

	public TipoProcesso getTpProcesso() {
		return tpProcesso;
	}

	public void setTpProcesso(TipoProcesso tpProcesso) {
		this.tpProcesso = tpProcesso;
	}

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Date getDtFim() {
		return dtFim;
	}

	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}

}
