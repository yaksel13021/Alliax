/**
 * Abstract class para metodo comunes JSF
 * @author saul.ibarra
 * @fecha 27-Enero-2016
 */
package com.alliax.portalclientes.view;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.jsf.FacesContextUtils;

import com.alliax.portalclientes.controller.InfoClienteRFC;
import com.alliax.portalclientes.model.ClienteInfo;
import com.alliax.portalclientes.model.CustomUserDetails;


public abstract class AbstractBackingGen {
	
	private final static Logger logger = Logger.getLogger(AbstractBackingGen.class);
	
	private String test = "Prueba Abstract";
	
	private ClienteInfo clienteInfo;
	
	private String noCliente;

	private boolean usrVentas;
	private boolean usrPeru; //Usuario de peru
	private boolean usrAdmin;
	private boolean usrEjecutivo;
	
	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
	
	/*@ManagedProperty(value="#{facesContext}")
	private FacesContext facesContext = FacesContext.getCurrentInstance();*/
	
	/*@ManagedProperty(value="#{requestScope}")
	private Map<String,Object> requestMap = getFacesContext().getExternalContext().getRequestMap();*/	
	
	public ClienteInfo getClienteInfo() {
		return clienteInfo;
	}

	public void setClienteInfo(ClienteInfo clienteInfo) {
		this.clienteInfo = clienteInfo;
	}

	public String getNoCliente() {
		return noCliente;
	}

	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}

	public boolean isUsrEjecutivo() {
		return usrEjecutivo;
	}

	public void setUsrEjecutivo(boolean usrEjecutivo) {
		this.usrEjecutivo = usrEjecutivo;
	}

	public boolean isUsrVentas() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		usrVentas = false;
		
		if(!(authentication instanceof AnonymousAuthenticationToken)){			
			if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENTAS_MEMBER")) ||
			    authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EJECUTIVO_MEMBER")) ||
			     authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENTAS_MEMBER_AR")) ||
			      authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EJECUTIVO_MEMBER_PE")) ||
			       authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENTAS_MEMBER_PE")) ||
			        authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EJECUTIVO_MEMBER_CA")) ||
			         authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENTAS_MEMBER_CA")) ||
				      authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EJECUTIVO_MEMBER_BR")) ||
				       authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENTAS_MEMBER_BR"))) {
				usrVentas = true;
			}			
		}
		return usrVentas;
	}
	
	public boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		usrAdmin = false;
		
		if(!(authentication instanceof AnonymousAuthenticationToken)){			
			if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN_MEMBER")) ||
			    authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN_MEMBER_AR")) ||
			     authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN_MEMBER_PE")) ||
			      authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN_MEMBER_CA")) ||
			       authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN_MEMBER_BR"))) {
				usrAdmin = true;
			}			
		}
		return usrAdmin;
	}

	public boolean isEjecutivo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		usrEjecutivo = false;
		
		if(!(authentication instanceof AnonymousAuthenticationToken)){			
			if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EJECUTIVO_MEMBER")) ||
			    authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EJECUTIVO_MEMBER_AR")) ||
			     authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EJECUTIVO_MEMBER_PE")) ||
			      authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EJECUTIVO_MEMBER_CA")) ||
			       authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EJECUTIVO_MEMBER_BR"))) {
				usrEjecutivo = true;
			}			
		}
		return usrEjecutivo;
	}
	
	public void setUsrVentas(boolean usrVentas) {
		this.usrVentas = usrVentas;
	}
	
	public boolean isUsrPeru(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		usrPeru = false;
		
		if(!(authentication instanceof AnonymousAuthenticationToken)){			
			if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN_MEMBER_PE")) ||
			    authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EJECUTIVO_MEMBER_PE")) ||
			      authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENTAS_MEMBER_PE")) ) {
				usrPeru = true;
			}			
		}		
		return usrPeru;
	}
	
	public void setUsrPeru(boolean usrPeru){
		this.usrPeru = usrPeru;
	}

	@ManagedProperty(value="#{sessionScope}")
	private Map<String,Object> sessionMap = getFacesContext().getExternalContext().getSessionMap();
			
	private ApplicationContext springContext = FacesContextUtils.getWebApplicationContext(this.getFacesContext());	
	
	@ManagedProperty(value="#{lblMain}")
	private ResourceBundle lblMain;
	

	public ResourceBundle getLblMain() {
		return lblMain;
	}

	public void setLblMain(ResourceBundle lblMain) {
		this.lblMain = lblMain;
	}

	public void setSessionMap(Map<String, Object> sessMap){
		sessionMap = sessMap;
	}
	
	public Map<String, Object> getSessionMap(){
		return sessionMap;
	}
		
	/*public void setRequestMap(Map<String, Object> reqMap){
		requestMap = reqMap;
	}
	
	public Map<String, Object> getRequestMap(){
		return requestMap;
	}*/
	
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	/*public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}*/
	
	public Flash getFlash(){
		return getFacesContext().getExternalContext().getFlash();
	}

	
	/*public ApplicationContext getSpringContext() {
		return FacesContextUtils.getWebApplicationContext(this.getFacesContext());
	}*/
	
	public ApplicationContext getSpringContext() {
		return springContext;
	}

	public void setSpringContext(ApplicationContext springContext) {
		this.springContext = springContext;
	}

	/**
	 * Activa/Desactiva estilo en pagina de resumen
	 * @return
	 */
	public String getPaginaActual() {
        String viewId = getFacesContext().getViewRoot().getViewId();
        if (viewId.startsWith("inicio") || viewId.startsWith("altaCE")) {
            return "resumen";
        }
        
        return "";
    }	
	
	public String getPaginaInicio() {
        String viewId = getFacesContext().getViewRoot().getViewId();
        if (viewId.startsWith("index") || viewId.startsWith("recuperaPassword")) {
            return "false";
        } else {
        	return "true";
        }
    }		
	
	/**
	 * Regresa el usuario logueado en el sistema
	 * @return
	 */
	public CustomUserDetails getUsuarioLogueado(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(!(authentication instanceof AnonymousAuthenticationToken)){
			//logger.info("Email " + ((CustomUserDetails)authentication.getPrincipal()).getEmail());
			return (CustomUserDetails)authentication.getPrincipal();
		} else {
			//logger.info("Email: Invitado ");
			CustomUserDetails invitado = new CustomUserDetails("Invitado",null,null,null,null,null);
			return invitado;
		}
	}
	
	
	/**
	 * Determina si se esta ingresando a traves de un dispositivo movil o navegador estandar.
	 * @return
	 */
	public boolean isMobileDevice(){
		try{
			HttpServletRequest req = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
			String userAgent = req.getHeader("user-agent");	
			
			logger.info("User-Agent " + userAgent);
			
			if(userAgent.indexOf("iPhone") > 0)
				return true;
			else if (userAgent.indexOf("Android") > 0)
				return true;
			else if (userAgent.indexOf("Windows") > 0)
				return false;
			else
				return false;
		} catch(Exception e){
			return false;
		}
	}
	
	public void validaUsuarioCliente(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean rolCxp = false;
		
		if(!(authentication instanceof AnonymousAuthenticationToken)){			
			if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENTAS_MEMBER")) ||
					authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EJECUTIVO_MEMBER"))){
				rolCxp = true;
			}			
		}

	}
	
	
			
}
