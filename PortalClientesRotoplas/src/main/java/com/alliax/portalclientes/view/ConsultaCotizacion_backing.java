package com.alliax.portalclientes.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.alliax.portalclientes.controller.ConstructEmail;
import com.alliax.portalclientes.controller.PrecioMaterialRFC;

import com.alliax.portalclientes.domain.*;
import com.alliax.portalclientes.model.DetallePedidoCotizacion;
import com.alliax.portalclientes.model.PrecioMaterial;
import com.alliax.portalclientes.service.MaterialService;
import org.apache.log4j.Logger;
import com.alliax.portalclientes.controller.ConstructEmail;
import com.alliax.portalclientes.controller.InfoClienteRFC;
import com.alliax.portalclientes.domain.RolUsuario;
import com.alliax.portalclientes.domain.Usuario;
import com.alliax.portalclientes.model.CotizacionFlete;


import com.alliax.portalclientes.service.PedidoPartidasService;
import com.alliax.portalclientes.service.PedidoService;

@ManagedBean(name="consultaCotizacion")
@SessionScoped
public class ConsultaCotizacion_backing extends AbstractBackingGen {




    private final static Logger logger = Logger.getLogger(ConsultaCotizacion_backing.class);


   
    private String noCotizacion;
    private String fecha;
    private String noCliente;
    private String noCotizacionSel;


    private PedidoService service;
    private PedidoPartidasService partidaService;
    private MaterialService materialService;
    private List<CotizacionFlete> cotizaciones;
    
    private String total;
    private String subtotal;
    private String impuesto;
    
    private String moneda;
    
    private String finalizar;
    private String email;


    private List<DetallePedidoCotizacion> partidas;
    
    private DetallePedidoCotizacion cotizacion;
    
    private boolean mostrarCotizacion = false;
    
    public String getNoCotizacion() {
        return noCotizacion;
    }

    public void setNoCotizacion(String noCotizacion) {
        this.noCotizacion = noCotizacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNoCliente() {
        return noCliente;
    }

    public void setNoCliente(String noCliente) {
        this.noCliente = noCliente;
    }

    public List<CotizacionFlete> getCotizaciones() {
        return cotizaciones;
    }

    public void setCotizaciones(List<CotizacionFlete> cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    public List<DetallePedidoCotizacion> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<DetallePedidoCotizacion> partidas) {
        this.partidas = partidas;
    }
    
    public DetallePedidoCotizacion getCotizacion() {
    	return this.cotizacion;
    }
    public void setCotizacion(DetallePedidoCotizacion cotizacion) {
    	this.cotizacion = cotizacion;
    	
    }
    
    public String getNoCotizacionSel() {
    	return noCotizacionSel;
    }
    
    public void setNoCotizacionSel(String noCotizacionSel) {
    	this.noCotizacionSel = noCotizacionSel;
    }
    
    public String getMoneda() {
    	return moneda;
    }
    
    public void setMoneda(String moneda) {
    	this.moneda = moneda;
    }
    
    public String getFinalizar() {
    	return finalizar;
    }
    
    public void setFinalizar(String finalizar) {
    	this.finalizar = finalizar;
    }
    
    @PostConstruct
    public void init(){

        logger.info("consultaCotizacion init");
        try{
            service = this.getSpringContext().getBean("pedidoService" , PedidoService.class);
            partidaService = this.getSpringContext().getBean("pedidoPartidasService" , PedidoPartidasService.class);
            materialService = this.getSpringContext().getBean("materialService" , MaterialService.class);
        }catch(Exception e){
            logger.error("Error al iniciar");
        }
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String buscaCotizaciones(String noCliente){
        logger.info("consultaCotizacion " + fecha + noCliente + noCotizacion);
        cotizaciones = new ArrayList();
        try{

             List<com.alliax.portalclientes.domain.Pedido> pedidos = service.findCotizacionesFlete(fecha , noCotizacion ,noCliente);
             for(com.alliax.portalclientes.domain.Pedido p : pedidos){
                        CotizacionFlete c = new CotizacionFlete();
                        c.setNroPedido(""+p.getIdPedido());
                        c.setNoCotizacion(p.getNoCotizacion());
                        c.setEstado(p.getEstatusCotizacion());
                        PedidoPartidasPK pk = new PedidoPartidasPK();
                        pk.setIdPedido(p.getIdPedido());
                        pk.setSku(CotizacionFlete.idMatFlete);
                        com.alliax.portalclientes.domain.PedidoPartidas pp = partidaService.findById(pk);
                        if(pp!= null) {
                        	c.setMaterial(CotizacionFlete.idMatFlete);
                        	c.setCantidad(pp.getCantidad());
                        	c.setDescripcion("Flete");
                        	c.setPrecioNeto(pp.getPrecioNeto());
                        	c.setMonto(pp.getMonto());
                        	c.setuM("SVO");
                        
                        }
                        Material mat = materialService.findById(CotizacionFlete.idMatFlete);
                        if(mat!= null){
                            c.setMaterial(mat.getSku());
                            c.setDescripcion(mat.getDescripcion());
                            c.setuM(mat.getUnidadMedida());
                        }
                        this.getCotizaciones().add(c);
                    }
        } catch(Exception e){
            logger.error("Error al desplegar listado de fletes " + e.getLocalizedMessage());
            this.getFacesContext().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,"Error",this.getLblMain().getString("errListaPedidos")));
        }
        return "";
    }

    public String buscarDetalles(String noPedido){
        partidas = new ArrayList();
        try{

            PrecioMaterialRFC precioRFC = this.getSpringContext().getBean("precioMaterialRFC",PrecioMaterialRFC.class);
            Long idPedido = Long.valueOf(noPedido);
            Pedido pedido=  service.findById(idPedido);
            if(pedido!= null) {
            this.setEmail(pedido.getCorreoElectronico());
            List<PedidoPartidas> partidasPedidos = partidaService.findByidPedido(idPedido);
            BigDecimal subtotal = BigDecimal.ZERO;
            BigDecimal impuesto = BigDecimal.ZERO;
            for(PedidoPartidas pp : partidasPedidos){

                
            	DetallePedidoCotizacion d = new DetallePedidoCotizacion();
                d.setNoPedido(noPedido);
                d.setPosicion(pp.getPosicion());
                Material mat = materialService.findById(pp.getId().getSku());
                if(mat!= null){
                    d.setNoMaterial(mat.getSku());
                    d.setDescripcion(mat.getDescripcion());
                    d.setUnidadMedida(mat.getUnidadMedida());
             
                    PrecioMaterial precio =  precioRFC.obtienePrecioMaterial(pedido.getClasePedido(),pedido.getOrganizacionVenta(),"20","02",pedido.getTipoMaterial(),pp.getId().getSku(),pp.getCantidad(),mat.getUnidadMedida(),pedido.getNroCliente(),pedido.getDestinatarioMercancia());
              
                    if(precio!= null) {
                    pp.setMonto(precio.getMonto()!= null? precio.getMonto().toString() : pp.getMonto());
                    pp.setPrecioNeto(precio.getPrecioNeto()!= null ? precio.getPrecioNeto().toString() : pp.getPrecioNeto());
                    pp.setIva(precio.getIva()!= null ?  precio.getIva() : pp.getIva());
                    pp.setFechaEntrega(precio.getFechaEntrega()!= null ?precio.getFechaEntrega(): pp.getFechaEntrega());
                    pp.setMensajeError(precio.getMensajeError()!= null ? precio.getMensajeError() : pp.getMensajeError());
                    pp.setMoneda(precio.getMoneda()!= null ? precio.getMoneda() :pp.getMoneda());
                    pp.setTotalPartida(precio.getTotalPartida()!= null ? precio.getTotalPartida().toString(): pp.getTotalPartida());
                    partidaService.save(pp);
                    }

                    
                }
                d.setMonto(pp.getMonto());
                d.setPrecioNeto(pp.getPrecioNeto());
                d.setCantidad(pp.getCantidad());
                d.setFechaEnt(pp.getFechaEntrega());
                d.setEstatus(pedido.getEstatusCotizacion());
                d.setCantEnt(pp.getCantidadEntregada());
                d.setMoneda(pp.getMoneda());
                this.moneda = pp.getMoneda();
                subtotal = subtotal.add(pp.getMonto()!= null ? new BigDecimal(pp.getMonto()) : BigDecimal.ZERO);
                impuesto = impuesto.add(pp.getIva()!= null ? new BigDecimal(pp.getIva()) : BigDecimal.ZERO); 
                if(!mostrarCotizacion && pp.getId().getSku().equals(CotizacionFlete.idMatFlete)) {
                	this.noCotizacionSel = pedido.getNoCotizacion();
                	this.cotizacion = d;
                }else {
                	partidas.add(d);
                }
                this.subtotal = subtotal.toString();
                this.impuesto = impuesto.toString();
                this.total= subtotal.add(impuesto).toString();
            }
            }
        } catch(Exception e){
            logger.error("Error al desplegar listado de fletes " + e.getLocalizedMessage());
            this.getFacesContext().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,"Error",this.getLblMain().getString("errListaPedidos")));
        }
        return "";
    }


    public void enviarMailCotizacion(String nroPedido){
        this.buscarDetalles(nroPedido);
        BigDecimal total = BigDecimal.ZERO;
        String fechaEntrega = this.cotizacion.getFechaEnt();
        if(partidas!=null&&!partidas.isEmpty()) {
            for (DetallePedidoCotizacion detallePedidoCotizacion : this.partidas) {
                total = total.add(new BigDecimal(detallePedidoCotizacion.getMonto()));
            }
            logger.info("Total para envio de email:" + total);
            ConstructEmail mail = this.getSpringContext().getBean("constructEmail", ConstructEmail.class);
            mail.enviaCorreoCotizacion(this.email, this.getClienteInfo(), this.noCotizacion, this.partidas, total.toString() ,fechaEntrega);
        }else{
            logger.info("No se encontraros partidas para el nroPedido-"+nroPedido);
        }
    }

    
    public String grabar(DetallePedidoCotizacion cotizacion) {
    	logger.info("grabar noCotizacion" +  noCotizacion);
    		if(cotizacion!= null && cotizacion.getNoPedido()!= null) {
    			
    		logger.info("grabar noCotizacion" + cotizacion.getDescripcion() + " " + cotizacion.getNoPedido() + " " + cotizacion.getMonto());
    		PedidoPartidasPK pk = new PedidoPartidasPK();
    		Long idPedido = Long.valueOf(cotizacion.getNoPedido());
    		pk.setIdPedido(idPedido);
            pk.setSku(CotizacionFlete.idMatFlete);
            com.alliax.portalclientes.domain.PedidoPartidas pp = partidaService.findById(pk);
            if(pp!= null) {
            	logger.info("Se guardaran los cambios al pedido " + idPedido);
            	pp.setMonto(cotizacion.getMonto());
            	pp.setPrecioNeto(cotizacion.getMonto());
            	pp.setFechaEntrega(cotizacion.getFechaEnt());
            	partidaService.save(pp);
            	this.mostrarCotizacion = true;	
            	enviarMailCotizacion(cotizacion.getNoPedido());
            }
    	}
    	return "";
    	
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
     
    
    
}