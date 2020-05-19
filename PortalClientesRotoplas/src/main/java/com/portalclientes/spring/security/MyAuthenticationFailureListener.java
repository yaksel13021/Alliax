/**
 * Listener para excepciones en login de SS
 * @author saul.ibarra
 * @fecha 7-Mar-16
 */
package com.portalclientes.spring.security;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.service.UsuarioService;
import com.alliax.portalclientes.util.Helper;
import com.alliax.portalclientes.view.AbstractBacking;

public class MyAuthenticationFailureListener implements AuthenticationFailureHandler  {

    private static Logger logger = Logger.getLogger(MyAuthenticationFailureListener.class);
    
    private static final String BAD_CREDENTIALS_MESSAGE = "1";
    private static final String CREDENTIALS_EXPIRED_MESSAGE = "2";
    private static final String DISABLED_MESSAGE = "3";
    private static final String LOCKED_MESSAGE = "4";	
	
	@Autowired
	public UsuarioService usuarioService;
    
    
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
			throws IOException, ServletException {
		
		String userName = request.getParameter("login:j_username");
		
		logger.info("userName " + userName);
		logger.info("Password " + request.getParameter("login:j_password"));		
		logger.info("Ex " + ex.getLocalizedMessage());
		logger.info("Ex Cause " + ex.getCause());
		
		if(userName != null && !userName.trim().equals("")){
			Usuario usuario = usuarioService.findByUserName(userName);
			
			//Solo bloquea x intentos fallidos estatus Activo e Iniciales.
			if(usuario != null && 
					(usuario.getEstatus().equals("A") || 
						usuario.getEstatus().equals("I") ||
							usuario.getEstatus().equals("F")) ) {
				logger.info("Usr: " + usuario.getUsuario());
				logger.info("Intentos fallidos " + usuario.getIntentosFallidos());
				
				usuario.setIntentosFallidos(usuario.getIntentosFallidos() + 1);
				
				//Bloquea usuario
				if(usuario.getIntentosFallidos() >= 3){
					usuario.setEstatus("F");
				}
				
				//Save info
				usuario = usuarioService.save(usuario);
												
				/*if(usuario.getEstatus().equalsIgnoreCase("F")){
					logger.error("Err dis: " + DISABLED_MESSAGE);
					response.sendRedirect("/index.xhtml?error=" + DISABLED_MESSAGE);
				} else if(usuario.getEstatus().equalsIgnoreCase("B")){
					logger.error("Err lock " + LOCKED_MESSAGE);
					response.sendRedirect("/index.xhtml?error=" + LOCKED_MESSAGE);			
				}*/
			}
		}
		
		
		if (ex instanceof CredentialsExpiredException) {
			logger.error("Err cred" + CREDENTIALS_EXPIRED_MESSAGE);
			response.sendRedirect("./indexPwdChange.xhtml?error=" + CREDENTIALS_EXPIRED_MESSAGE);
			Helper.setLoginError(new Integer(CREDENTIALS_EXPIRED_MESSAGE));
		} else if (ex.getCause() instanceof DisabledException) {
			logger.error("Err dis 2 " + DISABLED_MESSAGE);
			response.sendRedirect("./index.xhtml?error=" + DISABLED_MESSAGE);
			Helper.setLoginError(new Integer(DISABLED_MESSAGE));
		} else if (ex instanceof BadCredentialsException) {
			logger.error("Errr bad " + BAD_CREDENTIALS_MESSAGE);
			response.sendRedirect("./index.xhtml?error=" + BAD_CREDENTIALS_MESSAGE);
			Helper.setLoginError(new Integer(BAD_CREDENTIALS_MESSAGE));
		} else if (ex.getCause() instanceof LockedException) {
			logger.error("Err Lock 2" + LOCKED_MESSAGE);
			response.sendRedirect("./index.xhtml?error=" + LOCKED_MESSAGE);
			Helper.setLoginError(new Integer(LOCKED_MESSAGE));
		} else {
			logger.error("Error Desconocido " + ex.getLocalizedMessage());
			response.sendRedirect("./index.xhtml?error=5");
			Helper.setLoginError(5);
		}
	}
}
