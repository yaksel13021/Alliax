/**
 * @author saul.ibarra
 * @fecha 29-Feb-2016
 */
package com.alliax.portalclientes.view;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.alliax.portalclientes.controller.ConstructEmail;
import com.alliax.portalclientes.controller.InfoClienteRFC;
import com.alliax.portalclientes.domain.RolUsuario;
import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.general.formato.Convertidor;
import com.alliax.portalclientes.general.formato.Fecha;
import com.alliax.portalclientes.general.formato.Generales;
import com.alliax.portalclientes.model.ClienteInfo;
import com.alliax.portalclientes.service.UsuarioService;
import com.alliax.portalclientes.util.Helper;

@ManagedBean(name="altaCliente")
@ViewScoped
public class AltaCliente_backing extends AbstractBacking {
	
	private final static Logger logger = Logger.getLogger(AltaCliente_backing.class);

	private String noCliente;
	private ClienteInfo clienteInfo;
	private String rol;
	private Set<String> rolesSet;
	private Map<String,String> rolesMap;
	
	
//	@ManagedProperty(value="#{usrRolesMap}")
	private Map<String,String> usrRolesMap;	
	

	public String getNoCliente() {
		return noCliente;
	}

	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}

	public ClienteInfo getClienteInfo() {
		return clienteInfo;
	}

	public void setClienteInfo(ClienteInfo clienteInfo) {
		this.clienteInfo = clienteInfo;
	}	
	
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public Set<String> getRolesSet() {
		return rolesSet;
	}

	public void setRolesSet(Set<String> rolesSet) {
		this.rolesSet = rolesSet;
	}

	public Map<String, String> getRolesMap() {
		return rolesMap;
	}
	
	public Object[] getRolesMapArray(){
		try{
			return this.getRolesMap().entrySet().toArray();
		} catch(Exception e){
			return null;
		}
	}

	public void setRolesMap(Map<String, String> rolesMap) {
		this.rolesMap = rolesMap;
	}

	public Map<String, String> getUsrRolesMap() {
		setUsrRolesMap(Helper.getUsrRolesMap(this.getLblMain()));
		return usrRolesMap;
	}
	
	public Set<Entry<String,String>> getUsrRolesSet(){
		return getUsrRolesMap().entrySet();
	}

	public void setUsrRolesMap(Map<String, String> usrRolesMap) {
		this.usrRolesMap = usrRolesMap;
	}
	

	/**
	 * Obtiene la informacion del cliente
	 * @return
	 */
	public String cargaInfoCliente(){
		try {
			InfoClienteRFC infoClienteRfc = this.getSpringContext().getBean("infoClienteRfc",InfoClienteRFC.class);
		
			this.setClienteInfo(infoClienteRfc.obtieneInfoCliente(this.getNoCliente()));
		
		} catch(Exception e){
			logger.error("Error al desplegar información del cliente. " + e.getLocalizedMessage());
			this.getFacesContext().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,"Error","Error al desplegar información del cliente."));				
		}
		return "";
	}
	
	/**
	 * Validador de No. de Cliente
	 * @param context
	 * @param component
	 * @param value
	 */
	public void validaNoCliente(FacesContext context, UIComponent component, Object value){
		try{
			String noCliente = (String)value;
			
			if(noCliente == null || (noCliente != null && noCliente.trim().equals("")))
				throw new Exception("No. cliente es invalido");
			
		} catch(Exception e){
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"",
							(new MessageFormat(this.getLblMain().getString("msgRequerido"))).format(
								new Object[] {this.getLblMain().getString("numeroCliente")})));			
		}
	}
	
	
	/**
	 * Graba usuario en Base de datos
	 * @return
	 */
	public String creaUsuarioCliente(){
		try{	
			
			if(this.validaInfoNuevoUsuario()){
				
				//Pwd encoder
				BCryptPasswordEncoder encoder = this.getSpringContext().getBean("encoder",BCryptPasswordEncoder.class);
				
				Usuario usuario = new Usuario();
				usuario.setUsuario(Convertidor.remueveCerosIzq(this.getClienteInfo().getNoCliente()));
				usuario.setPasswordStr(Generales.generaPassword()); //Password sin codificar para correo.
				usuario.setPassword(encoder.encode(usuario.getPasswordStr()));
				usuario.setNoCliente(this.getClienteInfo().getNoCliente());
				usuario.setEmail(this.getClienteInfo().getEmail());
				usuario.setFechaEntrada(null);
				usuario.setIntentosFallidos(0);
				usuario.setFechaAlta(Fecha.getDateTimeActual());
				usuario.setEstatus("I");
				usuario.setPais(this.getClienteInfo().getPais());
							
				RolUsuario rolUsr = new RolUsuario();
				
				/*** Setea roles ***
				for(String rol : this.getRolesSet()){
					rolUsr = new RolUsuario();					
					rolUsr.setRol(rol);
					usuario.setRol(rolUsr);
				} ****/
				
				
				rolUsr = new RolUsuario();
				rolUsr.setRol("ROLE_CLIENT_MEMBER");
				usuario.setRol(rolUsr);
								
				
				logger.info("Password " + usuario.getPasswordStr());
				
				//Graba Usuario
				UsuarioService usuarioService = this.getSpringContext().getBean("usuarioService",UsuarioService.class);
				
				//Valida usuario repetido
				List<Usuario> resultadoBusqueda = usuarioService.findByClientNumber(this.getClienteInfo().getNoCliente());
				if(resultadoBusqueda != null && resultadoBusqueda.size()  > 0){
					throw new Exception(new MessageFormat(this.getLblMain().getString("errUsuarioExiste")).format(
							new Object[] {this.getClienteInfo().getNoCliente()}));
				}
				
				usuario = usuarioService.save(usuario);
				
				//envia mail
				ConstructEmail mail = this.getSpringContext().getBean("constructEmail",ConstructEmail.class);
				mail.enviaCorreoAlta(usuario,this.getClienteInfo());				
								
				this.getFacesContext().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO,"Info",this.getLblMain().getString("creaUsuarioOK")));
				
				//Muestra el password...
				/*this.getFacesContext().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,"Error","Password: " + usuario.getPasswordStr()));*/
			} else 
				throw new Exception(this.getLblMain().getString("errCreaUsuarioDatosInv"));
			
		} catch(Exception e){
			logger.error("Error al grabar usuario " + e.getLocalizedMessage(),e);
			this.getFacesContext().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,"Error",e.getLocalizedMessage()));
		}
		return "";
	}
	
	/**
	 * Valida que el cliente tenga los datos necesarios
	 * @return
	 * @throws Exception
	 */
	private boolean validaInfoNuevoUsuario() throws Exception{
		try{
			boolean canCreate = true;
			if(this.getClienteInfo().getNoCliente() == null || 
				(this.getClienteInfo().getNoCliente() != null && this.getClienteInfo().getNoCliente().trim().equals(""))){
				canCreate = false;
			}
			
			if(this.getClienteInfo().getEmail() == null || 
				(this.getClienteInfo().getEmail() != null && this.getClienteInfo().getEmail().trim().equals(""))){
					canCreate = false;
					this.getFacesContext().addMessage(null, 
							new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
											this.getLblMain().getString("errCreaUsuarioDatosInv")));						
			}
			
			if(this.getUsrRolesSet().size() <= 0){
				canCreate = false;
				this.getFacesContext().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
										this.getLblMain().getString("errCreaRolReq")));		
			}
			
			return canCreate;
			
		} catch(Exception e){
			logger.error("Error al crear usuario, no tiene todos los datos obligatorios " + e.getLocalizedMessage(),e);
			throw new Exception("Error al crear usuario, no tiene todos los datos obligatorios");
		}
	}
	
	/**
	 * Action para agregar roles de nuevos usuarios
	 */
	public void agregaRol(){
		try{
			if(this.getRolesSet() == null)
				this.setRolesSet(new HashSet<String>());
			
			if(this.getRolesMap() == null)
				this.setRolesMap(new HashMap<String,String>());
			
			if(this.getRolesSet().add(this.getRol()))
				this.getRolesMap().put(this.getRol(), this.descripcionRol(this.getRol()));
			
			this.setRol(null);
		} catch(Exception e){
			logger.error("Error al agregar rol " + e.getLocalizedMessage(),e);
		}
	}
	
	public String descripcionRol(String rol){
		return this.getUsrRolesMap().get(rol).toString();
	}
	
	public void asignaPaisAClientes(){
		logger.info("asignaPaisAClientes()");
		UsuarioService usuarioService = this.getSpringContext().getBean("usuarioService",UsuarioService.class);
		InfoClienteRFC infoClienteRfc = this.getSpringContext().getBean("infoClienteRfc",InfoClienteRFC.class);
		logger.info("comenzará el la lista");
		for( Usuario u : usuarioService.findAll()){
			try{
				ClienteInfo  ci = infoClienteRfc.obtieneInfoCliente(u.getNoCliente());
				logger.info("NoCliente: " + ci.getNoCliente() + " - Pais: " + ci.getPais());
				if(ci != null && ci.getPais() != null && !ci.getPais().isEmpty()){
					u.setPais( ci.getPais() );
					usuarioService.save(u);
					logger.info("saved");
				}
			}catch(Exception e){
				logger.error("error", e);
			}
			
		}
	}
}
