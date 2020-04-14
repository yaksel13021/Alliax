/**
 * Backing bean para Seleccionar Cliente por usuarios de Ventas
 * @author ext.juan.gonzalez
 * @Fecha 1-Nov-2018
 */
package com.alliax.portalclientes.view;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.alliax.portalclientes.controller.InfoClienteRFC;
import com.alliax.portalclientes.model.ClienteInfo;
import com.alliax.portalclientes.model.CustomUserDetails;

@ManagedBean(name="selecCliente")
@ViewScoped
public class SeleccionaCliente_Backing extends AbstractBackingGen {

	private final static Logger logger = Logger.getLogger(SeleccionaCliente_Backing.class);	
	
	
	private String noCliente;
	
	private ClienteInfo clienteInfo;

	private String trk;

	public String getNoCliente() {
		return noCliente;
	}

	public void setNoCliente(String noCliente) {
		this.noCliente = noCliente;
	}

//	public List<ClienteInfo> getResultados() {
//		return resultados;
//	}
//
//	public void setResultados(List<ClienteInfo> resultados) {
//		this.resultados = resultados;
//	}	


	public ClienteInfo getClienteInfo() {
		return clienteInfo;
	}

	public void setClienteInfo(ClienteInfo clienteInfo) {
		this.clienteInfo = clienteInfo;
	}

	public String getTrk() {
		return trk;
	}

	public void setTrk(String trk) {
		this.trk = trk;
	}

	
//	public String buscaCliente(){
//		try {
//
//			InfoClienteRFC infoClienteRfc = this.getSpringContext().getBean("infoClienteRfc",InfoClienteRFC.class);
//
//			 this.setClienteInfo(infoClienteRfc.obtieneInfoCliente(
//						this.getNoCliente()));
//			 			
//			//Agrega Rol de Cliente
//			List<GrantedAuthority> authorities = new ArrayList(this.getUsuarioLogueado().getAuthorities());
//			authorities.add(new SimpleGrantedAuthority("ROLE_CLIENTE_MEMBER"));
//			
//			//Setea informacion del cliente en Spring Security
//			CustomUserDetails userDetails = new CustomUserDetails(
//				this.getUsuarioLogueado().getUsername(),
//					"pwd",
//						authorities,
//							this.getUsuarioLogueado().getNoCliente(),
//								this.getUsuarioLogueado().getEmail());
//			
//			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//			
//			this.getFacesContext().addMessage("", 
//					new FacesMessage(FacesMessage.SEVERITY_INFO,"",
//						(new MessageFormat(this.getLblMain().getString("msgClienteSel")))
//							.format(new Object[]{this.getClienteInfo().getNombre()})));			
//			
//			return this.trk;
//			//return "/bovedaFiscal/index";
//			
//		} catch(Exception e){
//			logger.error("Error al establecer permisos " + e.getLocalizedMessage(),e);
//			this.getFacesContext().addMessage("", 
//					new FacesMessage(FacesMessage.SEVERITY_ERROR,"",
//						(new MessageFormat(this.getLblMain().getString("errProcesamiento")))
//							.format(new Object[]{e.getLocalizedMessage()})));
//			
//			return "";
//		}
//		
//	}
}
