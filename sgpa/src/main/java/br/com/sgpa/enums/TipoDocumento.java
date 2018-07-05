package br.com.sgpa.enums;

import java.util.Arrays;
import java.util.List;

public enum TipoDocumento {
	PDF("PDF", ".pdf"), DOCX("Word 2010", ".docx"), DOC("Word", ".doc"), ODT("Open Office", ".odt"), ODF("Libre Office",
			".odf"), RAR("RAR", ".rar"), ZIP("ZIP", ".zip"), JPG("JPG", ".jpg"), JPEG("JPEG", ".jpeg"), PNG("PNG", ".png");

	private String valor;
	private String descricao;

	private TipoDocumento(String descricao, String valor) {
		this.valor = valor;
		this.descricao = descricao;
	}

	public String getValor() {
		return this.valor;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public static List<TipoDocumento> getListaTipoDocumento() {
		return Arrays.asList(TipoDocumento.values());
	}

}
