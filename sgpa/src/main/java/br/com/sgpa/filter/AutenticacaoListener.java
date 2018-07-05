package br.com.sgpa.filter;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class AutenticacaoListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6916937946643422637L;

	public void afterPhase(PhaseEvent event) {

		FacesContext facesContext = event.getFacesContext();
		String currentPage = facesContext.getViewRoot().getViewId();

		if (!(currentPage.lastIndexOf("cadastrarPessoa.xhtml") > -1)) {
			boolean isLoginPage = (currentPage.lastIndexOf("login.xhtml") > -1);
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
			Object currentUser = session.getAttribute("acesso");
			if (!isLoginPage && currentUser == null) {
				try {
					facesContext.getExternalContext().redirect("http://localhost:8080/sgpa/");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void beforePhase(PhaseEvent event) {
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
