/**
 * Objeto para condicionar la pagina de inicio al loguearse al portal....
 * 
 * Quiza no se use
 */
package com.portalclientes.spring.security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.general.formato.Fecha;
import com.alliax.portalclientes.model.CustomUserDetails;
import com.alliax.portalclientes.service.UsuarioService;

public class MySuccessHandler implements AuthenticationSuccessHandler  {

	private final static Logger logger = Logger.getLogger(MySuccessHandler.class);
	
	@Autowired
	public UsuarioService usuarioService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		logger.info("My successful authentication");
		        
		logger.info("Usuario " + ((CustomUserDetails)authentication.getPrincipal()).getUsername());
		
		Usuario usuario = usuarioService.findByUserName(((CustomUserDetails)authentication.getPrincipal()).getUsername());
		
		if(usuario != null){
			logger.info("Reseteando valores...");
			logger.info("FehaHora " + Fecha.getDateTimeActual());
			usuario.setFechaEntrada(Fecha.getDateTimeActual());
			usuario.setIntentosFallidos(0);
			usuario = usuarioService.save(usuario);
		}
		
		logger.info("Fecha entrada "+ usuario.getFechaEntrada());
		

		//Redirect
        response.sendRedirect("./inicio.xhtml");   
        return;
		
		//Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		/*if (roles.contains("ROLE_ADMIN_MEMBER")){
            response.sendRedirect("./inicio.xhtml");   
            return;
        } else if(roles.contains("ROLE_CLIENT_MEMBER")){
        	response.sendRedirect("./inicio.xhtml");
        	return;
        }*/
		
	}

}
