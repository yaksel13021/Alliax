/**
 * Backing bean para recuperar password
 * @author saul.ibarra
 * @fecha 28-Marzo-2016
 * 
 */
package com.alliax.portalclientes.view;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import com.alliax.portalclientes.controller.ConstructEmail;
import com.alliax.portalclientes.controller.InfoClienteRFC;
import com.alliax.portalclientes.domain.RolUsuario;
import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.general.formato.Generales;
import com.alliax.portalclientes.model.ClienteInfo;
import com.alliax.portalclientes.service.UsuarioService;

@ManagedBean(name="recuperaPwd")
@RequestScoped
public class RecuperaPwd_backing extends AbstractBackingGen {
	
	private final static Logger logger = Logger.getLogger(RecuperaPwd_backing.class);
	
	private String usuario;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	public String recuperaPassword(){
		try {
			logger.info("Recuperando password Usuario:" + this.getUsuario());
			
			UsuarioService usrServ = this.getSpringContext().getBean("usuarioService",UsuarioService.class);
			Usuario usuario = usrServ.findByUserName(this.getUsuario());
			
			if(usuario != null){
				if(usuario.getEstatus().equalsIgnoreCase("B") || usuario.getEstatus().equalsIgnoreCase("E"))
					this.getFacesContext().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
							this.getLblMain().getString("errUsuarioBloqueado")));
				else{
					//Extrae correo del cliente.
					InfoClienteRFC infoClienteRfc = this.getSpringContext().getBean("infoClienteRfc",InfoClienteRFC.class);					
					ClienteInfo clienteInfo = infoClienteRfc.obtieneInfoCliente(usuario.getNoCliente());					
										
					//Valida correo
					if(StringUtils.isEmpty(clienteInfo.getEmail())){
						logger.info("No hay correo en SAP.");
						//Valida si es admin..
						boolean isCliente = false;
						for(RolUsuario rol : usuario.getRoles()){
							if(rol.getRol().equals("ROLE_CLIENT_MEMBER"))
								isCliente = true;
						}
						
						//Si no es admin y no tiene correo manda excepcion
						if(isCliente){
							logger.info("No es admin ni de ventas");
							this.getFacesContext().addMessage(null, 
									new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
											this.getLblMain().getString("errNoMail")));
							
							throw new Exception(this.getLblMain().getString("errNoMail"));
						}
					} else {
						//Actualiza Mail desde SAP
						usuario.setEmail(clienteInfo.getEmail());						
					}
					
					//Pwd encoder
					BCryptPasswordEncoder encoder = this.getSpringContext().getBean("encoder",BCryptPasswordEncoder.class);					
										
					usuario.setEstatus("I");
					
					//Genera Pwd Aleatorio
					String pwdStr = Generales.generaPassword();
					usuario.setPasswordStr(pwdStr);
					usuario.setPassword(encoder.encode(usuario.getPasswordStr()));
					
					//Graba Datos
					usuario = usrServ.save(usuario);
					
					//Setea nuevamente el password.
					usuario.setPasswordStr(pwdStr);
					
					logger.info("Usuario: " + usuario.getUsuario());
					logger.info("Password: " + usuario.getPasswordStr());
					
					//Envia correo 
					ConstructEmail mail = this.getSpringContext().getBean("constructEmail",ConstructEmail.class);
					mail.enviaCorreoReseteo(usuario, clienteInfo);					
						
					this.getFacesContext().addMessage(null, 
							new FacesMessage(FacesMessage.SEVERITY_INFO,"Info",
									this.getLblMain().getString("recuperaPwdOk")));					
				}
				
			} else {
				this.getFacesContext().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
							this.getLblMain().getString("errUsuarioInvalido")));
			}
		} catch(Exception e){
			logger.error("Error al recuperar password " + e.getLocalizedMessage(),e);
			this.getFacesContext().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
							this.getLblMain().getString("errRecuperaPwd")));
		}
		
		return "";
	}	
}
