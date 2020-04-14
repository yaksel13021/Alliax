/**
 * Backing bean para detalle del Pedido
 */
package com.alliax.portalclientes.view;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;

import com.alliax.portalclientes.controller.DetalleFacturaRFC;
import com.alliax.portalclientes.controller.DetallePedidoRFC;
import com.alliax.portalclientes.controller.FacturasPedidoRFC;
import com.alliax.portalclientes.model.Factura;
import com.alliax.portalclientes.model.Item;
import com.alliax.portalclientes.model.OrdenVenta;

@ManagedBean(name="detalle")
@ViewScoped
public class DetallePedido_backing extends AbstractBackingGen {
	
	private OrdenVenta pedido;
	private List<Item> partidas;
	
	private List<Factura> facturas;
	//private List<Factura> facturasTest;
	
	private final static Logger logger = Logger.getLogger(DetallePedido_backing.class);
	
	private String classPartidas = "tab-pane active";
	private String classFacturas = "tab-pane";

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

	/*public List<Factura> getFacturasTest() {
		return facturasTest;
	}

	public void setFacturasTest(List<Factura> facturasTest) {
		this.facturasTest = facturasTest;
	}*/
	
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

	/**
	 * Action para cargar el detalle del pedido
	 */
	public String cargaDetallePartidas(){
		try{
			logger.info("cargaDetallePartidas");
			//if(this.getSessionMap().get("pedidoSel") != null) {
				this.setPedido((OrdenVenta)this.getSessionMap().get("pedidoSel"));
				//this.setPedido((OrdenVenta)this.getFlash().get("pedidoSel"));
				
				DetallePedidoRFC detalle = this.getSpringContext().getBean("detallePedido",DetallePedidoRFC.class);
				this.setPartidas(
						detalle.detallePedido(this.getPedido().getDocumentoComercial(),
								this.getUsuarioLogueado().getLanguage()));
				
				this.setFacturas(detalle.getListaFacturas());
			//}
		} catch(Exception e){
			logger.error("Error al desplegar detalle del pedido. " + e.getLocalizedMessage());
			this.getFacesContext().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
							(new MessageFormat(this.getLblMain().getString("errDetallePedido")).format(
									new Object[] {this.getPedido().getDocumentoComercial()}))));			
		}
		
		return "";
	}
	
	
	/**
	 * Metodo para Extraer las facturas de un pedido
	 * @param abe
	 */
	public void obtieneFacturas(AjaxBehaviorEvent abe){
		try {
			logger.info("Buscando facturas del pedido ");
			logger.info("Doc. Comercial: " + this.getPedido().getDocumentoComercial());
			
			if(this.getFacturas() == null){
				this.setClassFacturas("tab-pane active");
				this.setClassPartidas("tab-pane");
				
				//List<Factura> test = new ArrayList<Factura>();
				
				FacturasPedidoRFC facturas = this.getSpringContext().getBean("facturasPedido",FacturasPedidoRFC.class);
				
				this.setFacturas(
						facturas.busquedaFacturas(this.getPedido().getDocumentoComercial()));
			}
		} catch(Exception e){
			logger.error("Error al desplegar detalle del pedido " + e.getLocalizedMessage());
									
			this.getFacesContext().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
							(new MessageFormat(this.getLblMain().getString("errListaFacturas")).format(
									new Object[] {this.getPedido().getDocumentoComercial()}))));			
		}
	}
	
	
	/**
	 * Metodo para descargar detalle de factura
	 * @param abe
	 */
	public void verDetalleFacturaClick(AjaxBehaviorEvent abe){
		//Obtiene el registro seleccionado de la tabla
		logger.info("Buscando facturas");
		Factura facturaSel = this.getFacesContext().getApplication().evaluateExpressionGet(
	    						this.getFacesContext(), "#{factura}", Factura.class);
		
		if(facturaSel != null) {		
			logger.info("Pedido " + this.getPedido().getDocumentoComercial());
			logger.info("Doc. Factura " + facturaSel.getDocFactura());
			
			try {
				if(facturaSel.getDetalleFactura() == null){
					DetalleFacturaRFC detalleFact = this.getSpringContext().getBean("detalleFacturaRFC",DetalleFacturaRFC.class);
					facturaSel.setDetalleFactura(
							detalleFact.obtieneDetalleFactura(
								pedido.getDocumentoComercial(), facturaSel.getDocFactura()));
					facturaSel.setMuestraDetalle(true);
				} else {
					if(facturaSel.isMuestraDetalle())
						facturaSel.setMuestraDetalle(false);
					else
						facturaSel.setMuestraDetalle(true);						
				}
					
				
			} catch(Exception e){
				logger.error("Error al desplegar detalle del pedido " + this.getPedido().getDocumentoComercial() + " " + e.getLocalizedMessage());
				
				this.getFacesContext().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
								(new MessageFormat(this.getLblMain().getString("errDetalleFacturas")).format(
										new Object[] {facturaSel.getDocFactura(),this.getPedido().getDocumentoComercial()}))));			
			}	
		} else {
			logger.error("La fila seleccionada es null");
			this.getFacesContext().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",
							(new MessageFormat(this.getLblMain().getString("errDetalleFacturas")).format(
									new Object[] {facturaSel.getDocFactura(),this.getPedido().getDocumentoComercial()}))));				
		}
	}	
	
	
	/**
	 * Form flow consturctor
	 * @param estatusGen
	 * @return
	 */
	public String estiloFormFlow(String estatusGen){
		
		if(estatusGen.equalsIgnoreCase("Pend")){
			if(this.getPedido().getEstatusGeneral().equalsIgnoreCase("Pend"))
				return "stepPendiente stepTextPendienteSel";
			else if(this.getPedido().getEstatusGeneral().equalsIgnoreCase("Vsac"))
				return "stepPendiente stepTextPendienteSel";
			else if(this.getPedido().getEstatusGeneral().equalsIgnoreCase("Held"))
				return "stepPendiente stepTextPendienteSel";
			else if(this.getPedido().getEstatusGeneral().equalsIgnoreCase("Canc"))
				return "stepPendiente stepTextPendienteSel";
			else if(this.getPedido().getEstatusGeneral().equalsIgnoreCase("Rele"))
				return "stepPairBefore stepText";
			else 
				return "stepPair stepText";
		} else if(estatusGen.equalsIgnoreCase("Rele")){
			if(this.getPedido().getEstatusGeneral().equalsIgnoreCase("Rele"))
				return "stepSelectedImpair stepTextSel";
			else if(this.getPedido().getEstatusGeneral().equalsIgnoreCase("Proc"))
				return "stepImpairBefore stepText";			
			else
				return "stepImpair stepText";
		} else if(estatusGen.equalsIgnoreCase("Proc")){ 
			if(this.getPedido().getEstatusGeneral().equalsIgnoreCase("Proc"))
				return "stepSelectedPair stepTextSel";
			else if(this.getPedido().getEstatusGeneral().equalsIgnoreCase("Pinv"))
				return "stepPairBefore stepText";			
			else
				return "stepPair stepText";
		} else if(estatusGen.equalsIgnoreCase("Pinv")){
			if(this.getPedido().getEstatusGeneral().equalsIgnoreCase("Pinv"))
				return "stepSelectedImpair stepTextSel";
			else if(this.getPedido().getEstatusGeneral().equalsIgnoreCase("Conc"))
				return "stepImpairBefore stepText";					
			else
				return "stepImpair stepText";			
		} else if(estatusGen.equalsIgnoreCase("Conc")){ 
			if(this.getPedido().getEstatusGeneral().equalsIgnoreCase("Conc"))			
				return "stepSelectedPair stepTextSel";
			else if(this.getPedido().getEstatusGeneral().equalsIgnoreCase("Comp"))
				return "stepPairBefore stepText";				
			else 
				return "stepPair stepText";
		} else if(estatusGen.equalsIgnoreCase("Comp")){
			if(this.getPedido().getEstatusGeneral().equalsIgnoreCase("Comp"))			
				return "stepSelectedLast stepTextSel";
			else
				return "stepLast stepText";
		}
		return "";
	}
	
}
