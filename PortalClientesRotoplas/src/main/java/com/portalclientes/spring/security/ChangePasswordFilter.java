package com.portalclientes.spring.security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

public class ChangePasswordFilter extends OncePerRequestFilter  {

	private final static Logger logger = Logger.getLogger(ChangePasswordFilter.class);
			
	private static final String HttpServletResponse = null;


	/*@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	}*/
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, javax.servlet.http.HttpServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		//logger.info("Do filter!");
		//logger.info("URL " + request.getRequestURI());
		
		if(request.getRequestURI().endsWith("xhtml") && 
			!request.getRequestURI().contains("logo.png") &&
				!request.getRequestURI().contains("logOff")){
					if(isPwdChange()){			
						//logger.info("Cambia PWD FWD");
						request.getRequestDispatcher("/indexPwdChange.xhtml?message=")
							.forward(request, response);
						return;
					}
		}
		//logger.info("No cambia PWD");
		chain.doFilter(request, response);
	}
	
	/**
	 * Determina si tiene el rol de PWDCHANGE
	 * @return
	 */
	private boolean isPwdChange() {
		Set<String> roles = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        		
		if (roles.contains("ROLE_PWDCHANGE_MEMBER")){
			return true;
        } else {
        	return false;
        }
	}
	

	
	
}
