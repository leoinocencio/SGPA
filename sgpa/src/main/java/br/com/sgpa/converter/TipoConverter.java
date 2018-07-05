package br.com.sgpa.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.stereotype.Component;

import br.com.sgpa.enums.TipoProcesso;

@Component("tipoConverter")
public class TipoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		if (value != null && !value.equals("")) {
			try {
				if (value.contains("Selecione")) {
					return null;
				}
				return value;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value instanceof TipoProcesso) {
			if (((TipoProcesso) value).getDescricao() != null) {
				return ((TipoProcesso) value).getDescricao();
			}
		}
		return null;
	}

}
