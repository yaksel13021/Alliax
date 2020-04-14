/**
 * Backing bean para alta masiva de clientes
 */
package com.alliax.portalclientes.view;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;

import com.alliax.portalclientes.controller.InfoClienteRFC;
import com.alliax.portalclientes.domain.ClienteVendedor;
import com.alliax.portalclientes.domain.RolUsuario;
import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.general.formato.Convertidor;
import com.alliax.portalclientes.model.ClienteInfo;
import com.alliax.portalclientes.model.ClienteVendedorCargaMasiva;
import com.alliax.portalclientes.service.ClienteVendedorService;
import com.alliax.portalclientes.service.UsuarioService;

@ManagedBean(name="altaClienteVendedor")
@ViewScoped
public class AltaClienteVendedor_backing extends AbstractBacking {
	
	private final static Logger logger = Logger.getLogger(AltaClienteVendedor_backing.class);

	//Variables
	private String cargaMasivaBox;
	private boolean processed;
	
	private List<ClienteVendedorCargaMasiva> cargaMasivaLista;
	private List<ClienteVendedor> list;
    private String noCliente;
    private String vendedor;
    private String mensaje;
    
        
    private void reset(){    	
    	mensaje = null;
    	setList(null);
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
			List<ClienteVendedorCargaMasiva> listaClienteVendedor = new ArrayList<ClienteVendedorCargaMasiva>();
			ClienteVendedorCargaMasiva usrMasivo = null;			 
			RolUsuario rolUsr = null;

			//Pwd encoder
//			BCryptPasswordEncoder encoder = this.getSpringContext().getBean("encoder",BCryptPasswordEncoder.class);
						
			InfoClienteRFC infoClienteRfc = this.getSpringContext().getBean("infoClienteRfc",InfoClienteRFC.class);
			//Graba Usuario
			UsuarioService usuarioService = this.getSpringContext().getBean("usuarioService",UsuarioService.class);
								
			
			String[] row = this.getCargaMasivaBox().split("\\r\\n");
			int j = 0;
			while(j < row.length){				
				logger.info(row[j]);
				j++;
			}			
			int i = 0;
			while(i < row.length){
				try{
					usrMasivo = new ClienteVendedorCargaMasiva();
					StringTokenizer st = new StringTokenizer(row[i], "\t");  //Horizontal Tab
					String noCliente = st.nextToken();
					String vendedor = st.nextToken();
					
					usrMasivo.setNoCliente((Convertidor.remueveCerosIzq(noCliente)));
					usrMasivo.setVendedor( vendedor );
					//Obtiene vendedor desde la base de datos
					Usuario u = null;
					try{
						u = usuarioService.findByUserName(vendedor);
						boolean isUsuarioVentas = false;
						for( RolUsuario r : u.getRoles() ){
							if(r.getRol().contains("VENTAS")){
								isUsuarioVentas = true;
								break;
							}
						}
						if(!isUsuarioVentas){
							usrMasivo.setMensaje(this.getLblMain().getString("errUsuarioNoVentas"));							
						}
						logger.info("Usuario From BD:::: " + (u != null ? u.getNoCliente() + "-" + u.getEmail() + "-" + u.getUsuario()  : u) );
					}catch(Exception e){
						logger.error("AltaMasivaClienteVendedor: error al consultar usuario o no existe:");
						usrMasivo.setMensaje( this.getLblMain().getString("errUsuarioNoExiste") );
						i++;
						listaClienteVendedor.add(usrMasivo);
						continue;
					}
										
					//Get customer info
					ClienteInfo clienteInfo = infoClienteRfc.obtieneInfoCliente( noCliente );
				     logger.info("Cliente From SAP:::: " + clienteInfo != null ? clienteInfo.getNombre() : clienteInfo);				     
					if(clienteInfo != null && u != null && !clienteInfo.getNombre().trim().equals("")){
						usrMasivo.setNombreCliente(clienteInfo.getNombre());
					    usrMasivo.setMensaje("");
					}else{
						usrMasivo.setNombreCliente(clienteInfo != null ? clienteInfo.getNombre() : "");
						if(usrMasivo.getMensaje() == null || usrMasivo.getMensaje().equals("")){							
						}else{
							usrMasivo.setMensaje(this.getLblMain().getString("errClienteNoExiste"));
						}						
					}
					
					listaClienteVendedor.add(usrMasivo);										
				} catch(Exception e){
					logger.error("Error al procesar informacion del cliente " + e.getLocalizedMessage(),e);
				} finally {
					i++;
				}
			}
			
			this.setCargaMasivaLista(listaClienteVendedor);
			
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
//			UsuarioService usuarioService = this.getSpringContext().getBean("usuarioService",UsuarioService.class);	
			ClienteVendedorService clienteVendedorService = this.getSpringContext().getBean("clienteVendedorService",ClienteVendedorService.class);			
			//Mail
//			ConstructEmail mail = this.getSpringContext().getBean("constructEmail",ConstructEmail.class);
			
			ClienteVendedorCargaMasiva usrMasivo = null;			
			int i = 0;
			while(i < this.getCargaMasivaLista().size()){
				try {
					usrMasivo = this.getCargaMasivaLista().get(i);
					
					logger.info("Procesando Cliente-Vendedor " + usrMasivo.getNoCliente() + " _ " + usrMasivo.getVendedor());
					
					if(usrMasivo.getMensaje() != null && !usrMasivo.getMensaje().equals("")){
						logger.error("Cliente con mensaje, no se procesa");
						continue;
 					}
															
					//Graba Informacion
					ClienteVendedor clienteVendedor = new ClienteVendedor();
					 clienteVendedor.setNoCliente( usrMasivo.getNoCliente() );
					 clienteVendedor.setVendedor( usrMasivo.getVendedor() );
					clienteVendedorService.save(clienteVendedor);

					
					//envia mail
//					mail.enviaCorreoAlta(usrMasivo.getUsuario(), usrMasivo.getClienteInfo());
					
					//Msg
					usrMasivo.setMensaje(this.getLblMain().getString("creaClienteVendedorOK"));
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
	
	
	public void guardaClienteVendedor(){
		this.reset();
		try{
			ClienteVendedorService clienteVendedorService = this.getSpringContext().getBean("clienteVendedorService",ClienteVendedorService.class);
			InfoClienteRFC infoClienteRfc = this.getSpringContext().getBean("infoClienteRfc",InfoClienteRFC.class);			
			UsuarioService usuarioService = this.getSpringContext().getBean("usuarioService",UsuarioService.class);
			
			Usuario u = usuarioService.findByUserName(this.vendedor);
			if(u == null ){
				this.setMensaje( this.getLblMain().getString("errUsuarioNoExiste") );
				return;
			}
			//Get customer info
			ClienteInfo clienteInfo = infoClienteRfc.obtieneInfoCliente( noCliente );
		     logger.info("Cliente From SAP:::: " + clienteInfo != null ? clienteInfo.getNombre() : clienteInfo);				     
			if(clienteInfo != null && u != null && !clienteInfo.getNombre().trim().equals("")){
							    
			}else{				
				this.setMensaje(this.getLblMain().getString("errClienteNoExiste"));
				return;
			}
			
			
			if(this.noCliente != null && this.vendedor != null){
				ClienteVendedor cv = new ClienteVendedor();
				 cv.setNoCliente(this.noCliente);
				 cv.setVendedor(this.vendedor);
				clienteVendedorService.save(cv);			
				this.mensaje = this.getLblMain().getString("msgActualizaInfo");
				this.noCliente = "";
				this.vendedor = "";
			}else{
				//Campos requeridos
			}
		}catch(Exception e){
			logger.info("Error al guardar Cliente-Vendedor", e);
			this.mensaje = e.getMessage();
		}
	}
	
	public void listaClienteVendedor(){
		this.reset();
		ClienteVendedorService clienteVendedorService = this.getSpringContext().getBean("clienteVendedorService",ClienteVendedorService.class);
		 logger.info("metodo listaClienteVendedor");
		if(this.vendedor != null && !this.vendedor.isEmpty()){
			logger.info("campo vendedor: " + this.vendedor);
			this.setList(clienteVendedorService.findByNoClienteList(vendedor));
		}else if(this.noCliente != null && !this.noCliente.isEmpty() ){
			logger.info("campo cliente: " + this.noCliente);			
			this.setList( clienteVendedorService.findByNoCliente(this.noCliente) );
		}else{
			logger.info("findAll ClienteVendedor");
			this.setList(clienteVendedorService.findAll());
		}	
	}
	
	
	public void eliminaClienteVendedor(String noCliente, String vendedor){
		logger.info("eliminaClienteVendedor method " + noCliente + " - " + vendedor);
		ClienteVendedorService clienteVendedorService = this.getSpringContext().getBean("clienteVendedorService",ClienteVendedorService.class);
		clienteVendedorService.delete(noCliente, vendedor);
		this.listaClienteVendedor();
	}
		
	
	
	public String getCargaMasivaBox() {
		return cargaMasivaBox;
	}

	public void setCargaMasivaBox(String cargaMasivaBox) {
		this.cargaMasivaBox = cargaMasivaBox;
	}	

	public List<ClienteVendedorCargaMasiva> getCargaMasivaLista() {
		return cargaMasivaLista;
	}

	public void setCargaMasivaLista(List<ClienteVendedorCargaMasiva> cargaMasivaLista) {
		this.cargaMasivaLista = cargaMasivaLista;
	}
			
	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public String getNoCliente() {
		return noCliente;
	}

	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<ClienteVendedor> getList() {
		return list;
	}

	public void setList(List<ClienteVendedor> list) {
		this.list = list;
	}
	
}
 