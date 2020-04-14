/**
 * Backing bean para listado de Pedidos
 * @author saul.ibarra
 * @fecha 27-Enero-2016
 */
package com.alliax.portalclientes.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


import org.apache.log4j.Logger;

import com.alliax.portalclientes.controller.ListaPedidosRFC;
import com.alliax.portalclientes.general.formato.Fecha;
import com.alliax.portalclientes.model.OrdenVenta;
import com.alliax.portalclientes.util.Helper;

@ManagedBean(name="listaPedidos")
@SessionScoped
public class ListadoPedidos_backing extends AbstractBackingGen {

	private final static Logger logger = Logger.getLogger(ListadoPedidos_backing.class);
		
	private String tipoDocumento;
	private String documento;
	private String noPedido;
	private String documentoComercial;
	private String rangoDias;
	private String fechaInicial;
	private String fechaFinal;
	private String estatus;
	private String lang;
	
	//@ManagedProperty(value="#{sessionScope.listadoPedidos}")/
	private List<OrdenVenta> listadoPedidos;

//	@ManagedProperty(value="#{estatusGlobalMap}")
	private Map<String,String> catalogoEstatus;
	
//	@ManagedProperty(value="#{rangoFechaMap}")
	private Map<String,String> catalogoRangoFechas;
		
			
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}	

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNoPedido() {
		return noPedido;
	}

	public void setNoPedido(String noPedido) {
		this.noPedido = noPedido;
	}	

	public String getDocumentoComercial() {
		return documentoComercial;
	}

	public void setDocumentoComercial(String documentoComercial) {
		this.documentoComercial = documentoComercial;
	}

	public String getRangoDias() {
		return rangoDias;
	}

	public void setRangoDias(String rangoDias) {
		this.rangoDias = rangoDias;
	}

	public String getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}	

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Map<String, String> getCatalogoEstatus() {
		this.setCatalogoEstatus(Helper.getEstatusGlobalMap(this.getLblMain()));
		return catalogoEstatus;
	}
	
	public Set<Entry<String,String>> getCatalogoEstatusSet(){
		if(catalogoEstatus == null)
			this.setCatalogoEstatus(Helper.getEstatusGlobalMap(this.getLblMain()));
		return this.catalogoEstatus.entrySet();
	}

	public void setCatalogoEstatus(Map<String, String> catalogoEstatus) {
		this.catalogoEstatus = catalogoEstatus;
	}
	
	public Map<String, String> getCatalogoRangoFechas() {
		this.setCatalogoRangoFechas(Helper.getRangoFechaMap(this.getLblMain()));
		return catalogoRangoFechas;
	}
	
	public Set<Entry<String,String>> getCatalogoRangoFechasSet(){
		if(catalogoRangoFechas == null)
			this.setCatalogoRangoFechas(Helper.getRangoFechaMap(this.getLblMain()));
		return this.catalogoRangoFechas.entrySet();
	}	

	public void setCatalogoRangoFechas(Map<String, String> catalogoRangoFechas) {
		this.catalogoRangoFechas = catalogoRangoFechas;
	}

	public List<OrdenVenta> getListadoPedidos() {
		return this.listadoPedidos;
	}

	public void setListadoPedidos(List<OrdenVenta> listadoPedidos) {
		this.listadoPedidos = listadoPedidos;
	}
	
	
	/**
	 * Action para busqueda de pedidos
	 * @return
	 */
	public String buscaPedidos(){
		try {
			logger.info("Buscando pedidos");
			
			List<String> rangoFechas = null;
			
			logger.info("Rango dias " + this.getRangoDias());
			
			if(this.getRangoDias().equals("H")){
				rangoFechas = Fecha.getRangosFechas(0,6);
				logger.info("FechaInicial " + rangoFechas.get(0));
				logger.info("FechaFinal " + rangoFechas.get(1));								
				this.setFechaInicial(rangoFechas.get(0));
				this.setFechaFinal(rangoFechas.get(1));				
			} else if(this.getRangoDias().equals("S")){
				rangoFechas = Fecha.getRangosFechas(7,6);				
				logger.info("FechaInicial " + rangoFechas.get(0));
				logger.info("FechaFinal " + rangoFechas.get(1));								
				this.setFechaInicial(rangoFechas.get(0));
				this.setFechaFinal(rangoFechas.get(1));				
			} else if(this.getRangoDias().equals("T")) {
				rangoFechas = Fecha.getRangosFechas(30,6);				
				logger.info("FechaInicial " + rangoFechas.get(0));
				logger.info("FechaFinal " + rangoFechas.get(1));								
				this.setFechaInicial(rangoFechas.get(0));
				this.setFechaFinal(rangoFechas.get(1));					
			} else if(this.getRangoDias().equals("N")) {
				rangoFechas = Fecha.getRangosFechas(90,6);				
				logger.info("FechaInicial " + rangoFechas.get(0));
				logger.info("FechaFinal " + rangoFechas.get(1));								
				this.setFechaInicial(rangoFechas.get(0));
				this.setFechaFinal(rangoFechas.get(1));					
			} else if(this.getRangoDias().equals("F")) {
				this.setFechaFinal(this.getFechaInicial());
			}
			
			logger.info("Estatus " + this.getEstatus());
			
			List<String> estatus = new ArrayList<String>();
			if(this.getEstatus().equals("A")){
				estatus.add("A");
				estatus.add("B");
				estatus.add(" ");
			} else if(this.getEstatus().equals("C")) {
				estatus.add("C");
			}
			
			//Setea documento a buscar
			logger.info("Tipo Doc " + this.getTipoDocumento());
			if(this.getTipoDocumento().equalsIgnoreCase("Pedido")){
				logger.info("Pedido " + this.getNoPedido());
				this.setNoPedido(this.getDocumento());
				this.setDocumentoComercial("");
			} else {
				logger.info("Doc com " + this.getNoPedido());
				this.setDocumentoComercial(this.getDocumento());
				this.setNoPedido("");
			}			
			
			ListaPedidosRFC listado = this.getSpringContext().getBean("listaPedidosRfc",ListaPedidosRFC.class);
			
			//Elimina resultados de session
			//this.getSessionMap().remove("listadoPedidos");
						
			this.setListadoPedidos(listado.busquedaPedidos(
				this.getUsuarioLogueado().getNoCliente(),
				this.getFechaInicial(),
					this.getFechaFinal(),
						this.getNoPedido(),
							this.getDocumentoComercial(),
								estatus,
									this.getUsuarioLogueado().getLanguage()));
			
			logger.info("Despues del set list");
			
			//Graba listado en session
			if(this.getSessionMap() == null)
				logger.error("Session is null");
			
			if(this.getListadoPedidos() == null)
				logger.error("Lista is null");
			
			//this.getFacesContext().getExternalContext().getSessionMap().put("listadoPedidos", this.getListadoPedidos() );
			
			logger.info("Despues del set en session");
									
		} catch(Exception e){
			logger.error("Error al desplegar listado de pedidos " + e.getLocalizedMessage());
			this.getFacesContext().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR,"Error",this.getLblMain().getString("errListaPedidos")));
		}
		return "";
	}
}
