package br.com.sgpa.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpa.business.TipoPessoaBusiness;
import br.com.sgpa.entity.TipoPessoa;
import br.com.sgpa.utils.exception.ServiceException;

@Component("tipoPessoaConverter")
public class TipoPessoaConverter implements Converter {

	@Autowired
	TipoPessoaBusiness tipoPessoaBusiness;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		if (value != null && !value.equals("")) {
			try {
				if (value.contains("Selecione")) {
					return null;
				}
				return tipoPessoaBusiness.pesquisarPorId(Integer.parseInt(value));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value instanceof TipoPessoa) {
			if (((TipoPessoa) value).getId() != null) {
				return ((TipoPessoa) value).getId().toString();
			}
		}
		return null;
	}

}
