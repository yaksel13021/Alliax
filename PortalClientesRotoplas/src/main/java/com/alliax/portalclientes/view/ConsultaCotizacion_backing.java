package com.alliax.portalclientes.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.alliax.portalclientes.controller.ConstructEmail;
import com.alliax.portalclientes.controller.CrearPedidoRFC;
import com.alliax.portalclientes.controller.PrecioMaterialRFC;

import com.alliax.portalclientes.domain.*;
import com.alliax.portalclientes.domain.Material;
import com.alliax.portalclientes.domain.Pedido;
import com.alliax.portalclientes.domain.PedidoPartidas;
import com.alliax.portalclientes.model.*;
import com.alliax.portalclientes.service.MaterialService;
import com.alliax.portalclientes.util.Helper;
import org.apache.log4j.Logger;
import com.alliax.portalclientes.controller.ConstructEmail;
import com.alliax.portalclientes.controller.InfoClienteRFC;
import com.alliax.portalclientes.domain.RolUsuario;
import com.alliax.portalclientes.domain.Usuario;


import com.alliax.portalclientes.service.PedidoPartidasService;
import com.alliax.portalclientes.service.PedidoService;
import com.alliax.portalclientes.service.UsuarioService;

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
    
    private BigDecimal  total;
    private BigDecimal subtotal;
    private BigDecimal impuesto;
    
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public boolean isMostrarCotizacion() {
        return mostrarCotizacion;
    }

    public void setMostrarCotizacion(boolean mostrarCotizacion) {
        this.mostrarCotizacion = mostrarCotizacion;
    }

    public String buscaCotizaciones(String noCliente){
        logger.info("consultaCotizacion " + fecha + noCliente + noCotizacion);
        cotizaciones = new ArrayList();
        try{
        	if(this.isUsrVentas()) {
        		noCliente = null;
        	}
        	
             List<com.alliax.portalclientes.domain.Pedido> pedidos = service.findCotizacionesFlete(fecha , noCotizacion ,noCliente);
             for(com.alliax.portalclientes.domain.Pedido p : pedidos){
                        CotizacionFlete c = new CotizacionFlete();
                        c.setNroPedido(String.valueOf(p.getIdPedido()));
                        c.setNoCotizacion(p.getNoCotizacion());
                        c.setEstado(p.getEstatusCotizacion());
                        PedidoPartidasPK pk = new PedidoPartidasPK();
                        
                        pk.setIdPedido(p.getIdPedido());
                        pk.setSku(CotizacionFlete.idMatFlete);
                        
                        com.alliax.portalclientes.domain.PedidoPartidas pp = partidaService.findById(pk);
                        if(pp== null) {
                        	 List<PedidoPartidas> partidasPedidos = partidaService.findByidPedido(p.getIdPedido());
                        	 
                        	pp = new PedidoPartidas();
                        	pp.setPosicion(String.valueOf(partidasPedidos.size() + 1) );
                        	pp.setId(pk);
                        	pp.setCantidad("1");
                        	pp.setPrecioNeto("0");
                        	pp.setMonto("0");
                        	partidaService.save(pp);
                        	
                        }
                        c.setMaterial(CotizacionFlete.idMatFlete);
                    	c.setCantidad(pp.getCantidad());
                    	c.setDescripcion(CotizacionFlete.descFlete);
                    	c.setPrecioNeto(pp.getPrecioNeto()!= null ? new BigDecimal(pp.getPrecioNeto()):null);
                    	c.setMonto(pp.getMonto()!= null ? new BigDecimal(pp.getMonto()) : null);
                    	c.setuM(CotizacionFlete.unidadMed);
                    	
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
        PrecioMaterialRFC precioRFC = null;
        try{

            precioRFC = this.getSpringContext().getBean("precioMaterialRFC",PrecioMaterialRFC.class);
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
             
                    PrecioMaterial precio = null;
                    
                   try{ 
                    	
                    	precioRFC.obtienePrecioMaterial(pedido.getClasePedido(),
                    														pedido.getOrganizacionVenta()
                    														,"20","02",
                    														pedido.getTipoMaterial(),
                    														pp.getId().getSku(),
                    														pp.getCantidad(),
                    														mat.getUnidadMedida(),
                    														pedido.getNroCliente(),
                    														pedido.getDestinatarioMercancia());
                    } catch(Exception e){
            			logger.error("Error al obtener precio");
            			throw new  Exception("Error al obtener lista de clientes");
            		} 
              
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
                d.setMonto(pp.getMonto()!= null ? new BigDecimal(pp.getMonto()) : null);
                d.setPrecioNeto(pp.getPrecioNeto()!= null ? new BigDecimal(pp.getPrecioNeto()) : null);
                d.setCantidad(pp.getCantidad());
                d.setFechaEnt(pp.getFechaEntrega());
                d.setEstatus(pedido.getEstatus());
                d.setCantEnt(pp.getCantidadEntregada());
                d.setMoneda(pp.getMoneda());
                this.moneda = pp.getMoneda();
                subtotal = subtotal.add(pp.getMonto()!= null ? new BigDecimal(pp.getMonto()) : BigDecimal.ZERO);
                impuesto = impuesto.add(pp.getIva()!= null ? new BigDecimal(pp.getIva()) : BigDecimal.ZERO); 
                if(pp.getId().getSku().equals(CotizacionFlete.idMatFlete)) {
                	d.setNoMaterial(CotizacionFlete.idMatFlete);
                	d.setDescripcion(CotizacionFlete.descFlete);
                	d.setUnidadMedida(CotizacionFlete.unidadMed);
                	d.setEstatus(pedido.getEstatusCotizacion());
                	if(this.isUsrVentas()) {
                	if(d.getEstatus().equals(CotizacionFlete.estadoCaptura)) {
                	this.noCotizacionSel = pedido.getNoCotizacion();
                	this.cotizacion = d;
                	}else {
                		partidas.add(d);
                	}
                	}else {
                		this.noCotizacionSel = pedido.getNoCotizacion();
                    	this.cotizacion = d;
                    	partidas.add(d);
                	}
                }else {
                	partidas.add(d);
                }
                this.subtotal = subtotal;
                this.impuesto = impuesto;
                this.total= subtotal.add(impuesto);
            }
            }
        } catch(Exception e){
            logger.info("Error al desplegar listado de fletes ",e);
            this.getFacesContext().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,"Error",this.getLblMain().getString("errListaPedidos")));
        }
        return "";
    }


    public void enviarMailCotizacion(String nroPedido) {
        logger.info("enviarMailCotizacion");
        try {
            logger.info("Iniciando envio de email");
            BigDecimal total = BigDecimal.ZERO;
            String fechaEntrega = this.cotizacion.getFechaEnt();
            logger.info("Fecha Entrega:"+fechaEntrega);
            if (partidas != null && !partidas.isEmpty()) {
                logger.info("Se encontraron partidas:");
                if(this.cotizacion!=null&&this.cotizacion.getMonto()!=null) {
                    logger.info("monto :" +this.cotizacion.getMonto());
                    total = this.cotizacion.getMonto();
                }
                logger.info("email pedido :" + this.email);
                logger.info("Total para envio de email:" + total);
                ConstructEmail mail = this.getSpringContext().getBean("constructEmail", ConstructEmail.class);
                mail.enviaCorreoCotizacion(this.email, this.getClienteInfo(), this.noCotizacion, this.partidas, total.toString(), fechaEntrega);
                logger.info("temmina envio email " + this.email + " pedido :" + nroPedido);
            } else {
                logger.info("No se encontraros partidas para el nroPedido-" + nroPedido);
            }
        }catch (Exception e) {
            logger.info("ErrorEnviaCorreoCotizacion", e);
        }
    }

    
    public String grabar(DetallePedidoCotizacion cotizacion) {
    	logger.info("grabar noCotizacion" +  noCotizacion);
    		if(cotizacion!= null && cotizacion.getNoPedido()!= null) {
                Pedido pedido=  service.findById(new Long(cotizacion.getNoPedido()));
    		    logger.info("grabar noCotizacion" + cotizacion.getDescripcion() + " " + cotizacion.getNoPedido() + " " + cotizacion.getMonto());
    		    PedidoPartidasPK pk = new PedidoPartidasPK();
    		    Long idPedido = Long.valueOf(cotizacion.getNoPedido());
    		    pk.setIdPedido(idPedido);
                pk.setSku(CotizacionFlete.idMatFlete);
                com.alliax.portalclientes.domain.PedidoPartidas pp = partidaService.findById(pk);
                if(pp!= null) {
                    try {
                        logger.info("Se guardaran los cambios al pedido " + idPedido);
                        pp.setMonto(cotizacion.getMonto()!= null ? cotizacion.getMonto().toString() : "0");
                        pp.setPrecioNeto(cotizacion.getPrecioNeto()!= null ? cotizacion.getPrecioNeto().toString() : "0");
                        pp.setFechaEntrega(cotizacion.getFechaEnt());

                        partidaService.save(pp);
                        pedido.setEstatusCotizacion(CotizacionFlete.estadoFin);
                        service.save(pedido);
                        cotizacion.setEstatus(pedido.getEstatusCotizacion());
                        this.mostrarCotizacion = true;
                    }catch(Exception e) {
                        logger.info("ErrorGrabar",e);
                    }
                    enviarMailCotizacion(cotizacion.getNoPedido());
                }
    	}
    	return "";
    	
    }
    
   public String ordenarPedido(String noPedido) throws Exception{
        logger.info("inicio ordenar pedido :" + noPedido);
		String documento="E";
    	  try{
        	 CrearPedidoRFC crearPedidoRFC = this.getSpringContext().getBean("crearPedidoRFC",CrearPedidoRFC.class);
             com.alliax.portalclientes.model.Pedido pedidoRFC = crearPedidoRFC(noPedido);
             logger.info("ordenarPedido pedidoRFC: " + pedidoRFC);
             if(pedidoRFC!= null) {

            	 PedidoResultado pedidoResultado = crearPedidoRFC.crearPedido(pedidoRFC);
                 if(pedidoResultado!=null&&pedidoResultado.getGeneroDocumentoVenta().equals("0")){
                     documento = pedidoResultado.getDocumentoVenta();
                 }
             }

    	  }catch (Exception e){
        	 logger.error("Error al genear pedido en SAP" + e.getLocalizedMessage());
             this.getFacesContext().addMessage(null, new FacesMessage(
                     FacesMessage.SEVERITY_ERROR,"Error",this.getLblMain().getString("errListaPedidos")));
         }
        logger.info("fin ordenarPedido pedidoRFC: "+  noPedido);
		getFacesContext().getExternalContext().redirect("pedidos/listado.xhtml?documento="+documento);
    	return "";
    }

    public com.alliax.portalclientes.model.Pedido crearPedidoRFC(String noPedido){
    	com.alliax.portalclientes.model.Pedido pedidoRFC = null;
        Material mat = null;
        com.alliax.portalclientes.model.PedidoPartidas partidaRFC;
    	if(noPedido!= null && noPedido.length()>0) {
    		Pedido pedido = this.service.findById(new Long(noPedido));
    		if(pedido!= null) {
    			pedidoRFC = new com.alliax.portalclientes.model.Pedido();
    			PedidoEncabezado encabezado = new com.alliax.portalclientes.model.PedidoEncabezado();
    			List<com.alliax.portalclientes.model.PedidoPartidas> partidas = new ArrayList();
    			List<PedidoReferenciaUbicacion> referencias = new ArrayList();
    			List<PedidoProductoAlmacenar> prodAlmacenar = new ArrayList();
    			List<PedidoCapacidadesTransporteEspecial> transporteEspecial = new ArrayList();
    			List<PedidoEquipoEspecialProteccionPersonal>ProteccionPersonal = new ArrayList();
        
    			pedidoRFC.setNombreCliente(pedido.getNombreContacto() + " " + pedido.getApellidoContacto());
    			pedidoRFC.setNroTeleofno(pedido.getTelefonoContacto());
    			pedidoRFC.setNroTelefonoFijo(pedido.getTelefonoFijoContacto());
    			pedidoRFC.setHorarioRecepcion(pedido.getHorarioRecepcion());


    			encabezado.setNroCliente(Helper.lpad(pedido.getNroCliente(),10,"0"));
    			encabezado.setNroDestinatarioMercancias(Helper.lpad(pedido.getDestinatarioMercancia(),10,"0"));
    			encabezado.setClasePedido(pedido.getClasePedido());
    			encabezado.setOrganizacionVenta(pedido.getOrganizacionVenta());
    			encabezado.setCanalDistribucion("20");
    			encabezado.setSector("02");
    			encabezado.setMotivoPedido("166");
    			encabezado.setSegmento(pedido.getTipoMaterial());
    			encabezado.setNroPedidoCliente(pedido.getNroPedido());
    			encabezado.setSociedad(pedido.getSociedad());
    			encabezado.setMetodoPago(pedido.getMetodoPago());
    			encabezado.setUsoCFDI(pedido.getUsoCFDI());
                encabezado.setMoneda("MXN");

    			List<PedidoPartidas> partidasPedidos = partidaService.findByidPedido(pedido.getIdPedido());
        
    			for(PedidoPartidas pp : partidasPedidos){
    				partidaRFC = new com.alliax.portalclientes.model.PedidoPartidas();

    				partidaRFC.setPosicion(Helper.lpad(pp.getPosicion(),6,"0"));
    				partidaRFC.setNroMaterial(Helper.lpad(pp.getId().getSku(),18,"0"));
    				partidaRFC.setCantidad(Helper.lpad(pp.getCantidad(),13,"0"));
    				mat = materialService.findById(pp.getId().getSku());
    				if(mat!= null){
    					partidaRFC.setUnidadMedida(mat.getUnidadMedida()!= null ? mat.getUnidadMedida().trim() : "");

    				}
    				if(pp.getId().getSku().equals(CotizacionFlete.idMatFlete)){
    				    //partidaRFC.setUnidadMedida(CotizacionFlete.unidadMed);
    				    partidaRFC.setMonto(pp.getMonto());
                    }

                    partidas.add(partidaRFC);
    			}

    			pedidoRFC.setPedidoPartidas(partidas);
    			pedidoRFC.setPedidoEncabezado(encabezado);
                pedidoRFC.setReferenciaUbicacion(pedido.getReferenciaUbicacion());
                pedidoRFC.setPedidoProductoAlmacenar(pedido.getProductoAlmacenar());
                pedidoRFC.setPedidoCapacidadesTransporteEspecial(pedido.getCapacidadesTransporte());
                pedidoRFC.setPedidoEquipoEspecialProteccionPersonal(pedido.getEquipoEspecial());
            }
    	}
    	
    return pedidoRFC;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    

    
    
}