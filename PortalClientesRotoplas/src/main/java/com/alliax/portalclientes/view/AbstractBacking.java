/**
 * Abstract class para metodo comunes JSF
 * @author saul.ibarra
 * @fecha 27-Enero-2016
 */
package com.alliax.portalclientes.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.jsf.FacesContextUtils;
import org.apache.commons.io.IOUtils;

import com.alliax.portalclientes.model.CustomUserDetails;

//@ViewScoped
@SessionScoped
public abstract class AbstractBacking {
	
	
	private final static Logger logger = Logger.getLogger(AbstractBacking.class);
		
	/*@ManagedProperty(value="#{facesContext}")
	private FacesContext facesContext = FacesContext.getCurrentInstance();*/
	
	//@ManagedProperty(value="#{requestScope}")
	private Map<String,Object> requestMap = getFacesContext().getExternalContext().getRequestMap();
	
	
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
		
	public void setRequestMap(Map<String, Object> reqMap){
		requestMap = reqMap;
	}
	
	public Map<String, Object> getRequestMap(){
		return requestMap;
	}
	
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
		//return this.facesContext;
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
//			logger.info("Email " + ((CustomUserDetails)authentication.getPrincipal()).getEmail());
			return (CustomUserDetails)authentication.getPrincipal();
		} else {
//			logger.info("Email: Invitado ");
			CustomUserDetails invitado = new CustomUserDetails("Invitado",null,null,null,null,null);
			return invitado;
		}
	}
	
	public String salir(){
		try {
			logger.info("salir Abstract");
			//Log Off
			String lang = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("lang");
			
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			new SecurityContextLogoutHandler().logout((HttpServletRequest)context.getRequest(), 
														(HttpServletResponse)context.getResponse(), 
															auth);
			changeLocaleLoad(lang);
		} catch(Exception e){
			logger.error("Error en logoff " + e.getLocalizedMessage(),e);
		}
		
		return "/logOff.xhtml?faces-redirect=true";
	}
	
	
	/**
	 * Termina session
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String logOffOld(){
		try {			
			
			/*ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
					.getRequestDispatcher("/j_spring_security_logout");
			
			dispatcher.forward((ServletRequest)context.getRequest(), (ServletResponse)context.getResponse());
			
			FacesContext.getCurrentInstance().responseComplete();
			
			return null;*/
			
			//Log Off
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			new SecurityContextLogoutHandler().logout((HttpServletRequest)context.getRequest(), 
														(HttpServletResponse)context.getResponse(), 
															auth);
		} catch(Exception e){
			logger.error("Error en logoff " + e.getLocalizedMessage(),e);			
		}
		
		return "";
	}
	

	/**
	 * Descarga pdf de lista de precios.
	 * @param tipoLista
	 */
	public void listaPreciosPdf(String tipoLista){
		try {
			logger.debug("Tipo lista " + tipoLista);
			
			File pdfFile;
			String nombreLista;
		
			if(tipoLista.equalsIgnoreCase("construccion")){			
				pdfFile = new File("D:\\PortalClientes\\ListaPrecios\\Lista_de_Precio_Rotoplas_Construccion.pdf");
				//pdfFile = new File("C:\\CoyoteBeta\\Lista_de_Precio_Rotoplas_Construccion.pdf");
				nombreLista="Lista_de_Precio_Rotoplas_Construccion";
			} else if(tipoLista.equalsIgnoreCase("tuboplus")){
				pdfFile = new File("D:\\PortalClientes\\ListaPrecios\\Lista_de_precios_Tuboplus_hidraulico.pdf");
				//pdfFile = new File("C:\\CoyoteBeta\\Lista_de_precios_Tuboplus_hidraulico.pdf");
				nombreLista="Lista_de_precios_Tuboplus_hidraulico";
			} else if(tipoLista.equalsIgnoreCase("industriales")){
				pdfFile = new File("D:\\PortalClientes\\ListaPrecios\\Lista_de_Precio_Rotoplas_Industria.pdf");
				//pdfFile = new File("C:\\CoyoteBeta\\Lista_de_precios_Tuboplus_hidraulico.pdf");
				nombreLista="Lista_de_Precio_Rotoplas_Industria";
			} else if(tipoLista.equalsIgnoreCase("AgroIndustria")){
				pdfFile = new File("D:\\PortalClientes\\ListaPrecios\\Lista_de_Precio_Rotoplas_AgroIndustria.pdf");
				nombreLista="Lista_de_Precio_Rotoplas_AgroIndustria";				
			} else if(tipoLista.equalsIgnoreCase("purificadores")){
				pdfFile = new File("D:\\PortalClientes\\ListaPrecios\\Lista_de_Precio_Rotoplas_Purificadores.pdf");
				nombreLista="Lista_de_Precio_Rotoplas_Purificadores";				
			} else {
				pdfFile = new File("D:\\PortalClientes\\ListaPrecios\\Lista_de_Precio_Rotoplas_Coladeras_Registros.pdf");
				nombreLista="Lista_de_Precio_Rotoplas_Coladeras_Registros";
			}
						
			InputStream is = new FileInputStream(pdfFile);
			
			byte[] test = IOUtils.toByteArray(is);
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = this.getFacesContext().getExternalContext();
			externalContext.responseReset();
			externalContext.setResponseContentType("application/pdf");
			
			externalContext.setResponseHeader("Content-Disposition", "inline;filename=" + nombreLista + ".pdf");
			
			OutputStream os = externalContext.getResponseOutputStream();
			
			os.write(test);
			context.responseComplete(); // Prevent JSF from performing navigation.			
			
		} catch(Exception e){ 
			logger.error("Error al obtener listad de Precios " + e.getLocalizedMessage());
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

	/**
	 * Metodo para ingresar al tracking Proveedores y Ventas
	 * @return
	 */
	public String getTracking(String trk){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean rolCxp = false;
		
		if(!(authentication instanceof AnonymousAuthenticationToken)){			
			if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENTAS_MEMBER")) || 
					authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EJECUTIVO_MEMBER"))){
				rolCxp = true;
			}			
		}
		
		//Si tiene rol de Cxp pide RFC.
		if(rolCxp)
			return "/accesoVtas/index";
		else
			return trk;
	}
	

    /**
    * Cambia locale del portal
    * @param lang
    * @return
    */
    public void changeLocaleLoad(String locale){
    	                 		
//		String lang = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("lang");
		String langSession = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("lang");
//		logger.info("Parametro lang desde la url::::::::::::::: " + lang);
		//logger.info("Parametro lang desde sesion::::::::::::::: " + langSession);
		//logger.info("Parametro lang desde metodo::::::::::::::: " + locale);
				
		
      	String baseName = "com.alliax.portalclientes.locale.Labels";		
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if(locale == null || locale.isEmpty()){ locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale().getLanguage();  /**Locale del navegador*/ }
        switch(locale){
	    	case "pt":
	    	case "es":
	    		break;
	    	default:
	    		locale="es";
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("lblMain");
       		
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lang",locale);
        ResourceBundle rb = ResourceBundle.getBundle(baseName, new Locale(locale), loader);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lblMain", rb);
                   
    }			
	
	
	
}
