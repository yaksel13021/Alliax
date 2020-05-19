/**
 * Backing bena para Login
 * @author saul.ibarra
 * @fecha 9-Abril-2015
 */
package com.alliax.portalclientes.view;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.alliax.portalclientes.util.Helper;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

@ManagedBean(name="login")
@RequestScoped
public class ManageLogin extends AbstractBackingGen {
	
	//Variables
	private String rfc;
	private String usuario;
	private String password;
	private String nuevoPwd;
	private String confirmaPwd;
	private boolean cambiaPassword;
	
	private Integer returnCode;
	
	//Instancias
	private Logger logger = Logger.getLogger(ManageLogin.class);

	
	
	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isCambiaPassword() {
		return cambiaPassword;
	}

	public void setCambiaPassword(boolean cambiaPassword) {
		this.cambiaPassword = cambiaPassword;
	}		

	public String getNuevoPwd() {
		return nuevoPwd;
	}

	public void setNuevoPwd(String nuevoPwd) {
		this.nuevoPwd = nuevoPwd;
	}

	public String getConfirmaPwd() {
		return confirmaPwd;
	}

	public void setConfirmaPwd(String confirmaPwd) {
		this.confirmaPwd = confirmaPwd;
	}

	public Integer getReturnCode() {
		/*if(this.returnCode != Helper.loginError) {
			this.setReturnCode(Helper.loginError);
		}*/
		return returnCode;
	}

	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}

	/**
	 * Action Event para Validar que los passwords sean identicos y que cumplan con las caracteristicas
	 * @return
	 * @throws ValidationException 
	 */
	public void validaCambioPassword(ActionEvent event){

	}
	
 
	/**
	 * Accion para ingresar a portal
	 * @return
	 */
	public String doLogin() throws ServletException, IOException{
		logger.info("start login");
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/j_spring_security_check");
		
		dispatcher.forward((ServletRequest)context.getRequest(), (ServletResponse)context.getResponse());
		
		FacesContext.getCurrentInstance().responseComplete();
		
		this.setReturnCode(Helper.loginError);
		
		logger.info("Helper login error: " + Helper.loginError + "return Code: " + this.getReturnCode());

		logger.info("end login");
		return null;
	}
	
	/**
	 * Evento para redireccionar a usuario que no se han logueado y quieren acceder a paginas restringidas.
	 * @param cse
	 * @throws IOException 
	 * @throws ServletException 
	 */
    public String logOff() throws ServletException, IOException {
    	
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/j_spring_security_logout");
		
		dispatcher.forward((ServletRequest)context.getRequest(), (ServletResponse)context.getResponse());
		
		FacesContext.getCurrentInstance().responseComplete();
		
		Helper.setLoginError(null);
		
		return null;
    }		
	
    /**
     * Define los mensajes de error de acuerdo a los codigos de Spring Security
     */
    public void validaMensajesError(){
    	try {    		
    		this.setReturnCode(Helper.loginError);
    		
    		logger.info("Helper login error: " + Helper.loginError + "return Code: " + this.getReturnCode());
	    	if(this.getReturnCode() == 1){
	    		FacesContext.getCurrentInstance().addMessage("", 
	    			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", this.getLblMain().getString("errorLoginCredentials")));
	    	} else if(this.getReturnCode() == 2){	    		
	    		FacesContext.getCurrentInstance().addMessage("", 
		    			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", this.getLblMain().getString("errorLoginExpired")));
	    	} else if(this.getReturnCode() == 3){	    		
	    		FacesContext.getCurrentInstance().addMessage("", 
		    			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", this.getLblMain().getString("errorLoginDisabled")));
	    	} else if(this.getReturnCode() == 4){	    		
	    		FacesContext.getCurrentInstance().addMessage("", 
		    			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", this.getLblMain().getString("errorLoginBlocked")));
	    	} else if(this.getReturnCode() == 5){	    		
	    		FacesContext.getCurrentInstance().addMessage("", 
		    			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", this.getLblMain().getString("errorLogin")));
	    	}
	    	
	    	FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("login:valueCode");
	    	logger.info("error: "+this.getReturnCode());
    	} catch(Exception e){
    		
    	}
    }
 
	/**
	 * Do something before rendering phase.
	 */
	public void beforePhase(PhaseEvent event){
		Exception e = (Exception)FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
							.get(WebAttributes.AUTHENTICATION_EXCEPTION);
		
		if(e instanceof BadCredentialsException){
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Username or password not valid","Username or password not valid"));
		}		
	}
	
	
	public PhaseId getPhaseId(){
		return PhaseId.RENDER_RESPONSE;
	}

	/**
	 * Metodo de Login con cambio de password
	 * @return
	 */
	public String ingresarCambioPassword(){
		return "inicio";
	}	
	
	
	/**
	 * Metodo para recuperar password
	 * @param usuario
	 * @param rfc
	 */
	public String recuperaPassword(){
		return "index";
	}
	
}
