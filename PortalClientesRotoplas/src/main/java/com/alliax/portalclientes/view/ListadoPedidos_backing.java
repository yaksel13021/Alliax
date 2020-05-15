/**
 * Backing bean para listado de Pedidos
 * @author saul.ibarra
 * @fecha 27-Enero-2016
 */
package com.alliax.portalclientes.view;

import java.io.Serializable;
import java.text.MessageFormat;
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
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import com.alliax.portalclientes.controller.CancelaPedidoConfig;
import com.alliax.portalclientes.controller.CancelaPedidoRFC;
import com.alliax.portalclientes.controller.DetallePedidoConfig;
import com.alliax.portalclientes.controller.DetallePedidoRFC;
import com.alliax.portalclientes.controller.FacturasConfig;
import com.alliax.portalclientes.controller.FacturasPedidoRFC;
import com.alliax.portalclientes.controller.ListaPedidosConfig;
import com.alliax.portalclientes.model.Factura;
import com.alliax.portalclientes.model.Item;
import org.apache.log4j.Logger;

import com.alliax.portalclientes.controller.ListaPedidosRFC;
import com.alliax.portalclientes.general.formato.Fecha;
import com.alliax.portalclientes.model.OrdenVenta;
import com.alliax.portalclientes.util.Helper;

@ManagedBean(name="listaPedidos", eager = true)
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


	private OrdenVenta pedido;
	private List<Item> partidas;
	private List<Factura> facturas;

	private String classPartidas = "tab-pane active";
	private String classFacturas = "tab-pane";

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
			try {
				this.setListadoPedidos(listado.busquedaPedidos(
						this.getUsuarioLogueado().getNoCliente(),
						this.getFechaInicial(),
						this.getFechaFinal(),
						this.getNoPedido(),
						this.getDocumentoComercial(),
						estatus,
						this.getUsuarioLogueado().getLanguage()));
			}catch (Exception e){
				ListaPedidosConfig listaPedidosConfig = new ListaPedidosConfig();
				this.setListadoPedidos(listaPedidosConfig.pedidos());
			}
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

	public void asignaPedido(){

		OrdenVenta ov = this.getFacesContext().getApplication().evaluateExpressionGet(
				this.getFacesContext(), "#{pedidoObj}", OrdenVenta.class);
		this.setPedido(ov);
		logger.info("asignaPedido " + ov);
	}

	/**
	 * Action para cargar el detalle del pedido
	 */
	public void cargaDetallePartidas(){
		try{

			OrdenVenta ov = this.getFacesContext().getApplication().evaluateExpressionGet(
					this.getFacesContext(), "#{pedidoObj}", OrdenVenta.class);
			this.setPedido(ov);
			logger.info("cargaDetallePartidas " + getPedido());
			if(getPedido() != null) {
				DetallePedidoRFC detalle = this.getSpringContext().getBean("detallePedido", DetallePedidoRFC.class);
					this.setPartidas(
							detalle.detallePedido(this.getPedido().getDocumentoComercial(),
									this.getUsuarioLogueado().getLanguage()));

//				DetallePedidoConfig detalleConf = new DetallePedidoConfig();
//				setPartidas(detalleConf.partidas());

				this.setFacturas(detalle.getListaFacturas());
			}
			//}
		} catch(Exception e){
			logger.error("Error getPedido. " + getPedido());
			logger.error("Error al desplegar detalle del pedido. " + e.getLocalizedMessage());
			this.getFacesContext().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
							(new MessageFormat(this.getLblMain().getString("errDetallePedido")).format(
									new Object[] {this.getPedido().getDocumentoComercial()}))));

			DetallePedidoConfig detalle = new DetallePedidoConfig();
			setPartidas(detalle.partidas());
		}
	}

	/**
	 * Metodo para Extraer las facturas de un pedido
	 * @param abe
	 */
	public void obtieneFacturas(AjaxBehaviorEvent abe){
		try {
			logger.info("Buscando facturas del pedido ");
			logger.info("Doc. Comercial: " + this.getPedido().getDocumentoComercial());

			//if(this.getFacturas() == null){
				this.setClassFacturas("tab-pane active");
				this.setClassPartidas("tab-pane");

				//List<Factura> test = new ArrayList<Factura>();

				FacturasPedidoRFC facturas = this.getSpringContext().getBean("facturasPedido",FacturasPedidoRFC.class);

				this.setFacturas(
						facturas.busquedaFacturas(this.getPedido().getDocumentoComercial()));
			//}
		} catch(Exception e){
			logger.error("Error al desplegar detalle del pedido " + e.getLocalizedMessage());

			this.getFacesContext().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
							(new MessageFormat(this.getLblMain().getString("errListaFacturas")).format(
									new Object[] {this.getPedido().getDocumentoComercial()}))));
			FacturasConfig facturasConfig = new FacturasConfig();

			this.setFacturas(facturasConfig.facturas());
		}
	}

	/**
	 * Metodo para Cancelar un pedido
	 * @param nroPedido
	 */
	public void cancelarPedido(OrdenVenta nroPedido){
		try {
			logger.info("Cancelar pedido,  nroPedido: " + nroPedido);
			CancelaPedidoRFC cancelaPedidoRFC = this.getSpringContext().getBean("cancelaPedidoRFC",CancelaPedidoRFC.class);
			String cancelado = cancelaPedidoRFC.cancelaPedido(nroPedido.getPedidoCliente());
			logger.info("Cancelar pedido,  respuesta: " + cancelado);
			nroPedido.setPedidoCancelado(cancelado);
		} catch(Exception e){
			logger.error("Error al cancelar Pedidoo " + e.getLocalizedMessage());
			//CancelaPedidoConfig cancelaPedidoConfig	= new CancelaPedidoConfig();
			///String cancelado = cancelaPedidoConfig.cancelaPedido(nroPedido.getPedidoCliente());
			nroPedido.setPedidoCancelado("1");
			//this.getFacesContext().addMessage(null,
					//new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
							//(new MessageFormat(this.getLblMain().getString("errCancelaPedido")).format(
									//new Object[] {this.getPedido().getPedidoCliente()}))));
		}
	}

	/**
	 * Metodo para Copiar un pedido
	 * @param nroPedido
	 */
	public void copiarPedido(String nroPedido){
		try {
			logger.info("Copiar pedido,  nroPedido: " + nroPedido);

		} catch(Exception e){
			logger.error("Error al cancelar Pedidoo " + e.getLocalizedMessage());
			//this.getFacesContext().addMessage(null,
			//new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
			//(new MessageFormat(this.getLblMain().getString("errCancelaPedido")).format(
			//new Object[] {this.getPedido().getPedidoCliente()}))));
		}
	}

	public OrdenVenta getPedido() {
		return pedido;
	}

	public void setPedido(OrdenVenta pedido) {
		this.pedido = pedido;
	}

	public List<Item> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<Item> partidas) {
		this.partidas = partidas;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public String getClassPartidas() {
		return classPartidas;
	}

	public void setClassPartidas(String classPartidas) {
		this.classPartidas = classPartidas;
	}

	public String getClassFacturas() {
		return classFacturas;
	}

	public void setClassFacturas(String classFacturas) {
		this.classFacturas = classFacturas;
	}

	
}
