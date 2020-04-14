package com.alliax.portalclientes.view;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.service.UsuarioService;

@ManagedBean(name="cambiaPwd")
@ViewScoped
public class CambiaPassword_backing extends AbstractBacking {
	
	private final static Logger logger = Logger.getLogger(CambiaPassword_backing.class);
	
	private String passwordActual;
	private String passwordNuevo;
	private String passwordConfirma;
	
	
	public String getPasswordActual() {
		return passwordActual;
	}

	public void setPasswordActual(String passwordActual) {
		this.passwordActual = passwordActual;
	}

	public String getPasswordNuevo() {
		return passwordNuevo;
	}

	public void setPasswordNuevo(String passwordNuevo) {
		this.passwordNuevo = passwordNuevo;
	}

	public String getPasswordConfirma() {
		return passwordConfirma;
	}

	public void setPasswordConfirma(String passwordConfirma) {
		this.passwordConfirma = passwordConfirma;
	}

	/**
	 * Metodo para actualizr password y estatus de un usuario
	 * @return
	 * @throws Exception 
	 */
	public String cambiaPassword() throws Exception{
		try {
			
			UsuarioService usrServ = this.getSpringContext().getBean("usuarioService",UsuarioService.class);
			BCryptPasswordEncoder encoder = this.getSpringContext().getBean("encoder",BCryptPasswordEncoder.class); 
			
			logger.info("Usuario: " + this.getUsuarioLogueado().getUsername());
			logger.info("Pwd: " + this.getPasswordActual());
			logger.info("pwd nuevo " + this.getPasswordNuevo());
			logger.info("pwd sha" + encoder.encode(this.getPasswordActual()));
			
			Usuario usuario = usrServ.findByUserName(this.getUsuarioLogueado().getUsername());
			
			if(usuario != null){
				if(encoder.matches(this.getPasswordActual(), usuario.getPassword())){
					//Actualiza la informacion
					usuario.setPassword(encoder.encode(this.getPasswordNuevo()));
					usuario.setEstatus("A");
					
					//Graba info
					usrServ.save(usuario);
																										
					logger.info("Mensaje Logout");
					
					//Log out message
					this.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Info",
							this.getLblMain().getString("cambiaPwdOk")));
					
					this.salir();
					return "/logOff.xhtml?faces-redirect=true";
//					return "/logOff";
					
				} else{
					usuario = null;
				}
			}
			
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Info",
					this.getLblMain().getString("errorCambiaPwd")));
			
		} catch(Exception e){
			logger.error("Error al obtener la informacion del usuario " + this.getUsuarioLogueado().getUsername(),e);
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Info",
					this.getLblMain().getString("errorCambiaPwd")));
		}
		return "";
	}
}
