/**
 * Backing bean para Estado de Cuenta
 */
package com.alliax.portalclientes.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBElement;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.datacontract.schemas._2004._07.tt_eol_level_be.RPTADOCTRIBBE;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.alliax.portalclientes.controller.AutocompleteClientesRFC;
import com.alliax.portalclientes.controller.ClienteCompanyRFC;
import com.alliax.portalclientes.controller.EstadoCuentaDetRFC;
import com.alliax.portalclientes.controller.EstadoCuentaPdf;
import com.alliax.portalclientes.controller.XlsExportEstadoCuenta;
import com.alliax.portalclientes.domain.ClienteVendedor;
import com.alliax.portalclientes.general.formato.Convertidor;
import com.alliax.portalclientes.general.formato.Fecha;
import com.alliax.portalclientes.general.pdf.Pdf;
import com.alliax.portalclientes.model.ClienteCompany;
import com.alliax.portalclientes.model.EstadoCuenta;
import com.alliax.portalclientes.model.EstadoCuentaDet;
import com.alliax.portalclientes.service.ClienteVendedorService;
import com.alliax.portalclientes.util.Helper;

@ManagedBean(name="edoCta")
@ViewScoped
public class EstadoCuenta_backing extends AbstractBackingGen {
	
	private EstadoCuenta estadoCuenta;
	private List<EstadoCuentaDet> detalle;
		
	private final static Logger logger = Logger.getLogger(EstadoCuenta_backing.class);
	
	private String fechaInicioHD;
	private String fechaCorte;
	private String fechaCorteShow;
	private String empresa;
	private String classPartidas = "tab-pane active";
	private String classFacturas = "tab-pane";
	private String fechaConsulta;
	private String horaConsulta;
	private String mensajeError;
    
	private LinkedHashMap<String,String> clienteCompany;
	
	private ClienteCompanyRFC clienteCompanyRFC;
	private EstadoCuentaDetRFC edoCtaRFC;
	private AutocompleteClientesRFC autocompleteClienteRFC;
	private ClienteVendedorService clienteVendedorService;

	@PostConstruct
	public void init(){
		try{
			this.clienteCompanyRFC = this.getSpringContext().getBean("clienteCompanyRfc",ClienteCompanyRFC.class);
			this.edoCtaRFC = this.getSpringContext().getBean("estadoCuentaDetRFC",EstadoCuentaDetRFC.class);
			this.autocompleteClienteRFC = this.getSpringContext().getBean("autocompleteClienteRFC",AutocompleteClientesRFC.class);
			this.clienteVendedorService = this.getSpringContext().getBean("clienteVendedorService",ClienteVendedorService.class);
			
			this.estadoCuenta = new EstadoCuenta();
			this.detalle = new ArrayList<EstadoCuentaDet>();
			this.horaConsulta = Fecha.getFechaFormateada(Fecha.getDateActual(), 3);
			this.fechaConsulta = Fecha.getFechaFormateada(Fecha.getDateActual(), 7);
			this.estadoCuenta.setFechaCorte(Fecha.getDateActual());
			this.fechaCorte = Fecha.getFechaFormateada( this.estadoCuenta.getFechaCorte(), 6 );
			this.fechaCorteShow = Fecha.getFechaFormateada( this.estadoCuenta.getFechaCorte(), 7 );
//			this.generarEstadoCuenta();
//			logger.info("EstadoCuenta_backing:::::::::::::::::::::::::::::::::::::::::::");
//			logger.info("FacesContext.getCurrentInstance().getViewRoot().getLocale: " + FacesContext.getCurrentInstance().getViewRoot().getLocale());
//			logger.info("FacesContext.getCurrentInstance().getApplication().getDefaultLocale: " +  FacesContext.getCurrentInstance().getApplication().getDefaultLocale());
//			logger.info("LocaleContextHolder.getLocale: " + LocaleContextHolder.getLocale());
			
//        	FacesContext.getCurrentInstance().getApplication().setDefaultLocale(new Locale("es"));
//        	FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es"));
//        	LocaleContextHolder.setLocale(new Locale("es"));
		}catch(Exception e){
			logger.error("Error al iniciar");
		}
	}

	public void buscaCliente(AjaxBehaviorEvent abe){
		try {			
			
			//Reestableciendo valores
			if(this.detalle != null){
				this.estadoCuenta = new EstadoCuenta();
				this.detalle = new ArrayList<EstadoCuentaDet>();
			}

			getClienteCompany();
		} catch(Exception e){
			logger.error("Error al buscar cliente " + e.getLocalizedMessage(),e);
			this.getFacesContext().addMessage("", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"",
						(new MessageFormat(this.getLblMain().getString("errProcesamiento")))
							.format(new Object[]{e.getLocalizedMessage()})));
		}
	}
	
	/**
	 * Metodo para Extraer las facturas de un pedido
	 * @param abe
	 */
	public String generarEstadoCuenta(){
		try {
			logger.info("Buscando registros de estado de cuenta ");
			String cliente = this.getNoCliente()!= null?this.getNoCliente():this.getUsuarioLogueado().getNoCliente();
			if(!this.tienePermiso(cliente)){
				this.estadoCuenta = new EstadoCuenta();
				this.detalle = new ArrayList<EstadoCuentaDet>();
				this.setMensajeError(this.getLblMain().getString("sinPermisoConsultaCliente"));
				return null;
			}
			this.setMensajeError("null");
							
			logger.info("::::::::FechaCortePortal:::::: " + this.getFechaCorte() );
			this.setEstadoCuenta(
					this.edoCtaRFC.detalleEstadoCuenta(cliente, this.getEmpresa(), this.getFechaCorte(), "ES"));
			this.setDetalle(this.getEstadoCuenta().getDetalle());
			
			if(this.estadoCuenta == null || this.estadoCuenta.getDetalle() == null){
				this.setMensajeError(this.getLblMain().getString("noHayResultados"));
			}
		} catch(Exception e){
			logger.error("Error al desplegar detalle del estado de Cuenta " + e.getLocalizedMessage());										
		}
		
		return "";
	}

	/**
	 * Metodo para descargar resultados en Xlsx
	 */
	public void decargarXlsxListadoPagos(){
		try {			
			logger.info("decargarXlsxListadoPagos");
														
			XlsExportEstadoCuenta exportpedidos = this.getSpringContext().getBean("exportEstadoCuenta",XlsExportEstadoCuenta.class);			

			// Busqueda  de los complementos según los filtros selecionados
			EstadoCuenta edoCta;
			logger.info("::::::::FechaCorteXLS::::::::" + this.getFechaCorte());
			if(this.getNoCliente() == null || this.getNoCliente().isEmpty()){
				edoCta = this.edoCtaRFC.detalleEstadoCuenta(this.getUsuarioLogueado().getNoCliente(), this.getEmpresa(), this.getFechaCorte(), "ES");
			}else{
				edoCta = this.edoCtaRFC.detalleEstadoCuenta(this.getNoCliente(), this.getEmpresa(), this.getFechaCorte(), "ES");
			}			

			Workbook workbook = exportpedidos.estadoCuentaToXls(edoCta, this.getLblMain(), "es");
			
			ExternalContext externalContext = getFacesContext().getExternalContext();
			externalContext.responseReset(); 
			externalContext.setResponseContentType("application/excel");
			externalContext.setResponseHeader("Content-Disposition", "attachment;filename=ReportePagosProv.xlsx");				
			
			workbook.write(externalContext.getResponseOutputStream());
			getFacesContext().responseComplete(); // Prevent JSF from performing navigation.*/				
									
		} catch(Exception e){
			logger.error("Error al obtener listado del pedidos " + e.getLocalizedMessage(),e);
			
			this.getFacesContext().addMessage("", 
					new FacesMessage(FacesMessage.SEVERITY_INFO,"",
						(new MessageFormat(this.getLblMain().getString("errProcesamiento")))
							.format(new Object[]{e.getLocalizedMessage()})));				
		}
	}
	
	/**
	 * Metodo para descargar resultados en pdf 
	 */
	public void descargaPdf(){
		logger.info("descargaPdf");
		try{			
			
			
			Pdf pdf = null;
			EstadoCuentaPdf comppdf = null;			

//			LeeXMLV3_3 leexml33 = this.getSpringContext().getBean("leeXMLV3_3",LeeXMLV3_3.class);
			
			comppdf = this.getSpringContext().getBean("estadoCuentaPdf",EstadoCuentaPdf.class);

			// Busqueda  de los complementos según los filtros selecionados
			EstadoCuenta edoCta;
			if(this.getNoCliente() == null || this.getNoCliente().trim().isEmpty()){
				edoCta = this.edoCtaRFC.detalleEstadoCuenta(this.getUsuarioLogueado().getNoCliente(), this.getEmpresa(), this.getFechaCorte(), "ES");
			}else{
				edoCta = this.edoCtaRFC.detalleEstadoCuenta(this.getNoCliente(), this.getEmpresa(), this.getFechaCorte(), "ES");
			}
			
			pdf = comppdf.GenerarPdf(edoCta, this.getLblMain());

			
			ExternalContext externalContext = getFacesContext().getExternalContext();
			externalContext.responseReset(); 
			externalContext.setResponseContentType("Application/Pdf");
			externalContext.setResponseHeader("Content-Disposition", "inline;filename=estadoCuenta.pdf");				
						
			//OutputStream
			OutputStream os = externalContext.getResponseOutputStream();
			
			os.write(((EstadoCuentaPdf)comppdf).getPdfOutput(pdf));

			
			
			getFacesContext().responseComplete(); // Prevent JSF from performing navigation.*/			
								
		} catch(Exception e){
			logger.error("Error al generar PDF " + e.getLocalizedMessage(),e);
		}
	}
	
		
	public void descargaFacturaArgentina(String facturaSAP, String facturaFiscal){
		try {
			logger.info("Factura SAP: " + facturaSAP);
			facturaSAP = StringUtils.leftPad(facturaSAP, 10, "0");
			//facturaSAP = String.format("%10d", facturaSAP); //Rellenar a 10 posiciones con ceros a la izquierda
			 logger.info("Factura SAP: " + facturaSAP);
			 logger.info("Factura Fiscal: " + facturaFiscal);
			 
			File pdfFile;		
			String pathFile = "F:\\Facturas\\Argentina\\PRD\\";			
			String nameFactura = facturaSAP + "_" + facturaFiscal + ".PDF";
//			String nameFactura = "0320052042_0004A00023167.PDF";
			if(!new File(pathFile + nameFactura).exists()){				
				pathFile = "D:\\Facturas\\Argentina\\PRD\\";  //La unidad F no existe en QAS
			}						
		    pdfFile = new File(pathFile + nameFactura);            
			 logger.info( pathFile + nameFactura );			
			InputStream is = new FileInputStream(pdfFile);
			
			byte[] test = IOUtils.toByteArray(is);
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = this.getFacesContext().getExternalContext();
			externalContext.responseReset();
			externalContext.setResponseContentType("application/pdf");
			
			externalContext.setResponseHeader("Content-Disposition", "inline;filename=" + nameFactura);
			
			OutputStream os = externalContext.getResponseOutputStream();
			
			os.write(test);
			context.responseComplete(); // Prevent JSF from performing navigation.			
			
		} catch(Exception e){
			System.out.println( "::::::::::::::::::: AR  " + e.getMessage());
			logger.error("Error al obtener listad de Precios " + e.getLocalizedMessage() );
		}
	}
	
	public void descargaFacturaPeru(String notaEntrega, String tipoArchivo){
		try {
			logger.info("Nota Entrega: " + notaEntrega);
			logger.info("tipoArchivo: " + tipoArchivo);
			StringTokenizer st = new StringTokenizer(notaEntrega,"-");
			
//			String usuario = "demo@dalka.eol.pe"; //QAS
//			String password = "abc123abc"; //QAS
			String usuario = "oper@dalka.eol.pe"; //PRD
			String password = "34903e0c";  //PRD
			String ruc = "20389748669";
			String tipoComprobante = st.nextToken();  
			String serie = st.nextToken();
			String correlativo = st.nextToken();
//			String tipoComprobante = "01";  
//			String serie = "FA01";
//			String correlativo = "10079577";
			logger.info("tipoComprobante: " + tipoComprobante);
			logger.info("tipoArchivo: " + tipoArchivo);
			logger.info("correlativo: " + correlativo);
			
			boolean isXML = tipoArchivo.equals("xml") ? true : false;
			boolean isPDF = tipoArchivo.equals("pdf") ? true : false;
						
			org.tempuri.IwsOnlineToCPE serviceFacturas = new org.tempuri.WsOnlineToCPE().getBasicHttpBindingIwsOnlineToCPE();		 
			RPTADOCTRIBBE response = serviceFacturas.callExtractCPE(usuario, 
														            password, 
														            ruc, 
														            tipoComprobante, 
														            serie,	 
														            correlativo, 
														            isXML, 
														            isPDF, 
														            false);
			
			logger.info("Codigo de respuesta WebService Factura Peru: " + response.getCODRPTA().getValue() );
			logger.info("Descripción respuesta WebService Factura Peru: " + response.getDESCRIPCION().getValue() );
			
			byte[] test = null;			
			
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = this.getFacesContext().getExternalContext();
			externalContext.responseReset();
			if(isPDF && response.getDOCTRIBPDF().getValue() != null && response.getDOCTRIBPDF().getValue().length > 0 ){
				test = response.getDOCTRIBPDF().getValue();
				externalContext.setResponseContentType("application/pdf");			
				externalContext.setResponseHeader("Content-Disposition", "inline;filename=" + correlativo + ".pdf");
				
				OutputStream os = externalContext.getResponseOutputStream();		
				os.write(test);			
				os.flush();			
				os.close();			
				context.responseComplete(); // Prevent JSF from performing navigation.
			}else if(isXML && response.getDOCTRIBXML().getValue().getBytes() != null && response.getDOCTRIBXML().getValue().getBytes().length > 0){
				test = response.getDOCTRIBXML().getValue().getBytes();
				externalContext.setResponseContentType("text/plain");			
				externalContext.setResponseHeader("Content-Disposition", "attachment;filename=" + correlativo + ".xml");
				
				OutputStream os = externalContext.getResponseOutputStream();		
				os.write(test);			
				os.flush();			
				os.close();			
				context.responseComplete(); // Prevent JSF from performing navigation.
			}else{		
				externalContext.setResponseContentType("text/html");
				
				OutputStream os = externalContext.getResponseOutputStream();		
				os.write("Factura no encontrada".getBytes());			
				os.flush();			
				os.close();			
				context.responseComplete(); // Prevent JSF from performing navigation.
			}
			
							
		} catch(Exception e){			
			logger.error("::::::::::::::::::::::Error al obtener factura PE " + e.getLocalizedMessage() );
		}
	}
	
	
	
	
	
	/******getters & setters*******/
	public void getJSONClientes(){		
		String pais = getUsuarioLogueado().getPais();
		if(pais.isEmpty()){ pais = "MX"; }
		//Verificar si tiene rol autocompleteall
		if(getUsuarioLogueado().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_AUTOCOMPLETE_ALL")) ){
			pais = "ALL";
		}
			
		
		logger.info("Pais de usuario logueado es: " + pais);
		try{
			if(Helper.isTimeToReloadJSONfiles()){
				Helper.writeJSONClienteList( Helper.getJSONClientes(this.autocompleteClienteRFC, "CA") , "CA");
				Helper.writeJSONClienteList( Helper.getJSONClientes(this.autocompleteClienteRFC, "PE") , "PE");
				Helper.writeJSONClienteList( Helper.getJSONClientes(this.autocompleteClienteRFC, "AR") , "AR");
				Helper.writeJSONClienteList( Helper.getJSONClientes(this.autocompleteClienteRFC, "MX") , "MX");
				Helper.writeJSONClienteList( Helper.getJSONClientes(this.autocompleteClienteRFC, "BR") , "BR");
				Helper.writeJSONClienteList( Helper.getJSONClientes(this.autocompleteClienteRFC, "ALL") , "ALL");
			}
		    FacesContext facesContext = FacesContext.getCurrentInstance();
		    ExternalContext externalContext = facesContext.getExternalContext();
		    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();		    
		    externalContext.setResponseContentType("application/json");
		    externalContext.setResponseCharacterEncoding("UTF-8");
		    externalContext.getResponseOutputWriter().write( Helper.readJSONClienteList(pais) );
		    /******caché********/       
            response.setHeader("Cache-Control", "max-age=604800"); //7 dias en segundos 604800               
            response.setDateHeader("Last-Modified", System.currentTimeMillis() ); 
            response.setDateHeader("Expires", System.currentTimeMillis() + 604800000 );  //7 dias en milisegundos            
//		    externalContext.setResponseHeader("Cache-Control", "max-age=604800"); //7 dias en segundos 604800
//		    externalContext.setResponseHeader("Last-Modified", System.currentTimeMillis()+"" ); 
//		    externalContext.setResponseHeader("Expires", (System.currentTimeMillis() + 604800000)+"" );  //7 dias en milisegundos    
//		    facesContext.responseComplete();			
		}catch(Exception e){
			logger.error("::::::::::::::::::::::Error al obtener lista de clientes para el pais:" + pais + " " + e.getLocalizedMessage());			
		}		
	}
	public String getMontoFormateado(Object m){
		return Helper.getMontoFormateado(m, estadoCuenta.getPais());
	}
	public String getSimboloPesos(){
		if(estadoCuenta == null){
			return "";
		}else if(estadoCuenta.getPais() == null){
			return "";
	    }else{
			return Helper.getSignoPesos( estadoCuenta.getPais() );
		}		
	}
	public String getRFCLabel(String Pais){
		if(estadoCuenta == null){
			return "";
		}else if(estadoCuenta.getPais() == null){
			return "";
	    }else{
			return Helper.getRFCLabel( estadoCuenta.getPais() );
		} 
	}
	
	
	public String getFechaInicioHD() {
		return fechaInicioHD;
	}

	public void setFechaInicioHD(String fechaInicioHD) {
		this.fechaInicioHD = fechaInicioHD;
	}

	public String getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(String fechaCorte) {
		this.fechaCorte = fechaCorte;
	}
	
	public String getFechaCorteShow() {
		return fechaCorteShow;
	}

	public void setFechaCorteShow(String fechaCorteShow) {
		this.fechaCorteShow = fechaCorteShow;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public EstadoCuenta getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(EstadoCuenta edoCta) {
		this.estadoCuenta = edoCta;
	}

	public List<EstadoCuentaDet> getDetalle() {
		return detalle;
	}
	
	public void setDetalle(List<EstadoCuentaDet> detalle) {
		this.detalle = detalle;
	}
	
	public LinkedHashMap<String,String> getClienteCompany() {
		List<ClienteCompany> res = null;
		try{
			String cliente = this.getNoCliente()!= null?this.getNoCliente():this.getUsuarioLogueado().getNoCliente();
			//logger.info("llenando empresas de cliente : " + cliente);			
			this.setMensajeError(null);
			if(!this.tienePermiso(cliente)){
				this.estadoCuenta = new EstadoCuenta();
				this.detalle = new ArrayList<EstadoCuentaDet>();
				this.setMensajeError(this.getLblMain().getString("sinPermisoConsultaCliente"));				
			}			
			
		    res = (List<ClienteCompany>)this.clienteCompanyRFC.obtieneCompanyCliente(cliente);
				
			if(res == null || res.isEmpty()){	
				if(!this.getUsuarioLogueado().getNoCliente().equals(cliente))
					this.setMensajeError(this.getLblMain().getString("noHayResultados"));
			}else{
				if(mensajeError != null && mensajeError.equals(this.getLblMain().getString("sinPermisoConsultaCliente")))
					return null;
				this.setMensajeError(null);
				this.clienteCompany = new LinkedHashMap<String,String>();
				for(ClienteCompany soc:res){
					logger.info("company : " + soc.getCompanyCode() + " - " + soc.getCompanyName());
					this.clienteCompany.put(soc.getCompanyCode(), soc.getCompanyName());
				}
			}			
			

		}catch(Exception e){
			logger.error(e.getLocalizedMessage(),e);
			return null;
		}
		return clienteCompany;
	}
	
	private boolean tienePermiso(String cliente){
		//Si es usuario de ventas, validar si tiene asignado el cliente que quiere consultar
		boolean tienePermiso = true;		
		if(!isAdmin() && !isEjecutivo()){
			if(isUsrVentas()){
				List<ClienteVendedor> cv = this.clienteVendedorService.findByNoCliente(Convertidor.remueveCerosIzq(cliente));				
				 				 
				if(cv == null || cv.isEmpty()){
					tienePermiso = false;
				}else{
					for (ClienteVendedor clienteVendedor : cv) {
						if(clienteVendedor.getVendedor().equals( getUsuarioLogueado().getUsername() )){
							tienePermiso = true;
							break;
						}else{
							tienePermiso = false;
						}
					}
				}
				//Si entra como cliente, si tendrá permiso de consultar su propio número
				if(cliente.equals( this.getUsuarioLogueado().getNoCliente())){
					tienePermiso = true;
				}
			}
		}

		if(!tienePermiso){											
			logger.info("No tiene permiso:::::::::::::::::::::");
			this.clienteCompany = null;			
		}
		return tienePermiso;
	}

	public void setClienteCompany(LinkedHashMap<String,String> clienteCompany) {
		this.clienteCompany = clienteCompany;
	}
	
	public Set<Entry<String,String>> getClienteCompanySet(){
		try{
			return this.getClienteCompany().entrySet();
		} catch(NullPointerException npe){
			return null;
		}
	}	
	
	public ClienteCompanyRFC getClienteCompanyRFC() {
		return clienteCompanyRFC;
	}

	public void setClienteCompanyRFC(ClienteCompanyRFC clienteCompanyRFC) {
		this.clienteCompanyRFC = clienteCompanyRFC;
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
	
	public String getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(String fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}

	public String getHoraConsulta() {
		return horaConsulta;
	}

	public void setHoraConsulta(String horaConsulta) {
		this.horaConsulta = horaConsulta;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}


	
}
