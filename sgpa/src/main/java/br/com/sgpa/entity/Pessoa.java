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
@Table(name = "pessoa", schema = "sgpa")
public class Pessoa extends BaseEntity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1430806004428178035L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "nome", nullable = false, length = 255)
	private String nome;

	@Column(name = "cpf", nullable = false, length = 11)
	private String cpf;

	@Column(name = "rg", length = 9)
	private String rg;

	@Column(name = "n_oab", length = 50)
	private String n_oab;

	@Column(name = "n_carteira_trabalho", length = 50)
	private String n_carteira_trabalho;

	@Column(name = "login", nullable = false, length = 255)
	private String login;

	@Column(name = "senha", nullable = false, length = 100)
	private String senha;

	@Column(name = "ds_comentario", length = 4000)
	private String dsComentario;

	@ManyToOne
	@JoinColumn(name = "id_tipo_pessoa", nullable = false)
	private TipoPessoa tipoPessoa;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getN_oab() {
		return n_oab;
	}

	public void setN_oab(String n_oab) {
		this.n_oab = n_oab;
	}

	public String getN_carteira_trabalho() {
		return n_carteira_trabalho;
	}

	public void setN_carteira_trabalho(String n_carteira_trabalho) {
		this.n_carteira_trabalho = n_carteira_trabalho;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getDsComentario() {
		return dsComentario;
	}

	public void setDsComentario(String dsComentario) {
		this.dsComentario = dsComentario;
	}

}
