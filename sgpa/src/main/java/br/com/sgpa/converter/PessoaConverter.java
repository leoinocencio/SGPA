package br.com.sgpa.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpa.business.PessoaBusiness;
import br.com.sgpa.entity.Pessoa;
import br.com.sgpa.utils.exception.ServiceException;

@Component("pessoaConverter")
public class PessoaConverter implements Converter {

	@Autowired
	PessoaBusiness pessoaBusiness;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		if (value != null && !value.equals("")) {
			try {
				if (value.contains("Selecione")) {
					return null;
				}
				return pessoaBusiness.pesquisarPorId(Integer.parseInt(value));
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
		if (value instanceof Pessoa) {
			if (((Pessoa) value).getId() != null) {
				return ((Pessoa) value).getId().toString();
			}
		}
		return null;
	}

}
