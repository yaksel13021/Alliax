/**
 * Objeto para almacenar informacion del login.
 * @author saul.ibarra
 * @fecha 24-Feb-16
 */
package com.alliax.portalclientes.model;

import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails extends User {
	
	private final static Logger logger = Logger.getLogger(CustomUserDetails.class);

	private String noCliente;
	private String email;
	private String pais;
	private String language;
	
	
	public CustomUserDetails(String username, String password,Collection<? extends GrantedAuthority> authorities, String noCliente, String email, String pais){
		super(username, password, authorities);
		this.noCliente = noCliente;
		this.email = email;
		 if(pais != null){ 
	    this.pais = pais.trim();}
			
		
		//Extrae informacion del idioma		
		Locale locale = LocaleContextHolder.getLocale();
//		String lang = locale.getLanguage().toUpperCase();
				
//		if(!lang.equalsIgnoreCase("ES") && !lang.equalsIgnoreCase("EN"))
//			lang = "EN";	
      	String baseName = "com.alliax.portalclientes.locale.Labels";		
        ClassLoader loader = Thread.currentThread().getContextClassLoader();  
        
        
//		String lang = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("lang");
//		 logger.info("loader::::::::::::::::: " + loader);
//		 logger.info("lang::::::::::::::::: " + lang);
		
		
//		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lang", lang);		
		this.language = locale.getLanguage();
	}

	public String getNoCliente() {
		return noCliente;
	}

	public String getEmail() {
		return email;
	}

	public String getPais() {
		return pais;
	}

	public String getLanguage() {
		return language;
	}	
}
