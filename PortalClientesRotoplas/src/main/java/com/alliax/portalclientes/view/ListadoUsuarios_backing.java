/**
 * Backing bean para listado y alta de Usuarios
 * @author saul.ibarra
 * @fecha 1-Dic-2016
 */
package com.alliax.portalclientes.view;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.alliax.portalclientes.controller.ConstructEmail;
import com.alliax.portalclientes.controller.InfoClienteRFC;
import com.alliax.portalclientes.domain.RolUsuario;
import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.general.formato.Fecha;
import com.alliax.portalclientes.general.formato.Generales;
import com.alliax.portalclientes.model.ClienteInfo;
import com.alliax.portalclientes.service.RolUsuarioService;
//import com.alliax.portalclientes.service.RolUsuarioService;
import com.alliax.portalclientes.service.UsuarioService;
import com.alliax.portalclientes.util.Helper;

@ManagedBean(name="listaUsuarios")
@ViewScoped
public class ListadoUsuarios_backing extends AbstractBackingGen {
	
	private Logger logger = Logger.getLogger(ListadoUsuarios_backing.class);
	
	private Usuario usuario;
	private Usuario usuarioSel; //Para edicion
	private UsuarioService usuarioService;	
	private RolUsuarioService rolService;
	private ConstructEmail constructEmail;
	private List<Usuario> resultados;
	
	private Properties properties;
	
	private String txtUsuario;
	private boolean usuarioNuevo;
	
//	@ManagedProperty(value="#{usrRolesMap}")
	private Map<String,String> rolesCat;
	private String rolTemp;
	
//	@ManagedProperty(value="#{usrEstatusMap}")
	private Map<String,String> catalogoEstatus;
	
	
	public List<Usuario> getResultados() {
		return resultados;
	}

	public void setResultados(List<Usuario> resultados) {
		this.resultados = resultados;
	}

	public String getTxtUsuario() {
		return txtUsuario;
	}

	public void setTxtUsuario(String txtUsuario) {
		this.txtUsuario = txtUsuario;
	}
	
	public boolean isUsuarioNuevo() {
		return usuarioNuevo;
	}

	public void setUsuarioNuevo(boolean usuarioNuevo) {
		this.usuarioNuevo = usuarioNuevo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Usuario getUsuarioSel() {
		return usuarioSel;
	}

	public void setUsuarioSel(Usuario usuarioSel) {
		this.usuarioSel = usuarioSel;
	}
	
	public Map<String, String> getCatalogoEstatus() {
		setCatalogoEstatus(Helper.getUsrEstatusMap(this.getLblMain()));
		return catalogoEstatus;
	}

	public Set<Entry<String, String>> getCatalogoEstatusSet() {
		return getCatalogoEstatus().entrySet();
	}	
	
	public void setCatalogoEstatus(Map<String, String> catalogoEstatus) {
		this.catalogoEstatus = catalogoEstatus;
	}	
	
	public Map<String, String> getRolesCat() {
		setRolesCat(Helper.getUsrRolesMap(this.getLblMain()));
		return rolesCat;
	}
	
	public Set<Entry<String, String>> getRolesCatSet(){
		//Obtener roles según país al que pertenece el administrador
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String,String> rolesToShow = new HashMap<String,String>();
		return Helper.getRolesPais(authentication, rolesToShow,getRolesCat());
	}

	public void setRolesCat(Map<String, String> rolesCat) {
		this.rolesCat = rolesCat;
	}

	public String getRolTemp() {
		return rolTemp;
	}

	public void setRolTemp(String rolTemp) {
		this.rolTemp = rolTemp;
	}

	@PostConstruct
	public void init(){
		InputStream input = null;
		try{
			
//			this.rolUsuarioService = this.getSpringContext().getBean("rolUsuarioService",RolUsuarioService.class);	
			this.usuarioService = this.getSpringContext().getBean("usuarioService", UsuarioService.class);
//			this.setResultados(this.usuarioService.findByBdAdminIn(bdlist));
			
		} catch(Exception e){
			logger.error("Error al obtener usuarios. " + e.getLocalizedMessage(),e);
		}
	}

	public String cargaInfoUsuario(){
		this.setUsuario((Usuario)this.getFlash().get("usuario"));
		return "";
	}
	
	/**
	 * Busqueda de usuario 
	 * @return
	 */
	public String busquedaUsuario(){
		try{
			this.setUsuario(
				this.usuarioService.findByUserName(this.getTxtUsuario()));
						
		} catch(Exception e){
			logger.error("Error al buscar usuario " + e.getLocalizedMessage(),e);
		}
		return "";
	}
	
	/**
	 * Validaciones para evitar que se dupliquen usuarios
	 */
	public void validaUsuario(){
		try{
			if(this.usuarioService == null)
				this.usuarioService = this.getSpringContext().getBean("usuarioService",UsuarioService.class);			

			if(this.isUsuarioNuevo()){

				Usuario usrValida = this.usuarioService.findByUserName(this.usuarioSel.getUsuario());
				if(usrValida != null){
					this.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"",
							new MessageFormat(this.getLblMain().getString("errCreaUsuario")).format(
								new Object[] {this.getLblMain().getString("usrLogin"),this.getUsuarioSel().getUsuario()})));
					
					throw new AbortProcessingException();
//				} else {
//					usrValida = this.usuarioService.findbyemail(this.usuarioSel.getEmail());
//					if(usrValida != null){
//						this.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"",
//								new MessageFormat(this.getLblMain().getString("errCreaUsuario")).format(
//									new Object[] {this.getLblMain().getString("email"),this.getUsuarioSel().getEmail()})));
//						
//						throw new AbortProcessingException();
//					}
				}
				
				//Pwd encoder
				BCryptPasswordEncoder encoder = this.getSpringContext().getBean("encoder",BCryptPasswordEncoder.class);
											
				//Genera password aleatorio
				this.usuarioSel.setPasswordStr(Generales.generaPassword());
				this.usuarioSel.setPassword(encoder.encode(this.usuarioSel.getPasswordStr()));					
				this.usuarioSel.setFechaAlta(Fecha.getDateTimeActual());
				this.usuarioSel.setEstatus("I"); //Password inicial
				
			}
		} catch(AbortProcessingException abe){
			throw new AbortProcessingException(abe);
		}  catch(Exception e){
			logger.error("Errror al validar usuarios " + e.getLocalizedMessage(),e);
		}
	}
	
	
	/**
	 * Graba la informacion del usuario
	 * @return
	 */
	public String onSaveUsuario(){
		try{
			
			Set<RolUsuario> roles = new HashSet<RolUsuario>();
			
			this.rolService = this.getSpringContext().getBean("rolUsuarioService",RolUsuarioService.class);
			
			for(RolUsuario rolUsr : this.getUsuarioSel().getRoles()){
				if(!this.isUsuarioNuevo())
					rolUsr.setIdUsuario(this.getUsuarioSel().getIdUsuario());
												
				if(rolUsr.isBorrado())
					this.rolService.delete(rolUsr);
				else
					roles.add(rolUsr);
			}
			
			//Agrega roles
			this.getUsuarioSel().setRoles(roles);	
			
			
			this.getUsuarioSel().setNoCliente(this.getUsuarioSel().getUsuario());
			//Graba usuario
			this.usuarioService.save(this.getUsuarioSel());
			
			if(this.isUsuarioNuevo()){
				//Manda mail con datos de acceso
				logger.debug("Enviando correo de registro ");
								
				
				if(this.constructEmail == null)
					this.constructEmail = this.getSpringContext().getBean("constructEmail",ConstructEmail.class); 
				
				this.constructEmail.enviaCorreoAlta(this.getUsuarioSel());
				
				this.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"",
						this.getLblMain().getString("msgNuevoUsuario")));				
			} else {
				this.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"",
						this.getLblMain().getString("msgActualizaInfo")));				
			}
			
			this.setUsuarioSel(null);
			this.setUsuarioNuevo(false);
			this.setUsuario(null);
			this.setTxtUsuario("");
			
		} catch(Exception e){
			logger.error("Error al actualizar la informaci�n " + e.getLocalizedMessage(),e);
			this.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"",
					new MessageFormat(this.getLblMain().getString("errActualizaInfo")).format(new Object[] {e.getLocalizedMessage()})));			
		}
		return "";
	}
	
	public String onEditarUsuario(){
		this.setUsuarioNuevo(false);
		this.setUsuarioSel(this.getUsuario());
		return "";
	}
		
	/**
	 * Agrega rol al usuario 
	 * @param event
	 */
	public void onRolAdd(AjaxBehaviorEvent event){
		logger.info("Agregando rol " + this.getRolTemp());
		
		RolUsuario rolnew = new RolUsuario();
		//rolnew.setIdUsuario(this.usuarioSel.getIdUsuario());
		rolnew.setRol(this.getRolTemp());
		
		this.getUsuarioSel().getRoles().add(rolnew);		
	}
	
	public void onRolRemove(AjaxBehaviorEvent event){
		//Obtiene el registro seleccionado de la tabla
		logger.info("Remueve rol de lista de permisos");
		RolUsuario rol = this.getFacesContext().getApplication().evaluateExpressionGet(
	    						this.getFacesContext(), "#{per}", RolUsuario.class);
		
		logger.debug("Removiendo rol " + rol.getRol());
		rol.setBorrado(true);
//		this.getUsuarioSel().getRoles().remove(rol);
//		this.rolUsuarioService.delete(rol);
//		this.getUsuarioSel().setRoles(this.rolUsuarioService.findById(this.getUsuarioSel().getIdUsuario()));
	}
	
	public String onAgregarUsuario(){
		this.setUsuarioNuevo(true);
		this.setUsuarioSel(new Usuario());
		this.setUsuario(null);
		return "";
	}
	
	
	public String onCancelUsuario(){
		this.setUsuarioSel(null);
		this.setUsuarioNuevo(false);
		this.setUsuario(null);
		this.setTxtUsuario("");
		return "";
	}
	
	public String getRolLabel(String rol){
		return this.getRolesCat().get(rol);
	}
	
}
