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

import com.alliax.portalclientes.controller.ClienteCompanyRFC;
import com.alliax.portalclientes.controller.InfoClienteRFC;
import com.alliax.portalclientes.domain.ClienteVendedor;
import com.alliax.portalclientes.domain.RolUsuario;
import com.alliax.portalclientes.domain.SociedadClienteCorreo;
import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.general.formato.Convertidor;
import com.alliax.portalclientes.model.ClienteCompany;
import com.alliax.portalclientes.model.ClienteInfo;
import com.alliax.portalclientes.model.ClienteVendedorCargaMasiva;
import com.alliax.portalclientes.model.SociedadClienteCorreoCargaMasiva;
import com.alliax.portalclientes.service.ClienteVendedorService;
import com.alliax.portalclientes.service.SociedadClienteCorreoService;
import com.alliax.portalclientes.service.UsuarioService;

@ManagedBean(name="altaSociedadClienteCorreo")
@ViewScoped
public class AltaSociedadClienteCorreo_backing extends AbstractBacking {
	
	private final static Logger logger = Logger.getLogger(AltaSociedadClienteCorreo_backing.class);

	//Variables
	private String cargaMasivaBox;
	private boolean processed;
	
	private List<SociedadClienteCorreoCargaMasiva> cargaMasivaLista;
	private List<SociedadClienteCorreo> list;
    private String sociedad;
    private String noCliente;    
    private String correo;
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
			List<SociedadClienteCorreoCargaMasiva> listaSociedadClienteCorreoCargaMasiva = new ArrayList<SociedadClienteCorreoCargaMasiva>();
			ClienteCompanyRFC clienteCompanyRFC = this.getSpringContext().getBean("clienteCompanyRfc",ClienteCompanyRFC.class);
			
			List<ClienteCompany> res = (List<ClienteCompany>)clienteCompanyRFC.obtieneCompanyCliente(this.noCliente);
			SociedadClienteCorreoCargaMasiva usrMasivo = null;			 
			RolUsuario rolUsr = null;

			//Pwd encoder
//			BCryptPasswordEncoder encoder = this.getSpringContext().getBean("encoder",BCryptPasswordEncoder.class);
						
			InfoClienteRFC infoClienteRfc = this.getSpringContext().getBean("infoClienteRfc",InfoClienteRFC.class);
			//Graba Usuario
//			UsuarioService usuarioService = this.getSpringContext().getBean("usuarioService",UsuarioService.class);
											
			String[] row = this.getCargaMasivaBox().split("\\r\\n");
			int j = 0;
			while(j < row.length){				
				logger.info(row[j]);
				j++;
			}			
			int i = 0;
			while(i < row.length){
				try{
					usrMasivo = new SociedadClienteCorreoCargaMasiva();
					StringTokenizer st = new StringTokenizer(row[i], "\t");  //Horizontal Tab
					String sociedad = st.nextToken();
					String noCliente = st.nextToken();
					String correo = "";
					//Probablemente tenga direccion de correo
					try{ correo = st.nextToken(); }catch(Exception e){}					
					
					usrMasivo.setSociedad(sociedad);
					usrMasivo.setNoCliente((Convertidor.remueveCerosIzq(noCliente)));
					usrMasivo.setCorreo( correo );
					//Obtiene vendedor desde la base de datos
//					Usuario u = null;
//					try{
//						u = usuarioService.findByUserName(vendedor);
//						boolean isUsuarioVentas = false;
//						for( RolUsuario r : u.getRoles() ){
//							if(r.getRol().contains("VENTAS")){
//								isUsuarioVentas = true;
//								break;
//							}
//						}
//						if(!isUsuarioVentas){
//							usrMasivo.setMensaje(this.getLblMain().getString("errUsuarioNoVentas"));							
//						}
//						logger.info("Usuario From BD:::: " + (u != null ? u.getNoCliente() + "-" + u.getEmail() + "-" + u.getUsuario()  : u) );
//					}catch(Exception e){
//						logger.error("AltaMasivaClienteVendedor: error al consultar usuario o no existe:");
//						usrMasivo.setMensaje( this.getLblMain().getString("errUsuarioNoExiste") );
//						i++;
//						listaSociedadClienteCorreoCargaMasiva.add(usrMasivo);
//						continue;
//					}
										
					//Get customer info
					infoClienteRfc.completeEmail = true;
					ClienteInfo clienteInfo = infoClienteRfc.obtieneInfoCliente( noCliente );
				     logger.info("Cliente From SAP:::: " + clienteInfo != null ? clienteInfo.getNombre() : clienteInfo);				     
					if(clienteInfo != null && !clienteInfo.getNombre().trim().equals("")){
						usrMasivo.setNombreCliente(clienteInfo.getNombre());
						if(correo != null && correo.equals("")){ 
							usrMasivo.setCorreo(clienteInfo.getEmail()); 
						}
					    usrMasivo.setMensaje("");					    					   
							//Cliente si existe, buscamos si existe en la sociedad indicada
							logger.info("Lista de sociedades: " + res);
							if(res != null){
							boolean sociedadValida = false;
							for (ClienteCompany cc : res) {
								logger.info("Sociedad: " + cc.getCompanyCode());
								logger.info("Sociedad2: " + this.sociedad);
								if(cc.getCompanyCode().equals(this.sociedad)){	
									logger.info("sociedad encontrada y valida");
									sociedadValida = true;							
								}
							}
							logger.info("Sociedad valida: " + sociedadValida);
							if(!sociedadValida){
								this.setMensaje(this.getLblMain().getString("errSociedadNoEncontradaParaCliente"));								
							}
							
							//Verificar si correo está en blanco, para setear el que viene de SAP
							if(this.correo == null || this.correo.trim().isEmpty()){
								this.setCorreo(clienteInfo.getEmail());
							}
						}else{
							//No se encontró ninguna sociedad
							logger.info("res = null");
							this.setMensaje(this.getLblMain().getString("errSociedadNoEncontradaParaCliente"));							
						}							
					    
					}else{
						usrMasivo.setNombreCliente(clienteInfo != null ? clienteInfo.getNombre() : "");
						if(usrMasivo.getMensaje() == null || usrMasivo.getMensaje().equals("")){							
						}else{
							usrMasivo.setMensaje(this.getLblMain().getString("errClienteNoExiste"));
						}						
					}
					
					listaSociedadClienteCorreoCargaMasiva.add(usrMasivo);										
				} catch(Exception e){
					logger.error("Error al procesar informacion del cliente " + e.getLocalizedMessage(),e);
				} finally {
					i++;
				}
			}
			
			this.setCargaMasivaLista(listaSociedadClienteCorreoCargaMasiva);
			
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
			SociedadClienteCorreoService sociedadClienteCorreoService = this.getSpringContext().getBean("sociedadClienteCorreoService",SociedadClienteCorreoService.class);			
			//Mail
//			ConstructEmail mail = this.getSpringContext().getBean("constructEmail",ConstructEmail.class);
			
			SociedadClienteCorreoCargaMasiva usrMasivo = null;			
			int i = 0;
			while(i < this.getCargaMasivaLista().size()){
				try {
					usrMasivo = this.getCargaMasivaLista().get(i);
					
					logger.info("Procesando Sociedad-Cliente-Correo " + usrMasivo.getSociedad() + " _ " + usrMasivo.getNoCliente() + " _ " + usrMasivo.getCorreo());				
					
					if(usrMasivo.getMensaje() != null && !usrMasivo.getMensaje().equals("")){
						logger.error("Cliente con mensaje, no se procesa");
						continue;
 					}
															
					//Graba Informacion
					SociedadClienteCorreo sociedadClienteCorreo = new SociedadClienteCorreo();
					 sociedadClienteCorreo.setSociedad( usrMasivo.getSociedad() ); 
					 sociedadClienteCorreo.setNoCliente( usrMasivo.getNoCliente() );
					 sociedadClienteCorreo.setNombreCliente( usrMasivo.getNombreCliente() );
					 sociedadClienteCorreo.setCorreo(usrMasivo.getCorreo());					
					sociedadClienteCorreoService.save(sociedadClienteCorreo);

					
					//envia mail
//					mail.enviaCorreoAlta(usrMasivo.getUsuario(), usrMasivo.getClienteInfo());
					
					//Msg
					usrMasivo.setMensaje(this.getLblMain().getString("creaSociedadClienteCorreoOK"));
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
	
	
	public void guardaSociedadClienteCorreo(){
		this.reset();
		try{
			SociedadClienteCorreoService sociedadClienteCorreoService = this.getSpringContext().getBean("sociedadClienteCorreoService",SociedadClienteCorreoService.class);
			InfoClienteRFC infoClienteRfc = this.getSpringContext().getBean("infoClienteRfc",InfoClienteRFC.class);
			ClienteCompanyRFC clienteCompanyRFC = this.getSpringContext().getBean("clienteCompanyRfc",ClienteCompanyRFC.class);
			
			List<ClienteCompany> res = (List<ClienteCompany>)clienteCompanyRFC.obtieneCompanyCliente(this.noCliente);
//			UsuarioService usuarioService = this.getSpringContext().getBean("usuarioService",UsuarioService.class);
			
//			Usuario u = usuarioService.findByUserName(this.noCliente);
//			if(u == null ){
//				this.setMensaje( this.getLblMain().getString("errUsuarioNoExiste") );
//				return;
//			}
			//Get customer info
			infoClienteRfc.completeEmail = true;
			ClienteInfo clienteInfo = infoClienteRfc.obtieneInfoCliente( noCliente );
		     logger.info("Cliente From SAP:::: " + clienteInfo != null ? clienteInfo.getNombre() : clienteInfo);				     
			if(clienteInfo != null && !clienteInfo.getNombre().trim().equals("")){
					//Cliente si existe, buscamos si existe en la sociedad indicada
					logger.info("Lista de sociedades: " + res);
					if(res != null){
					boolean sociedadValida = false;
					for (ClienteCompany cc : res) {
						if(cc.getCompanyCode().equals(this.sociedad)){	
							logger.info("sociedad encontrada y valida");
							sociedadValida = true;							
						}
					}
					logger.info("Sociedad valida: " + sociedadValida);
					if(!sociedadValida){
						this.setMensaje(this.getLblMain().getString("errSociedadNoEncontradaParaCliente"));
						return;
					}
					
					//Verificar si correo está en blanco, para setear el que viene de SAP
					if(this.correo == null || this.correo.trim().isEmpty()){
						this.setCorreo(clienteInfo.getEmail());
					}
				}else{
					//No se encontró ninguna sociedad
					logger.info("res = null");
					this.setMensaje(this.getLblMain().getString("errSociedadNoEncontradaParaCliente"));
					return;
				}
	
			}else{				
				this.setMensaje(this.getLblMain().getString("errClienteNoExiste"));
				return;
			}
			
			
			if(this.sociedad != null && this.noCliente != null && this.correo != null){
				SociedadClienteCorreo scc = new SociedadClienteCorreo();
				 scc.setSociedad(this.sociedad);
				 scc.setNoCliente(this.noCliente);
				 scc.setNombreCliente(clienteInfo.getNombre());
				 scc.setCorreo(this.correo);
				 sociedadClienteCorreoService.save(scc);			
				this.mensaje = this.getLblMain().getString("msgActualizaInfo");
				this.sociedad = "";
				this.noCliente = "";
				this.correo = "";
			}else{
				//Campos requeridos
			}
		}catch(Exception e){
			logger.info("Error al guardar Cliente-Vendedor", e);
			this.mensaje = e.getMessage();
		}
	}
	
	public void listaSociedadClienteCorreo(){
		this.reset();
		SociedadClienteCorreoService sociedadClienteCorreoService = this.getSpringContext().getBean("sociedadClienteCorreoService",SociedadClienteCorreoService.class);
		 logger.info("metodo listaClienteVendedor");
		if((this.sociedad != null && !this.sociedad.isEmpty()) && (this.noCliente != null && !this.noCliente.isEmpty()) ){
			logger.info("campo sociedad y campo noCliente: " + this.sociedad + " - " + this.noCliente);	
			
			ArrayList<SociedadClienteCorreo>  l = new ArrayList();
			 l.add(sociedadClienteCorreoService.findBySociedadAndNoCliente(sociedad, noCliente));
			this.setList(l);
		}else if(this.sociedad != null && !this.sociedad.isEmpty() ){
			logger.info("campo sociedad: " + this.sociedad);
			this.setList(sociedadClienteCorreoService.findBySociedad(sociedad));
		}else if(this.noCliente != null && !this.noCliente.isEmpty() ){
			logger.info("campo cliente: " + this.noCliente);			
			this.setList( sociedadClienteCorreoService.findByNoCliente(this.noCliente) );
		}else{
			logger.info("findAll SociedadClienteVendedor");
			this.setList(sociedadClienteCorreoService.findAll());
		}	
	}
	
	
	public void eliminaSociedadClienteCorreo(String sociedad, String noCliente){
		logger.info("eliminaSociedadClienteVendedor method " + sociedad + " - " + noCliente);
		SociedadClienteCorreoService sociedadClienteCorreoService = this.getSpringContext().getBean("sociedadClienteCorreoService",SociedadClienteCorreoService.class);
		sociedadClienteCorreoService.delete(sociedad, noCliente);
		this.listaSociedadClienteCorreo();
	}
		
	
	
	public String getCargaMasivaBox() {
		return cargaMasivaBox;
	}

	public void setCargaMasivaBox(String cargaMasivaBox) {
		this.cargaMasivaBox = cargaMasivaBox;
	}	

	public List<SociedadClienteCorreoCargaMasiva> getCargaMasivaLista() {
		return cargaMasivaLista;
	}

	public void setCargaMasivaLista(List<SociedadClienteCorreoCargaMasiva> cargaMasivaLista) {
		this.cargaMasivaLista = cargaMasivaLista;
	}
			
	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public String getSociedad() {
		return sociedad;
	}

	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}
	public String getNoCliente() {
		return noCliente;
	}

	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<SociedadClienteCorreo> getList() {
		return list;
	}

	public void setList(List<SociedadClienteCorreo> list) {
		this.list = list;
	}
	
}
 