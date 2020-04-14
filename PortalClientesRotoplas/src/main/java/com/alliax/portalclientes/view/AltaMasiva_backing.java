/**
 * Backing bean para alta masiva de clientes
 */
package com.alliax.portalclientes.view;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.validation.ValidationException;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.alliax.portalclientes.controller.ConstructEmail;
import com.alliax.portalclientes.controller.InfoClienteRFC;
import com.alliax.portalclientes.domain.RolUsuario;
import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.general.formato.Convertidor;
import com.alliax.portalclientes.general.formato.Fecha;
import com.alliax.portalclientes.general.formato.Generales;
import com.alliax.portalclientes.model.ClienteInfo;
import com.alliax.portalclientes.model.UsuarioCargaMasiva;
import com.alliax.portalclientes.service.UsuarioService;

@ManagedBean(name="altaMasiva")
@ViewScoped
public class AltaMasiva_backing extends AbstractBacking {
	
	private final static Logger logger = Logger.getLogger(AltaMasiva_backing.class);

	//Variables
	private String cargaMasivaBox;
	private boolean processed;
	
	private List<UsuarioCargaMasiva> cargaMasivaLista;

	public String getCargaMasivaBox() {
		return cargaMasivaBox;
	}

	public void setCargaMasivaBox(String cargaMasivaBox) {
		this.cargaMasivaBox = cargaMasivaBox;
	}	

	public List<UsuarioCargaMasiva> getCargaMasivaLista() {
		return cargaMasivaLista;
	}

	public void setCargaMasivaLista(List<UsuarioCargaMasiva> cargaMasivaLista) {
		this.cargaMasivaLista = cargaMasivaLista;
	}
			
	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	/**
	 * Valida archivo de carga de clientes
	 * @param context
	 * @param component
	 * @param value
	 */
	public void validaArchivo(FacesContext context, UIComponent component, Object value){
		try{
			String cargaMasiva = (String)value;
			
			if(cargaMasiva == null)
				throw new Exception(this.getLblMain().getString("errArchivo"));
			
			String[] usuarios = cargaMasiva.split("\\r\\n");
			String[] infoUsr = null;
			
			if(usuarios.length <= 0)
				throw new Exception(this.getLblMain().getString("errArchivo"));
			
			int i = 0;
			while(i < usuarios.length){
				infoUsr = usuarios[i].split(" ");
				if(infoUsr.length > 1)
					throw new Exception(this.getLblMain().getString("arrArchivoInvalido"));
				i++;
			}
		} catch(Exception e){
			throw new ValidatorException(new FacesMessage(
				FacesMessage.SEVERITY_ERROR,"",
					e.getLocalizedMessage()));
		}
	}

	/**
	 * Metodo para leer archivo y precargar datos
	 * @return
	 */
	public String leeArchivo(){
		try{
			List<UsuarioCargaMasiva> listaUsuarios = new ArrayList<UsuarioCargaMasiva>();
			UsuarioCargaMasiva usrMasivo = null;
			Usuario usuario = null;
			RolUsuario rolUsr = null;

			//Pwd encoder
			BCryptPasswordEncoder encoder = this.getSpringContext().getBean("encoder",BCryptPasswordEncoder.class);
						
			InfoClienteRFC infoClienteRfc = this.getSpringContext().getBean("infoClienteRfc",InfoClienteRFC.class);
			ClienteInfo clienteInfo = null;			
			
			String[] usuarios = this.getCargaMasivaBox().split("\\r\\n");
			int i = 0;
			while(i < usuarios.length){
				try{
					usrMasivo = new UsuarioCargaMasiva();
					
					//Get customer info
					clienteInfo = infoClienteRfc.obtieneInfoCliente(Convertidor.remueveCerosIzq(usuarios[i]));
					
					usuario = new Usuario();
					usuario.setUsuario(Convertidor.remueveCerosIzq(usuarios[i]));
					usuario.setNoCliente(usuario.getUsuario());
					usuario.setPasswordStr(Generales.generaPassword());
					usuario.setPassword(encoder.encode(usuario.getPasswordStr()));
					usuario.setFechaEntrada(null);
					usuario.setIntentosFallidos(0);
					usuario.setFechaAlta(Fecha.getDateTimeActual());
					usuario.setEstatus("I");
					usuario.setPais(clienteInfo.getPais());
															

					if(clienteInfo != null && !clienteInfo.getEmail().trim().equals(""))
						usuario.setEmail(clienteInfo.getEmail());
					else 
						usrMasivo.setMensaje(this.getLblMain().getString("errNoMail"));
					
					rolUsr = new RolUsuario();
					rolUsr.setRol("ROLE_CLIENT_MEMBER");
					usuario.setRol(rolUsr);
					
					usrMasivo.setUsuario(usuario);
					usrMasivo.setClienteInfo(clienteInfo);
					
					listaUsuarios.add(usrMasivo);										
				} catch(Exception e){
					logger.error("Error al procesar informacion del cliente " + e.getLocalizedMessage(),e);
				} finally {
					i++;
				}
			}
			
			this.setCargaMasivaLista(listaUsuarios);
			
		} catch(Exception e){
			logger.error("Error al procesar altas masivas " + e.getLocalizedMessage(),e);
		}
		
		return "";
	}
	
	
	/**
	 * Metodo para cargar usuarios masivamente
	 * @return
	 */
	public String creaUsuariosCliente(){
		try {
			//Marca como procesada}
			this.setProcessed(true);
			
			//Graba Usuario
			UsuarioService usuarioService = this.getSpringContext().getBean("usuarioService",UsuarioService.class);			
			
			//Mail
			ConstructEmail mail = this.getSpringContext().getBean("constructEmail",ConstructEmail.class);
			
			UsuarioCargaMasiva usrMasivo = null;
			
			List<Usuario> repetidos = null;
			
			int i = 0;
			while(i < this.getCargaMasivaLista().size()){
				try {
					usrMasivo = this.getCargaMasivaLista().get(i);
					
					logger.info("Procesando Cliente " + usrMasivo.getUsuario().getNoCliente());
					
					if(usrMasivo.getMensaje() != null){
						logger.error("Cliente con mensaje, no se procesa");
						continue;
 					}
										
					//Valida usuario repetido
					repetidos = null;
					repetidos =  usuarioService.findByClientNumber(usrMasivo.getUsuario().getNoCliente());
					if(repetidos!= null && repetidos.size() > 0){
						logger.error("Cliente Existe " + usrMasivo.getUsuario().getNoCliente());											
						usrMasivo.setMensaje(new MessageFormat(this.getLblMain().getString("errUsuarioExiste")).format(new Object[] {usrMasivo.getUsuario().getNoCliente()}));
						continue;
					}
					
					//Graba Informacion
					usrMasivo.setUsuario(
						usuarioService.save(usrMasivo.getUsuario()));
					
					//envia mail
					mail.enviaCorreoAlta(usrMasivo.getUsuario(), usrMasivo.getClienteInfo());
					
					//Msg
					usrMasivo.setMensaje(this.getLblMain().getString("creaUsuarioOK"));
				} catch(Exception e){
					logger.info("Error al procesar cliente " + e.getLocalizedMessage(),e);
				} finally {
					i++;
				}
			}
		} catch(Exception e){
			logger.error("Error al procesar listado de altas masivas " + e.getLocalizedMessage(),e);
		}
		return "";
	}
	
}
 