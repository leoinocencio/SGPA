package br.com.sgpa.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.sgpa.business.ProcessoBusiness;
import br.com.sgpa.entity.Processo;
import br.com.sgpa.utils.exception.ServiceException;

@Component("processoConverter")
public class ProcessoConverter implements Converter {

	@Autowired
	ProcessoBusiness processoBusiness;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		if (value != null && !value.equals("")) {
			try {
				if (value.contains("Selecione")) {
					return null;
				}
				return processoBusiness.pesquisarPorId(Integer.parseInt(value));
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
		if (value instanceof Processo) {
			if (((Processo) value).getId() != null) {
				return ((Processo) value).getId().toString();
			}
		}
		return null;
	}

}
