package br.com.sgpa.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AutenticacaoFilter implements Filter {

    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
        ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = ((HttpServletRequest) request).getSession (true);

            System.out.println (session.getAttribute ("acesso"));
            if (session.getAttribute ("acesso") != null && !session.isNew ()) {

                chain.doFilter (request, response);
            } else {

                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect (req.getContextPath () + "/pages/login.jsf");
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
        finally {

        }

    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }
}
