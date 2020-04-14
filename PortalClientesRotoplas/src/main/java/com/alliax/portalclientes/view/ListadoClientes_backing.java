/**
 * Backing bean para busqueda de clientes registrados.
 * @author saul.ibarra
 * @fecha 20-Mar-2016
 */
package com.alliax.portalclientes.view;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

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

@ManagedBean(name="listaClientes")
@ViewScoped
public class ListadoClientes_backing extends AbstractBacking {
	
	private final static Logger logger = Logger.getLogger(ListadoClientes_backing.class);

	private String noCliente;
	
	private List<Usuario> resultados;
	
	private Usuario usuarioSel;
	private ClienteInfo clienteInfo;
	
//	@ManagedProperty(value="#{usrEstatusMap}")
	private Map<String,String> usrEstatusMap;
	
//	@ManagedProperty(value="#{usrRolesMap}")
	private Map<String,String> usrRolesMap;
	

	public String getNoCliente() {
		return noCliente;
	}

	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}	

	public Usuario getUsuarioSel() {
		return usuarioSel;
	}

	public void setUsuarioSel(Usuario usuarioSel) {
		this.usuarioSel = usuarioSel;
	}

	public ClienteInfo getClienteInfo() {
		return clienteInfo;
	}

	public void setClienteInfo(ClienteInfo clienteInfo) {
		this.clienteInfo = clienteInfo;
	}

	public List<Usuario> getResultados() {
		return resultados;
	}

	public void setResultados(List<Usuario> resultados) {
		this.resultados = resultados;
	}

	public Map<String, String> getUsrEstatusMap() {
		setUsrEstatusMap(Helper.getUsrEstatusMap(this.getLblMain()));
		return usrEstatusMap;
	}

	public void setUsrEstatusMap(Map<String, String> usrEstatusMap) {
		this.usrEstatusMap = usrEstatusMap;
	}
	
	public Map<String, String> getUsrRolesMap() {
		this.setUsrRolesMap(Helper.getUsrRolesMap(this.getLblMain()));
		return usrRolesMap;
	}

	public void setUsrRolesMap(Map<String, String> usrRolesMap) {
		this.usrRolesMap = usrRolesMap;
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
				throw new Exception(new MessageFormat(this.getLblMain().getString("msgRequerido")).format(
						new Object[] {this.getLblMain().getString("numeroCliente")}));
			
			if(noCliente.trim().length() < 4)
				throw new Exception(this.getLblMain().getString("msgNoClientErr"));
			
		} catch(Exception e){
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"",
							e.getLocalizedMessage()));			
		}
	}
	
	
	/**
	 * Graba usuario en Base de datos
	 * @return
	 */
	public String buscaCliente(){
		try{	
			UsuarioService userServ = this.getSpringContext().getBean("usuarioService", UsuarioService.class);
			
			this.setResultados(userServ.findByClientNumber(Convertidor.remueveCerosIzq(this.getNoCliente().trim())));
			
			//Borra valores
			this.setUsuarioSel(null);
			this.setClienteInfo(null);
				
		} catch(Exception e){
			logger.error("Error al buscar usuarios " + e.getLocalizedMessage(),e);
			this.getFacesContext().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,"Error",e.getLocalizedMessage()));
		}
		return "";
	}
	
	/**
	 * Obtiene la informacion del cliente
	 * @return
	 */
	public String cargaInfoCliente(){
		try {
			InfoClienteRFC infoClienteRfc = this.getSpringContext().getBean("infoClienteRfc",InfoClienteRFC.class);
		
			this.setUsuarioSel((Usuario)this.getFlash().get("usuarioSel"));
			
			 this.setClienteInfo(infoClienteRfc.obtieneInfoCliente(
					this.getUsuarioSel().getNoCliente()));
		
		} catch(Exception e){
			logger.error("Error al desplegar informaci�n del cliente. " + e.getLocalizedMessage());
			this.getFacesContext().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,"Error",this.getLblMain().getString("errInfoCliente")));				
		}
		return "";
	}
	
	
	/**
	 * Bloquea el usuario seleccionado.
	 * @return
	 */	
	public String bloqueaUsuario(){
		try {					
			this.getUsuarioSel().setEstatus("B");
			this.actualizaUsuario();
		} catch(Exception e){
			logger.error("Error al bloquear usuario " + e.getLocalizedMessage());
			this.getFacesContext().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,"Error",this.getLblMain().getString("errActualizaUsr")));			
		}
		
		return "";
	}	
	
	/**
	 * desbloquea el usuario seleccionado.
	 * @return
	 */	
	public String desbloqueaUsuario(){
		try {					
			this.getUsuarioSel().setEstatus("I");
			this.actualizaUsuario();
		} catch(Exception e){
			logger.error("Error al bloquear usuario " + e.getLocalizedMessage());
			this.getFacesContext().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,"Error",this.getLblMain().getString("errActualizaUsr")));			
		}
		
		return "";
	}		
	
	/**
	 * Actualiza el usuario seleccionado.
	 * @return
	 */
	private void actualizaUsuario(){
		UsuarioService userServ = this.getSpringContext().getBean("usuarioService", UsuarioService.class);						
		userServ.save(this.getUsuarioSel());
	}
	
	
	
	public String descripcionEstatus(String idEstatus){
		return this.getUsrEstatusMap().get(idEstatus).toString();
	}
	
	public String descripcionRol(String rol){
		return this.getUsrRolesMap().get(rol).toString();
	}
	
}
