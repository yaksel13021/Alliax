/**
 * Backing bean para desplegar la informacion del cliente
 * @author saul.ibarra
 * @fecha 29-Feb-16
 */
package com.alliax.portalclientes.view;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.alliax.portalclientes.controller.InfoClienteRFC;
import com.alliax.portalclientes.model.ClienteInfo;

@ManagedBean(name="infoCliente")
@ViewScoped
public class InfoCliente_backing extends AbstractBacking {
	
	private final static Logger logger = Logger.getLogger(InfoCliente_backing.class);
	
	private ClienteInfo clienteInfo;

	public ClienteInfo getClienteInfo() {
		return clienteInfo;
	}

	public void setClienteInfo(ClienteInfo clienteInfo) {
		this.clienteInfo = clienteInfo;
	}
	
	/**
	 * Obtiene la informacion del cliente
	 * @return
	 */
	public String cargaInfoCliente(){
		try {
		InfoClienteRFC infoClienteRfc = this.getSpringContext().getBean("infoClienteRfc",InfoClienteRFC.class);
		
		this.setClienteInfo(
				infoClienteRfc.obtieneInfoCliente(
						this.getUsuarioLogueado().getNoCliente()));
		
		} catch(Exception e){
			logger.error("Error al desplegar información del cliente. " + e.getLocalizedMessage());
			this.getFacesContext().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,"Error",this.getLblMain().getString("errInfoCliente")));				
		}
		return "";
	}
}
