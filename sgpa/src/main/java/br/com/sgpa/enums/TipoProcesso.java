package br.com.sgpa.enums;

import java.util.Arrays;
import java.util.List;

public enum TipoProcesso {
	PENAL("PENAL", "PENAL"), TRABALHISTA("TRABALHISTA", "TRABALHISTA"), CIVEL("CIVEL", "CIVEL"), TRIBUTARIO("TRIBUTARIO",
			"TRIBUTÁRIO"), PREVIDENCIARIO("PREVIDENCIARIO", "PREVIDENCIÁRIO");

	private String valor;
	private String descricao;

	private TipoProcesso(String descricao, String valor) {
		this.valor = valor;
		this.descricao = descricao;
	}

	public String getValor() {
		return this.valor;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public static List<TipoProcesso> getListaTipoProcesso() {
		return Arrays.asList(TipoProcesso.values());
	}

}
