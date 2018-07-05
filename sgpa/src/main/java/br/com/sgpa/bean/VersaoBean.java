package br.com.sgpa.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.faces.bean.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@ManagedBean(name = "versaoBean")
@Component
@Scope(value = "application")
public class VersaoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 763027381553308548L;
	
	private String versaoSistema;

	public String getVersaoSistema() {
		if (versaoSistema == null) {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
			Properties properties = new Properties();
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			versaoSistema = properties.getProperty("build.version");
		}
		return versaoSistema;
	}



	public void setVersaoSistema(String versaoSistema) {
		this.versaoSistema = versaoSistema;
	}


}
